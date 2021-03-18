package pl.streamsoft.www;

import java.math.BigDecimal;
import java.util.Date;

import pl.streamsoft.services.Context;
import pl.streamsoft.services.Strategy;

public class CurrencyConversion {
	
	public static void conversion(String code, String date, BigDecimal amount, Strategy strategy) {
		
		Context context = new Context(strategy);
		
		Currency currency = context.execute(code, date);
		
		System.out.println(amount +" "+ currency.getName() + " to " + amount.multiply(currency.getRate())+" z³otych polskich");
		
		
	}

}
