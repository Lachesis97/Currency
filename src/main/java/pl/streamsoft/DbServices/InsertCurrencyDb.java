package pl.streamsoft.DbServices;

import java.time.LocalDate;

import pl.streamsoft.services.Currency;

public class InsertCurrencyDb {

	public void insert(Currency currency, String code, LocalDate date) {

		// InsertCurrencyDbService insertCurrencyDbService = new
		// InsertCurrencyDbService(code, date);

		// if(insertCurrencyDbService.itExistCode()) {

		CurrencyCodeTablePersist codeTablePersist = new CurrencyCodeTablePersist();
		codeTablePersist.persist(currency.getCode(), currency.getName());

		// }

		// if(insertCurrencyDbService.itExistRate()) {

		CurrencyRatesTablePersist ratesTablePersist = new CurrencyRatesTablePersist();
		ratesTablePersist.persist(currency.getDate(), currency.getRate());

		// }

		System.out.println(
				"Dodano kurs \"" + currency.getName() + "\" z dnia \"" + currency.getDate() + "\" do bay danych.");

	}

}
