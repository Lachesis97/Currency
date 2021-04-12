/*
 * SaleDocumentService
 *
 * 
 *
 */
package pl.streamsoft.currency_rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import pl.streamsoft.services.Currency;

public class SaleDocumentService {

	public void insert() {

		String code = "EUR";
		LocalDate date = LocalDate.of(2021, 4, 8);
		BigDecimal foreignCurrency = new BigDecimal("123.00");

		CurrencyConversion conversion = new CurrencyConversion();
		Currency currency = conversion.conversion(code, date);
		BigDecimal result = currency.currencyToPln(foreignCurrency);
		System.out.println(result.setScale(2, RoundingMode.HALF_UP));

		conversion.conversion("inr", LocalDate.of(2021, 3, 22));
		conversion.conversion("inr", LocalDate.of(2021, 3, 23));
		conversion.conversion("isk", LocalDate.of(2021, 3, 24));
		conversion.conversion("isk", LocalDate.of(2021, 3, 24));
		conversion.conversion("czk", LocalDate.of(2021, 3, 25));
		conversion.conversion("czk", LocalDate.of(2021, 3, 25));
		conversion.conversion("gbp", LocalDate.of(2021, 3, 18));
		conversion.conversion("gbp", LocalDate.of(2021, 3, 18));
		conversion.conversion("usd", LocalDate.of(2021, 3, 17));

//		CountryRepository repository = new CountryRepository();
//
//		repository.deleteCountry("DE");

//		repository.addCountry("AU", "Australia", "AUÆ", "Dolar taki o");

//		List<CountryTable> resultList = repository.getCountriesWithTwoOrMoreCurrencies();
//		for (CountryTable countryTable2 : resultList) {
//			System.out.println("Kod Kraju: " + countryTable2.getCountry_code() + ", Nazwa Kraju: "
//					+ countryTable2.getCountry_name());
//		}

//		CountryTable countryTable = repository.getCountryCurrencyList("PL");
//		for (CurrencyCodeTable currencyCodeTable : countryTable.getCodetable()) {
//			System.out.println("Kod Kraju: " + countryTable.getCountry_code() + ", Nazwa Kraju: "
//					+ countryTable.getCountry_name() + ", Kod waluty: " + currencyCodeTable.getCode()
//					+ ", Pe³na nazwa waluty: " + currencyCodeTable.getName());
//		}

//		CurrencySpecificRequests specificRequests = new CurrencySpecificRequests();
//
//		Currency maxCurrency = specificRequests.getMaxRate("EUR", LocalDate.of(2021, 3, 17), LocalDate.of(2021, 4, 10));
//		System.out.println("Max: " + maxCurrency.getCode() + ", " + maxCurrency.getName() + ", " + maxCurrency.getDate()
//				+ ", " + maxCurrency.getRate());
//		Currency minCurrency = specificRequests.getMinRate("EUR", LocalDate.of(2021, 3, 17), LocalDate.of(2021, 4, 10));
//		System.out.println("Min: " + minCurrency.getCode() + ", " + minCurrency.getName() + ", " + minCurrency.getDate()
//				+ ", " + minCurrency.getRate());
//
//		List<CurrencyRatesTable> bestFive = specificRequests.getFiveBestRates("TEST");
//		List<CurrencyRatesTable> worstFive = specificRequests.getFiveWorstRates("TEST");
//
//		int i = 1;
//		for (CurrencyRatesTable currencyRatesTable : bestFive) {
//			System.out.println("Best " + i + ": " + currencyRatesTable.getCode() + ", " + currencyRatesTable.getDate()
//					+ ", " + currencyRatesTable.getRate());
//			i++;
//		}
//		i = 1;
//		for (CurrencyRatesTable currencyRatesTable : worstFive) {
//			System.out.println("Worst " + i + ": " + currencyRatesTable.getCode() + ", " + currencyRatesTable.getDate()
//					+ ", " + currencyRatesTable.getRate());
//			i++;
//		}
//
	}

}
