package nl.nuggit.aggregator.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "cff_output")
@Data
public class CffOutput extends CashflowForecast {

}
