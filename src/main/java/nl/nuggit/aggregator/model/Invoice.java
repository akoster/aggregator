package nl.nuggit.aggregator.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "invoice")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "party_id", nullable = false, updatable = false)
    private Party party;

    @Column(name = "booking_code")
    private String bookingCode;

    @Column(name = "booking_period")
    private String bookingPeriod;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_date")
    private Date invoiceDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "closed_date")
    private Date closedDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "currency")
    private String currency;

    @Column(name = "amount")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal amount;

    @Column(name = "tax_amount")
    @Digits(integer = Constants.DIGITS_INTEGER, fraction = Constants.DIGITS_FRACTION)
    private BigDecimal taxAmount;

    @Column(name = "company_id")
    private long companyId;

    @Column(name = "ledger")
    private String ledgerCode;

    public Ledger getLedger() {
        return Ledger.fromCode(ledgerCode);
    }

}
