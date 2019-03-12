package nl.nuggit.aggregator.repository;

import java.util.Date;
import java.util.List;

import nl.nuggit.aggregator.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByInvoiceDateGreaterThanEqualAndInvoiceDateLessThanEqualAndClosedDateAndLedgerCode(Date from,
            Date to, Object closed, String ledgerCode);

    List<Invoice> findByDueDateGreaterThanEqualAndDueDateLessThanEqualAndClosedDateNotNullAndLedgerCodeOrderByDueDateAsc(
            Date from, Date to, String code);

}
