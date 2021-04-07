package pl.streamsoft.data_base_services;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "CurrencyCodeTable")
@NamedQueries({
		@NamedQuery(name = "CurrencyCodeTable.GetCurrency", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE c.code = :code AND r.date = :date"),
		@NamedQuery(name = "CurrencyCodeTable.GetCode", query = "SELECT c FROM CurrencyCodeTable c WHERE c.code = :code"),
		@NamedQuery(name = "CurrencyCodeTable.getFiveBestRates", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE c.code = :code ORDER BY r.rate DESC"),
		@NamedQuery(name = "CurrencyCodeTable.getFiveWorstRates", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE c.code = :code ORDER BY r.rate ASC"),
		@NamedQuery(name = "CurrencyCodeTable.GetMaxRate", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE r.rate = (SELECT MAX(r.rate) FROM CurrencyRatesTable r WHERE r.cid = :cid AND (r.date BETWEEN :start AND :end))"),
		@NamedQuery(name = "CurrencyCodeTable.GetMinRate", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE r.rate = (SELECT MIN(r.rate) FROM CurrencyRatesTable r WHERE r.cid = :cid AND (r.date BETWEEN :start AND :end))") })

public class CurrencyCodeTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "Name", nullable = false, unique = true)
	private String name;

	@Column(name = "Code", nullable = false, unique = true)
	private String code;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = CurrencyRatesTable.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cid", referencedColumnName = "id")
	private Set<CurrencyRatesTable> rate;

	public CurrencyCodeTable() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<CurrencyRatesTable> getRate() {
		return rate;
	}

	public void setRate(Set<CurrencyRatesTable> rate) {
		rate = rate;
	}

	@Override
	public String toString() {
		return "CurrencyCodeTable [id=" + id + ", name=" + name + ", code=" + code + ", rate=" + rate + "]";
	}

}
