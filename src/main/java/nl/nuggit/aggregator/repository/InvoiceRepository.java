package nl.nuggit.aggregator.repository;

import java.util.Date;
import java.util.List;

import nl.nuggit.aggregator.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /**
     * Finds historic invoices with a dueDate between the given 'from' and 'to' dates and the given ledger code. The
     * results are sorted ascending by dueDate.
     *
     * @param from
     *         the lower boundary for dueDate, inclusive
     * @param to
     *         the upper boundary for dueDate, inclusive
     * @param ledgerCode
     *         the ledger code, 'R' for receivable and 'P' for payable.
     *
     * @return the matching invoices
     */
    List<Invoice> findByInvoiceDateGreaterThanEqualAndInvoiceDateLessThanEqualAndClosedDateNotNullAndLedgerCode(
            Date from, Date to, String ledgerCode);

    /**
     * Finds forecast invoices with a dueDate between the given 'from' and 'to' dates and the given ledger code. The
     * results are sorted ascending by dueDate.
     *
     * @param from
     *         the lower boundary for dueDate, inclusive
     * @param to
     *         the upper boundary for dueDate, inclusive
     * @param ledgerCode
     *         the ledger code, 'R' for receivable and 'P' for payable.
     *
     * @return the matching invoices
     */
    List<Invoice> findByDueDateGreaterThanEqualAndDueDateLessThanEqualAndClosedDateIsNullAndLedgerCodeOrderByDueDateAsc(
            Date from, Date to, String ledgerCode);

}
