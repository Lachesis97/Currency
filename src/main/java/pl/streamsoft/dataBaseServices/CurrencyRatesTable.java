package pl.streamsoft.dataBaseServices;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "CurrencyRatesTable")
@NamedQueries({
		@NamedQuery(name = "CurrencyCodeTable.GetRate", query = "SELECT r FROM CurrencyRatesTable r WHERE r.date = :date AND r.cid = :cid") })

public class CurrencyRatesTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "cid", nullable = false)
	private long cid;

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
		return cid;
	}

	public void setCodeID(long codeID) {
		this.cid = codeID;
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
