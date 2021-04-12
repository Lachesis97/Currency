package pl.streamsoft.data_base_services;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CurrencyCodeTable")
@NamedQueries({
		@NamedQuery(name = "CurrencyCodeTable.GetCurrency", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE c.code = :code AND r.date = :date"),
		@NamedQuery(name = "CurrencyCodeTable.GetCode", query = "SELECT c FROM CurrencyCodeTable c WHERE c.code = :code"),
		@NamedQuery(name = "CurrencyCodeTable.GetMaxRate", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE c.code = :code AND r.rate = (SELECT MAX(r.rate) FROM CurrencyRatesTable r WHERE r.code = :code AND (r.date BETWEEN :start AND :end))"),
		@NamedQuery(name = "CurrencyCodeTable.GetMinRate", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE c.code = :code AND r.rate = (SELECT MIN(r.rate) FROM CurrencyRatesTable r WHERE r.code = :code AND (r.date BETWEEN :start AND :end))") })

public class CurrencyCodeTable implements Serializable {

	@Id
	@Column(name = "Code", unique = true)
	private String code;

	@Column(name = "Name", unique = true)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = CurrencyRatesTable.class, mappedBy = "codetable", orphanRemoval = true)
	private Set<CurrencyRatesTable> rate = new HashSet<CurrencyRatesTable>();

	@ManyToMany(targetEntity = CountryTable.class, mappedBy = "codetable")
	private Set<CurrencyRatesTable> country = new HashSet<CurrencyRatesTable>();

	public CurrencyCodeTable() {

	}

	public CurrencyCodeTable(String code, String name) {

		this.code = code;
		this.name = name;
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
		this.rate = rate;
	}

}
