package pl.streamsoft.services;

import java.util.Date;

public interface Strategy {
	
	public Currency getCurrency(String code, String date);

}
