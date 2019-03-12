package nl.nuggit.aggregator.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;

import lombok.Data;

@MappedSuperclass
@Data
public class CashflowForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "bank_account", updatable = false)
    private Account account;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "account_code")
    private String accountCode;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CffType type;

    @Column(name = "amount")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal amount;

    @Column(name = "forecast", columnDefinition = "integer")
    private boolean forecast;
}
