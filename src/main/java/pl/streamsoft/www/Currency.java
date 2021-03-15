package pl.streamsoft.www;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({
"currency",
"code",
"effectiveDate",
"mid"
})

@JsonIgnoreProperties({
	"table",
	"no",
})

public class Currency {
	
	@JsonProperty("currency")
	String name;
	
	@JsonProperty("code")
	String code;
	
	@JsonProperty("effectiveDate")
	Date date;
	
	@JsonProperty("mid")
	BigDecimal rate; 
	
	public Currency(String name, String code, Date date, BigDecimal rate) {
		
		this.name = name;
		this.code = code;
		this.date = date;
		this.rate = rate;
	}
	
	public Currency() {
		super();
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
