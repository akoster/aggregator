package nl.nuggit.aggregator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "cff_input")
@Data
public class CffInput extends CashflowForecast {

    @Column(name = "organisation")
    private long organisation;
}
