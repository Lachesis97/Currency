package pl.streamsoft.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.streamsoft.exceptions.StringToDateException;

public class StringToDate {


	public static Date conversion(String dateS) {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			
			date = sdf1.parse(dateS);
			
			return date;
		} catch (ParseException e) {
			throw new StringToDateException("B³¹d konwersji string to date / StringToDate.java");
		}	

		
		
	}
	
}
