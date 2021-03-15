package pl.streamsoft.www;

import java.math.BigDecimal;
import java.sql.Date;

public class Currency {
	
	String code;
	String name;
	BigDecimal reate = new BigDecimal("");
	Date date;
	
	public Currency(String code, String name, BigDecimal reate, Date date) {
		super();
		this.code = code;
		this.name = name;
		this.reate = reate;
		this.date = date;
	}
	
	
	
	
	
	

}
