package pl.streamsoft.services;

import java.util.Date;

import pl.streamsoft.www.Currency;

public interface Strategy {
	
	public Currency getCurrency(String code, String date);

}
