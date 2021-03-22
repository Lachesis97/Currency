package pl.streamsoft.test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import pl.streamsoft.services.FutureDateCheckService;
import pl.streamsoft.services.GetCurrancyNbpDateCheckService;

public class GetRightDateTest {

	@Test
	public void should_return_todays_date() {

		// given
		LocalDate date = LocalDate.now();
		Calendar convert = Calendar.getInstance();
		convert.set(Calendar.HOUR_OF_DAY, 0);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());

		// when
		FutureDateCheckService getCurrancyDateService = new FutureDateCheckService();
		LocalDate newdate = getCurrancyDateService.datacheck(date);

		// then
		Assert.assertEquals(today, newdate);

	}

	@Test
	public void should_return_first_date_at_which_the_rate_is_available_if_given_date_doesnt_have_rate() {

		// given
		LocalDate date = LocalDate.of(2021, 3, 25);
		String code = "eur";

		Boolean check = false;
		Calendar convert = Calendar.getInstance();
		convert.set(Calendar.HOUR_OF_DAY, 0);
		String today = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());

		convert.set(Calendar.HOUR_OF_DAY, -1);
		String minus1 = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());

		convert.set(Calendar.HOUR_OF_DAY, -25);
		String minus2 = new SimpleDateFormat("yyyy-MM-dd").format(convert.getTime());

		// when
		GetCurrancyNbpDateCheckService getCurrancyDateCheckService = new GetCurrancyNbpDateCheckService();
		LocalDate checkedDate = getCurrancyDateCheckService.checkDate(code, date);

		if (checkedDate.equals(today)) {
			check = true;
		} else if (checkedDate.equals(minus1)) {
			check = true;
		} else if (checkedDate.equals(minus2)) {
			check = true;
		}

		// then
		Assert.assertTrue(check);

	}

}
