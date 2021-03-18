package pl.streamsoft.services;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class FutureDateCheckService {
	
	public String datacheck(String dates) {
		
		StringToDate stringToDate = new StringToDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		
		Date now = new Date();
		
		
		if(dates.equals("today") || dates.equals("dzisiaj") || dates.equals("dzi�") || dates.equals("") || dates.equals("now") || dates.equals("teraz")) {

			dates = dateFormat.format( new Date() );
			return dates;
			
		} 
		
		Date date = stringToDate.conversion(dates);
		
		if(date.after(now)) {
			System.out.println("Pr�bujesz sprawdzi� kurs z przysz�o�ci");
			date = now;
			
			String today;

				today = new SimpleDateFormat("yyyy-MM-dd").format(now);
				return today;

		}
		
		return dates; 
		
	}
	

}
