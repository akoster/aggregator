package nl.nuggit.aggregator.api.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cashflow {
    private Date from;
    private Date to;
    private BigDecimal amount;
}
