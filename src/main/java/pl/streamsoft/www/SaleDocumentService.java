package pl.streamsoft.www;

import java.math.BigDecimal;

public class SaleDocumentService {

	
	
	public static void insert() {
		
		System.out.println(CurrencyConversion.conversion("USD", new BigDecimal("10")));
			
	}

}
