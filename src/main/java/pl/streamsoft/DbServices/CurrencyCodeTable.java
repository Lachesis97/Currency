package pl.streamsoft.DbServices;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
//@NamedQueries(
//		{
//		@NamedQuery(
//				name = "GetCurrencyCodeID", 
//				query = "SELECT c.id FROM CurrencyCodeTable c WHERE c.code = :code"
//				),
//		@NamedQuery(
//				name = "GetCurrencyDB", 
//				query = "SELECT c.code, c.name, r.date, r.rate FROM CurrencyCodeTable c, CurrencyRatesTable r WHERE c.id = r.codeid AND c.code = :code AND r.date = :date"
//				)
//		}
//		)
@Table(name = "CurrencyCodeTable")
public class CurrencyCodeTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "Name", nullable = false)
	private String name;
	
	@Column(name = "Code", nullable = false)
	private String code;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = CurrencyRatesTable.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID", referencedColumnName = "CodeID")
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

}





