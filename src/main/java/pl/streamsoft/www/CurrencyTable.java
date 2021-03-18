package pl.streamsoft.www;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries(
		@NamedQuery(
				name = "GetCurrencyDB", 
				query = "SELECT c FROM CurrencyTable c WHERE c.code = :code AND c.date = :date"
				)
		)
@Table(name = "CurrencyTable")
public class CurrencyTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "Name", nullable = false)
	private String name;
	
	@Column(name = "Code", nullable = false)
	private String code;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Date", nullable = false)
	private Date date;
	
	@Column(name = "Rate", nullable = false, columnDefinition="DECIMAL(19,4)")
	private BigDecimal rate;

	public CurrencyTable() {
		
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}





