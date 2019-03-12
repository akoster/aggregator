package nl.nuggit.aggregator.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "financial")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Financial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "year", unique = true)
    private int year;

    @Column(name = "accounts_receivable")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal accountsReceivable;

    @Column(name = "accounts_payable")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal accountsPayable;

    @Column(name = "annual_sales")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal annualSales;

    @Column(name = "annual_purchases")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal annualPurchases;

    @Column(name = "inventory")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal inventory;

    @Column(name = "cost_of_goods_sold")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal costOfGoodsSold;

    @Column(name = "days")
    private int days;

    @Column(name = "currency")
    private String currency;

    @Column(name = "initial_liquidity")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal initialLiquidity;

    @Column(name = "initial_liquidity_date")
    private Date initialLiquidityDate;
}
