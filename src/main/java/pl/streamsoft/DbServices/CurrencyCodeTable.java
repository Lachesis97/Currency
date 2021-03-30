package pl.streamsoft.DbServices;

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
		@NamedQuery(name = "CurrencyCodeTable.GetCurrency", query = "SELECT c FROM CurrencyCodeTable c JOIN FETCH c.rate r WHERE (c.code = :code) AND (r.date = :date)"),
		@NamedQuery(name = "CurrencyCodeTable.GetCode", query = "SELECT c FROM CurrencyCodeTable c WHERE c.code = :code") })

public class CurrencyCodeTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "Name", nullable = false, unique = true)
	private String name;

	@Column(name = "Code", nullable = false, unique = true)
	private String code;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = CurrencyRatesTable.class, cascade = CascadeType.ALL)
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
}
