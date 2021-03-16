package pl.streamsoft.www;

import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) {
		
		//Context context = new Context(new GetCurrencyJson());
		
		//Currency currency = context.execute("USD");
		
		
//		System.out.println(currency.getName());
//      System.out.println(currency.getCode());
//      System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(currency.getDate()));
//      System.out.println(currency.getRate());
        
        SaleDocumentService.insert();
        
		
	}

}
