package pl.streamsoft.services;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DateCheckService {
	
	public String datacheck(String dates) {
		
		StringToDate stringToDate = new StringToDate();
		
		Date date = stringToDate.conversion(dates);
		Date now = new Date();
		
		
		
		
		if(date.after(now)) {
			System.out.println("Próbujesz sprawdziæ kurs z przysz³oœci");
			date = now;
			
			String today;

				today = new SimpleDateFormat("yyyy-MM-dd").format(now);
				return today;

		}
		return dates; 
		
	}
	

}
