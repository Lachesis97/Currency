package pl.streamsoft.www;

import java.math.BigDecimal;

public class CurrencyConversion {
	
	public static BigDecimal conversion(String code,BigDecimal amount) {
		
		Context context = new Context(new GetCurrencyJson());
		
		Currency currency = context.execute(code);
		
		
		return amount.multiply(currency.getRate());
		
	}

}
