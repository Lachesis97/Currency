package pl.streamsoft.test;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.postgresql.jdbc2.optional.SimpleDataSource;

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.Context;
import pl.streamsoft.services.FutureDateCheckService;
import pl.streamsoft.services.GetCurrancyNbpDateCheckService;

public class GetRightDateTest {
	
	
	@Test
	public void should_return_todays_date() {
		
		//given
		String date = "2022-05-25";
		Calendar convert = Calendar.getInstance();
		convert.set(Calendar.HOUR_OF_DAY, 0);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());
		
		//when
		FutureDateCheckService getCurrancyDateService = new FutureDateCheckService(); 
		String newdate = getCurrancyDateService.datacheck(date);
		
		//then
		Assert.assertEquals(today, newdate);
		
	}
	

	
    
	@Test
	public void should_return_first_date_at_which_the_rate_is_available_if_given_date_doesnt_have_rate() {
		
		//given
		String date = "2021-03-25";
		String code = "eur";
		
		Boolean check = false;
		Calendar convert = Calendar.getInstance();
		convert.set(Calendar.HOUR_OF_DAY, 0);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());
		
		convert.set(Calendar.HOUR_OF_DAY, -1);
		String minus1 = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());
		
		convert.set(Calendar.HOUR_OF_DAY, -25);
		String minus2 = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());
		
		
		//when
		GetCurrancyNbpDateCheckService getCurrancyDateCheckService = new GetCurrancyNbpDateCheckService();
		String checkedDate = getCurrancyDateCheckService.checkDate(code, date);
		
		
		if(checkedDate.equals(today)){
			check = true;
		} else if(checkedDate.equals(minus1)) {
			check = true;
		} else if(checkedDate.equals(minus2)) {
			check = true;
		}
		
		//then
		Assert.assertTrue(check);
				
	}
	
	

}
