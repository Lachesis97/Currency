package pl.streamsoft.DbServices;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CurrencyRatesTable")
public class CurrencyRatesTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CodeID", nullable = false)
	private long codeID;

	@Column(name = "Date", nullable = false)
	private LocalDate date;

	@Column(name = "Rate", nullable = false, columnDefinition = "DECIMAL(19,5)")
	private BigDecimal rate;

	public CurrencyRatesTable() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getCodeID() {
		return codeID;
	}

	public void setCodeID(long codeID) {
		this.codeID = codeID;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}
