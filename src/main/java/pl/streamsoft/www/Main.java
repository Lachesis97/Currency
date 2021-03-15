package pl.streamsoft.www;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) {
		
		GetCurrencyJson getcurr = new GetCurrencyJson();

		BigDecimal PLN = new BigDecimal("1000.23");
		
		Context context = new Context(new GetCurrencyJson());
		
		Currency currency = context.execute("EUR");
		
		
		System.out.println(currency.getName());
        System.out.println(currency.getCode());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(currency.getDate()));
        System.out.println(currency.getRate());
		
	}

}
