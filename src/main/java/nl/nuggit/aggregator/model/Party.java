package nl.nuggit.aggregator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "party")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private long partyId;

    @Column(name = "contact_code")
    private long contactCode;

    @Column(name = "contact_id")
    private long contactId;

    @Column(name = "name")
    private String name;

    @Column(name = "relationship")
    private String relationshipCode;

    @Column(name = "segment")
    private String segment;

    @Column(name = "company_id")
    private long companyId;

    public Relationship getRelationship() {
        return Relationship.fromCode(relationshipCode);
    }
}
