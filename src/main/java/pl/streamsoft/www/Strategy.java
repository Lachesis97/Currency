package pl.streamsoft.www;

import java.util.Date;

public interface Strategy {
	
	public Currency getCurrency(String code, Date date);

}
