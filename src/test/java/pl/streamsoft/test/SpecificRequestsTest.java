package pl.streamsoft.test;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.data_base_services.CurrencyRatesTable;
import pl.streamsoft.data_base_services.CurrencySpecificRequests;
import pl.streamsoft.services.Currency;

public class SpecificRequestsTest {

	@Test
	public void should_return_max_rate_for_specific_currency() {

		// given
		clearTestDB();
		CurrencySpecificRequests specificRequests = new CurrencySpecificRequests();
		Currency currency1 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 14), new BigDecimal("10.00"));
		Currency expectedMaxRate = new Currency("Test", "TEST", LocalDate.of(1997, 11, 15), new BigDecimal("3000.23"));
		Currency currency3 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 16), new BigDecimal("200.00"));
		specificRequests.addCurrency(currency1);
		specificRequests.addCurrency(expectedMaxRate);
		specificRequests.addCurrency(currency3);

		// when
		Currency maxRate = specificRequests.getMaxRate("TEST", LocalDate.of(1997, 11, 14), LocalDate.of(1997, 11, 16));

		// then
		Assertions.assertThat(expectedMaxRate.getCode().equals(maxRate.getCode())
				&& expectedMaxRate.getName().equals(maxRate.getName())
				&& expectedMaxRate.getDate().equals(maxRate.getDate())
				&& expectedMaxRate.getRate().equals(maxRate.getRate()));

	}

	@Test
	public void should_return_min_rate_for_specific_currency() {

		// given
		clearTestDB();
		CurrencySpecificRequests specificRequests = new CurrencySpecificRequests();
		Currency expectedMaxRate = new Currency("Test", "TEST", LocalDate.of(1997, 11, 14), new BigDecimal("10.00"));
		Currency currency2 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 15), new BigDecimal("3000.23"));
		Currency currency3 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 16), new BigDecimal("200.00"));
		specificRequests.addCurrency(currency2);
		specificRequests.addCurrency(expectedMaxRate);
		specificRequests.addCurrency(currency3);

		// when
		Currency maxRate = specificRequests.getMinRate("TEST", LocalDate.of(1997, 11, 14), LocalDate.of(1997, 11, 16));

		// then
		Assertions.assertThat(expectedMaxRate.getCode().equals(maxRate.getCode())
				&& expectedMaxRate.getName().equals(maxRate.getName())
				&& expectedMaxRate.getDate().equals(maxRate.getDate())
				&& expectedMaxRate.getRate().equals(maxRate.getRate()));

	}

	@Test
	public void should_return_list_of_the_best_five_rates() {

		// given
		clearTestDB();
		CurrencySpecificRequests specificRequests = new CurrencySpecificRequests();
		Currency currency1 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 14), new BigDecimal("1.11"));
		Currency currency2 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 15), new BigDecimal("22.22"));
		Currency currency3 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 16), new BigDecimal("333.33"));
		Currency currency4 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 17), new BigDecimal("4444.44"));
		Currency currency5 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 18), new BigDecimal("55555.55"));
		Currency currency6 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 19), new BigDecimal("666666.66"));
		specificRequests.addCurrency(currency1);
		specificRequests.addCurrency(currency2);
		specificRequests.addCurrency(currency3);
		specificRequests.addCurrency(currency4);
		specificRequests.addCurrency(currency5);
		specificRequests.addCurrency(currency6);

		// when
		List<CurrencyRatesTable> dbResult = specificRequests.getFiveBestRates("TEST");
		// System.out.println(dbResult.get(5));

		// then
		Assertions.assertThat(dbResult.size() == 5);
		Assertions.assertThat(dbResult.get(0).getDate().equals(currency6.getDate())
				&& dbResult.get(0).getRate().equals(currency6.getRate())
				&& dbResult.get(1).getDate().equals(currency5.getDate())
				&& dbResult.get(1).getRate().equals(currency5.getRate())
				&& dbResult.get(2).getDate().equals(currency4.getDate())
				&& dbResult.get(2).getRate().equals(currency4.getRate())
				&& dbResult.get(3).getDate().equals(currency3.getDate())
				&& dbResult.get(3).getRate().equals(currency3.getRate())
				&& dbResult.get(4).getDate().equals(currency2.getDate())
				&& dbResult.get(4).getRate().equals(currency2.getRate()));

		Throwable thrown = catchThrowable(() -> dbResult.get(5));
		Assertions.assertThat(thrown).isInstanceOf(IndexOutOfBoundsException.class);

	}

	@Test
	public void should_return_list_of_the_worst_five_rates() {

		// given
		clearTestDB();
		CurrencySpecificRequests specificRequests = new CurrencySpecificRequests();
		Currency currency1 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 14), new BigDecimal("1.11"));
		Currency currency2 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 15), new BigDecimal("22.22"));
		Currency currency3 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 16), new BigDecimal("333.33"));
		Currency currency4 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 17), new BigDecimal("4444.44"));
		Currency currency5 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 18), new BigDecimal("55555.55"));
		Currency currency6 = new Currency("Test", "TEST", LocalDate.of(1997, 11, 19), new BigDecimal("666666.66"));
		specificRequests.addCurrency(currency1);
		specificRequests.addCurrency(currency2);
		specificRequests.addCurrency(currency3);
		specificRequests.addCurrency(currency4);
		specificRequests.addCurrency(currency5);
		specificRequests.addCurrency(currency6);

		// when
		List<CurrencyRatesTable> dbResult = specificRequests.getFiveWorstRates("TEST");

		// then
		Assertions.assertThat(dbResult.size() == 5);
		Assertions.assertThat(dbResult.get(0).getDate().equals(currency1.getDate())
				&& dbResult.get(0).getRate().equals(currency1.getRate())
				&& dbResult.get(1).getDate().equals(currency2.getDate())
				&& dbResult.get(1).getRate().equals(currency2.getRate())
				&& dbResult.get(2).getDate().equals(currency3.getDate())
				&& dbResult.get(2).getRate().equals(currency3.getRate())
				&& dbResult.get(3).getDate().equals(currency4.getDate())
				&& dbResult.get(3).getRate().equals(currency4.getRate())
				&& dbResult.get(4).getDate().equals(currency5.getDate())
				&& dbResult.get(4).getRate().equals(currency5.getRate()));

		Throwable thrown = catchThrowable(() -> dbResult.get(5));
		Assertions.assertThat(thrown).isInstanceOf(IndexOutOfBoundsException.class);

	}

	public void clearTestDB() {

		CurrencySpecificRequests specificRequests = new CurrencySpecificRequests();
		specificRequests.deleteCurrency("TEST");

	}

}
