package nl.nuggit.aggregator.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import nl.nuggit.aggregator.api.model.Cashflow;
import nl.nuggit.aggregator.model.Invoice;
import nl.nuggit.aggregator.model.Ledger;
import nl.nuggit.aggregator.repository.InvoiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

    private long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    @InjectMocks
    private InvoiceService sut;
    @Mock
    private InvoiceRepository invoiceRepository;

    @Test
    public void getHistoricInvoiceTotal() {

        // SETUP
        Date from = new Date();
        Date to = new Date();
        Ledger ledger = Ledger.PAYABLE_INVOICE;
        List<Invoice> invoices = createInvoices(100, 200, 300);
        when(invoiceRepository.findByInvoiceDateGreaterThanEqualAndInvoiceDateLessThanEqualAndClosedDateNotNullAndLedgerCode(
                from, to, ledger.getCode())).thenReturn(invoices);

        // CALL
        BigDecimal result = sut.getHistoricInvoiceTotal(from, to, ledger);

        // VERIFY
        assertThat(result.intValue()).isEqualTo(600);
    }

    @Test
    public void getForecasts() {

        // SETUP
        Date from = new Date(0);
        Date to = new Date(4 * DAY_IN_MILLIS);
        Ledger ledger = Ledger.PAYABLE_INVOICE;
        List<Invoice> invoices = createInvoices(100, 200, 400);
        invoices.get(0).setDueDate(new Date(DAY_IN_MILLIS));
        invoices.get(1).setDueDate(new Date(2 * DAY_IN_MILLIS));
        invoices.get(2).setDueDate(new Date(3 * DAY_IN_MILLIS));
        when(invoiceRepository.findByDueDateGreaterThanEqualAndDueDateLessThanEqualAndClosedDateIsNullAndLedgerCodeOrderByDueDateAsc(
                from, to, ledger.getCode())).thenReturn(invoices);
        int periodDays = 2;

        // CALL
        List<Cashflow> result = sut.getForecasts(from, to, ledger, periodDays);

        // VERIFY
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAmount().intValue()).isEqualTo(300);
        assertThat(result.get(1).getAmount().intValue()).isEqualTo(400);
    }

    private List<Invoice> createInvoices(int... amounts) {
        return Arrays.stream(amounts).mapToObj(this::createInvoice).collect(Collectors.toList());
    }

    private Invoice createInvoice(int amount) {
        Invoice invoice = new Invoice();
        invoice.setAmount(new BigDecimal(amount));
        return invoice;
    }
}