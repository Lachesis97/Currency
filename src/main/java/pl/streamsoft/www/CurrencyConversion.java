package pl.streamsoft.www;

import java.math.BigDecimal;
import java.util.Date;

public class CurrencyConversion {
	
	public static void conversion(String code,Date date, BigDecimal amount) {
		
		Context context = new Context(new GetCurrencyJson());
		
		Currency currency = context.execute(code, date);
		
		System.out.println(amount +" "+ currency.getName() + " to " + amount.multiply(currency.getRate())+" z³otych polskich");
		
		
	}

}
