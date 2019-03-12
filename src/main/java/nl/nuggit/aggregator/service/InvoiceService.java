package nl.nuggit.aggregator.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
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
                invoiceRepository.findByInvoiceDateGreaterThanEqualAndInvoiceDateLessThanEqualAndClosedDateAndLedgerCode(
                from, to, null, ledger.getCode());
        return invoices.stream().map(Invoice::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Cashflow> getForecasts(Date from, Date to, Ledger ledger, int periodDays) {
        List<Invoice> invoices =
                invoiceRepository.findByDueDateGreaterThanEqualAndDueDateLessThanEqualAndClosedDateNotNullAndLedgerCodeOrderByDueDateAsc(
                from, to, ledger.getCode());
        return aggregate(invoices, dayToMillis(periodDays));
    }

    private int dayToMillis(int periodDays) {
        return periodDays * 24 * 60 * 60 * 1000;
    }

    private List<Cashflow> aggregate(List<Invoice> invoices, long periodMillis) {
        LinkedList<Cashflow> cashflows = new LinkedList<>();
        for (Invoice invoice : invoices) {
            if (cashflows.isEmpty()) {
                cashflows.add(createCashflow(invoice));
            }
            aggregate(invoice, cashflows, periodMillis);
        }
        return cashflows;
    }

    private Cashflow createCashflow(Invoice invoice) {
        Cashflow cashflow = Cashflow.builder().amount(BigDecimal.ZERO).build();
        cashflow.setFrom(invoice.getDueDate());
        cashflow.setTo(invoice.getDueDate());
        return cashflow;
    }

    private void aggregate(Invoice invoice, LinkedList<Cashflow> cashflows, long periodMillis) {
        Cashflow cashflow = cashflows.getLast();
        if (isInPeriod(cashflow, invoice, periodMillis)) {
            cashflow.setAmount(cashflow.getAmount().add(invoice.getAmount()));
            cashflow.setTo(invoice.getDueDate());
        } else {
            cashflows.add(cashflow);
            cashflows.add(createCashflow(invoice));
        }
    }

    private boolean isInPeriod(Cashflow cashflow, Invoice invoice, long periodMillis) {
        return (invoice.getDueDate().getTime() - cashflow.getFrom().getTime()) < periodMillis;
    }

}
