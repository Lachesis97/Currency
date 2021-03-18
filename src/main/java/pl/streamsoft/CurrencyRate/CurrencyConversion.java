/*
 * CurrencyConversion
 *
 * Za pomoc¹ klasy FutureDateCheckService sprawdza czy ktoœ nie poda³ daty z przyszlosci
 *
 * Przekazuje wybrane parametry wyszukiwania do wybranej strategi i finalnie otrzymuje obiekt waluty
 *
 * Na koniec zwraca wynik konwersji wybranej waluty na zlotowki
 *
 */


package pl.streamsoft.CurrencyRate;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.streamsoft.services.Context;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.FutureDateCheckService;
import pl.streamsoft.services.Strategy;


public class CurrencyConversion {
	
	public static BigDecimal conversion(String code, String dateS, BigDecimal amount, Strategy strategy) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if(dateS == "today" || dateS == "dzisiaj" || dateS == "dziœ" || dateS == "" || dateS == "now") {

			dateS = dateFormat.format( new Date() );
			
		} else {
			
			FutureDateCheckService getCurrancyDateService = new FutureDateCheckService();
			dateS = getCurrancyDateService.datacheck(dateS);
			
		}
		
		
		Context context = new Context(strategy);
		
		Currency currency = context.execute(code.toUpperCase(), dateS);
		
		
		return amount.multiply(currency.getRate().setScale(4));
	}

}
