package nl.nuggit.aggregator.api;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import nl.nuggit.aggregator.api.model.Cashflow;
import nl.nuggit.aggregator.api.model.Forecast;
import nl.nuggit.aggregator.model.Constants;
import nl.nuggit.aggregator.model.Ledger;
import nl.nuggit.aggregator.service.InvoiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forecast")
@AllArgsConstructor
public class ForecastController {

    private InvoiceService invoiceService;

    @GetMapping(value = "/incoming")
    public Forecast getForecastIncomingInvoices(
            @RequestParam("from") @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date from,
            @RequestParam("to") @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date to,
            @RequestParam("periodDays") int periodDays) {
        return getForecast(from, to, periodDays, Ledger.RECEIVABLE_INVOICE);
    }

    @GetMapping(value = "/outgoing")
    public Forecast getForecastOutgoingInvoices(
            @RequestParam("from") @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date from,
            @RequestParam("to") @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date to,
            @RequestParam("periodDays") int periodDays) {
        return getForecast(from, to, periodDays, Ledger.PAYABLE_INVOICE);
    }

    private Forecast getForecast(Date from, Date to, int periodDays, Ledger ledger) {
        List<Cashflow> cashflows = invoiceService.getForecasts(from, to, ledger, periodDays);
        return Forecast.builder().from(from).to(to).periodDays(periodDays).cashflows(cashflows).build();
    }

}
