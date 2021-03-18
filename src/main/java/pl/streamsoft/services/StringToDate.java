package pl.streamsoft.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {


	public Date conversion(String dateS) {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			
			date = sdf1.parse(dateS);
			
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
		
		
	}
	
}
