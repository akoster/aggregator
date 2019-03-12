package nl.nuggit.aggregator.api;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import nl.nuggit.aggregator.api.model.Cashflow;
import nl.nuggit.aggregator.model.Constants;
import nl.nuggit.aggregator.model.Ledger;
import nl.nuggit.aggregator.service.InvoiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuals")
@AllArgsConstructor
public class ActualsController {

    private InvoiceService invoiceService;

    @GetMapping(value = "/incoming")
    public Cashflow getHistoricIncomingInvoiceTotal(
            @RequestParam("from") @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date from,
            @RequestParam("to") @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date to) {
        return getCashflow(from, to, Ledger.RECEIVABLE_INVOICE);
    }

    @GetMapping(value = "/outgoing")
    public Cashflow getHistoricOutgoingInvoiceTotal(
            @DateTimeFormat(pattern = Constants.DATE_FORMAT) @RequestParam("from") Date from,
            @DateTimeFormat(pattern = Constants.DATE_FORMAT) @RequestParam("to") Date to) {
        return getCashflow(from, to, Ledger.PAYABLE_INVOICE);
    }

    private Cashflow getCashflow(Date from, Date to, Ledger ledger) {
        BigDecimal sum = invoiceService.getHistoricInvoiceTotal(from, to, ledger);
        return Cashflow.builder().from(from).to(to).amount(sum).build();
    }

}
