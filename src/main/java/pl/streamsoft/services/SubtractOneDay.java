package pl.streamsoft.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SubtractOneDay {
	
	public Date substract(Date newdate) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(newdate);
		cal.add(Calendar.DATE, -1);
		newdate = cal.getTime();	
		
		return newdate;
	}

}
