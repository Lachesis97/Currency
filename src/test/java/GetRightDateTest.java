import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.postgresql.jdbc2.optional.SimpleDataSource;

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.Context;
import pl.streamsoft.services.FutureDateCheckService;
import pl.streamsoft.services.GetCurrancyDateCheckService;

public class GetRightDateTest {
	
	FutureDateCheckService getCurrancyDateService = new FutureDateCheckService(); 
	
	@Test
	public void should_return_todays_date() {
		
		//given
		String date = "2022-05-25";
		Calendar convert = Calendar.getInstance();
		convert.set(Calendar.HOUR_OF_DAY, 0);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());
		
		//when
		String newdate = getCurrancyDateService.datacheck(date);
		
		//then
		Assert.assertEquals(today, newdate);
		
	}
	
	
	
	GetCurrancyDateCheckService getCurrancyDateCheckService = new GetCurrancyDateCheckService();
    
	@Test
	public void should_return_first_date_at_which_the_rate_is_available_if_given_date_doesnt_have_rate() {
		
		//given
		String date = "2021-05-25";
		String code = "eur";
		
		Calendar convert = Calendar.getInstance();
		convert.set(Calendar.HOUR_OF_DAY, 0);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());
		
		//when
		String checkedDate = getCurrancyDateCheckService.checkDate(code, date);
		
		//then
		Assert.assertEquals(today, checkedDate);
		
		
		
	}
	
	

}
