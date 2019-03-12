package nl.nuggit.aggregator.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_no")
    private long accountNo;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "iban")
    private String iban;

    @Column(name = "credit_limit")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal creditLimit;

    @Column(name = "closing_balance")
    private String closingBalance;
}
