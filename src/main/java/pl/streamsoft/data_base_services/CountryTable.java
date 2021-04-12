package pl.streamsoft.data_base_services;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CountryTable")
@NamedQueries({
		@NamedQuery(name = "CountryTable.GetCountry", query = "SELECT country FROM CountryTable country WHERE country.country_code = :country_code AND country.country_name = :country_name"),
		@NamedQuery(name = "CountryTable.CountryHaveRate", query = "SELECT country FROM CountryTable country JOIN country.codetable code WHERE code.code = :code AND country.country_code = :country_code"),
		@NamedQuery(name = "CountryTable.GetCountryCurrencyList", query = "SELECT country FROM CountryTable country JOIN FETCH country.codetable code WHERE country.country_code = :country_code"),
		@NamedQuery(name = "CountryTable.GetCountriesWithTwoOrMoreCurrencies", query = "SELECT country FROM CountryTable country JOIN country.codetable code GROUP BY country.country_code HAVING COUNT(code) > 1") })

public class CountryTable implements Serializable {

	@Id
	@Column(name = "Country_Code", unique = true)
	private String country_code;

	@Column(name = "Country_Name", unique = true)
	private String country_name;

	@ManyToMany(targetEntity = CurrencyCodeTable.class)
	@JoinColumn(name = "f_code", referencedColumnName = "code")
	private Set<CurrencyCodeTable> codetable = new HashSet<CurrencyCodeTable>();

	public CountryTable() {

	}

	public CountryTable(String country_code, String country_name, Set<CurrencyCodeTable> codetable) {
		super();
		this.country_code = country_code;
		this.country_name = country_name;
		this.codetable = codetable;

	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public Set<CurrencyCodeTable> getCodetable() {
		return codetable;
	}

	public void setCodetable(Set<CurrencyCodeTable> codetable) {
		this.codetable = codetable;
	}

}
