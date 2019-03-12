package nl.nuggit.aggregator.api.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Forecast {
    private Date from;
    private Date to;
    private int periodDays;
    private List<Cashflow> cashflows;
}
