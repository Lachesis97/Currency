package pl.streamsoft.www;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) throws Exception {
		
		GetCurrencyJson getcurr = new GetCurrencyJson();

		BigDecimal PLN = new BigDecimal("1000.23");
		
		Context context = new Context(new GetCurrencyJson());
		
		
		
		
		try {
		
			context.execute("EUR");
			
		} catch (Exception e) {

		
		} finally {
			getcurr.close();
		}
		
		
	}

}
