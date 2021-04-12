package pl.streamsoft.data_base_services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CurrencyRatesTable")
@NamedQueries({
		@NamedQuery(name = "CurrencyRatesTable.GetRate", query = "SELECT r FROM CurrencyRatesTable r WHERE r.code = :code AND r.date = :date"),
		@NamedQuery(name = "CurrencyRatesTable.DeleteRate", query = "DELETE FROM CurrencyRatesTable r WHERE r.code = :code AND r.date = :date"),
		@NamedQuery(name = "CurrencyRatesTable.getFiveBestRates", query = "SELECT r FROM CurrencyRatesTable r WHERE r.code = :code ORDER BY r.rate DESC"),
		@NamedQuery(name = "CurrencyRatesTable.getFiveWorstRates", query = "SELECT r FROM CurrencyRatesTable r WHERE r.code = :code ORDER BY r.rate ASC") })

public class CurrencyRatesTable implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "Date", nullable = false, unique = false)
	private LocalDate date;

	@Column(name = "Rate", nullable = false, unique = false, columnDefinition = "DECIMAL(19,5)")
	private BigDecimal rate;

	@ManyToOne
	@JoinColumn(name = "f_code", referencedColumnName = "code")
	private CurrencyCodeTable codetable;

	@Column(name = "f_code", insertable = false, updatable = false)
	private String code;

	public CurrencyRatesTable() {

	}

	public CurrencyRatesTable(LocalDate date, BigDecimal rate, CurrencyCodeTable codetable) {
		super();
		this.date = date;
		this.rate = rate;
		this.codetable = codetable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CurrencyCodeTable getCodetable() {
		return codetable;
	}

	public void setCodetable(CurrencyCodeTable codetable) {
		this.codetable = codetable;
	}

}
