package pl.streamsoft.test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import pl.streamsoft.CurrencyRate.CurrencyConversion;
import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.FutureDateCheckService;
import pl.streamsoft.services.Strategy;

public class GatRightCurrencyConversion {

	
	
	@Test
	public void should_return_right_bidecimal_value() {
		
		//given
		String code1 = "eur";
		String code2 = "usd";
		String code3 = "gbp";
		String dateS1 = "2021-03-18";
		String dateS2 = "2021-03-15";
		String dateS3 = "2021-03-14";
		BigDecimal zlotowki1 = new BigDecimal("123.00");
		BigDecimal zlotowki2 = new BigDecimal("83.45");
		BigDecimal zlotowki3 = new BigDecimal("21.37");
		String urlNBP = "http://api.nbp.pl/api/exchangerates/rates/a/";
		Strategy strategy = new GetCurrencyJsonNBP(urlNBP);
		
		
		//when
		BigDecimal result1 = CurrencyConversion.conversion(code1, dateS1, zlotowki1, strategy);
		//BigDecimal result2 = CurrencyConversion.conversion(code2, dateS2, zlotowki2, strategy);
		//BigDecimal result3 = CurrencyConversion.conversion(code3, dateS3, zlotowki3, strategy);
		
		//then
		Assert.assertTrue(result1.equals(new BigDecimal("568.5552000")));

		
	}
	
	
	
	
	
}
