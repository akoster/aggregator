package nl.nuggit.aggregator.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import nl.nuggit.aggregator.api.model.Cashflow;
import nl.nuggit.aggregator.model.Invoice;
import nl.nuggit.aggregator.model.Ledger;
import nl.nuggit.aggregator.repository.InvoiceRepository;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class InvoiceService {

    private InvoiceRepository invoiceRepository;

    public BigDecimal getHistoricInvoiceTotal(Date from, Date to, Ledger ledger) {
        List<Invoice> invoices =
                invoiceRepository.findByInvoiceDateGreaterThanEqualAndInvoiceDateLessThanEqualAndClosedDateNotNullAndLedgerCode(
                from, to, ledger.getCode());
        return invoices.stream().map(Invoice::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Cashflow> getForecasts(Date from, Date to, Ledger ledger, int periodDays) {
        List<Invoice> invoices =
                invoiceRepository.findByDueDateGreaterThanEqualAndDueDateLessThanEqualAndClosedDateIsNullAndLedgerCodeOrderByDueDateAsc(
                from, to, ledger.getCode());
        return aggregate(invoices, from, to, dayToMillis(periodDays));
    }

    private int dayToMillis(int periodDays) {
        return periodDays * 24 * 60 * 60 * 1000;
    }

    private List<Cashflow> aggregate(List<Invoice> invoices, Date from, Date to, long periodMillis) {
        List<Cashflow> cashflows = new ArrayList<>();
        Cashflow cashflow = createCashflow(from, periodMillis);
        cashflows.add(cashflow);
        for (Invoice invoice : invoices) {
            cashflow = walkToDate(cashflow, invoice.getDueDate(), periodMillis, cashflows);
            cashflow.setAmount(cashflow.getAmount().add(invoice.getAmount()));
        }
        cashflow = walkToDate(cashflow, to, periodMillis, cashflows);
        return cashflows;
    }

    private Cashflow walkToDate(Cashflow cashflow, Date date, long periodMillis, List<Cashflow> cashflows) {
        while (date.getTime() > cashflow.getTo().getTime()) {
            cashflow = createCashflow(cashflow.getTo(), periodMillis);
            cashflows.add(cashflow);
        }
        return cashflow;
    }

    private Cashflow createCashflow(Date from, long periodMillis) {
        Cashflow cashflow = Cashflow.builder().amount(BigDecimal.ZERO).build();
        cashflow.setFrom(from);
        cashflow.setTo(new Date(from.getTime() + periodMillis));
        return cashflow;
    }

}
