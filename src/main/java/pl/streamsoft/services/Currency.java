package pl.streamsoft.services;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "currency", "code", "effectiveDate", "mid" })

@JsonIgnoreProperties({ "table", "no", })

public class Currency {

	@JsonProperty("currency")
	String name;

	@JsonProperty("code")
	String code;

	@JsonProperty("effectiveDate")
	LocalDate date;

	@JsonProperty("mid")
	BigDecimal rate;

	public Currency(String name, String code, LocalDate date, BigDecimal rate) {

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getRate() {
		return rate.setScale(4);
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}
