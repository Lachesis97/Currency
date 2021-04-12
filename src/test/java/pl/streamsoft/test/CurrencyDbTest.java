package pl.streamsoft.test;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.get_or_save_data.CurrencyRepository;
import pl.streamsoft.services.Currency;

public class CurrencyDbTest {

	@Test
	public void should_return_true_if_code_exist() {

		// given
		Currency currency = new Currency("NAZWA", "KOD", LocalDate.of(2021, 5, 23), new BigDecimal("1"));
		CurrencyRepository currencyRepository = new CurrencyRepository();
		currencyRepository.addCurrency(currency);

		// when
		Boolean result1 = currencyRepository.codeExists("KOD");
		Boolean result2 = currencyRepository.codeExists("tak");

		// then
		Assertions.assertThat(result1).isEqualTo(true);
		Assertions.assertThat(result2).isEqualTo(false);

	}

	@Test
	public void should_return_true_if_rate_exist() {

		// given
		Currency currency = new Currency("NAZWA", "KOD", LocalDate.of(2021, 5, 23), new BigDecimal("1"));
		CurrencyRepository currencyRepository = new CurrencyRepository();
		currencyRepository.addCurrency(currency);

		// when
		Boolean result1 = currencyRepository.rateExists("KOD", LocalDate.of(2021, 5, 23));
		Boolean result2 = currencyRepository.rateExists("tak", LocalDate.of(2021, 5, 23));

		// then
		Assertions.assertThat(result1).isEqualTo(true);
		Assertions.assertThat(result2).isEqualTo(false);

	}

	@Test
	public void should_add_and_then_delete_single_rate() {

		// given
		Currency currency = new Currency("NAZWA", "KOD", LocalDate.of(2021, 5, 23), new BigDecimal("1"));
		CurrencyRepository currencyRepository = new CurrencyRepository();
		currencyRepository.addCurrency(currency);
		Assertions.assertThat(currencyRepository.codeExists("KOD")).isEqualTo(true);
		Assertions.assertThat(currencyRepository.rateExists("KOD", LocalDate.of(2021, 5, 23))).isEqualTo(true);

		// when
		currencyRepository.deleteSingleRate("KOD", LocalDate.of(2021, 5, 23));

		// then
		Assertions.assertThat(currencyRepository.rateExists("KOD", LocalDate.of(2021, 5, 23))).isEqualTo(false);

	}

	@Test
	public void should_add_and_then_delete_currency_obj() {

		// given
		Currency currency = new Currency("NAZWA", "KOD", LocalDate.of(2021, 5, 23), new BigDecimal("1"));
		CurrencyRepository currencyRepository = new CurrencyRepository();
		currencyRepository.addCurrency(currency);
		Assertions.assertThat(currencyRepository.codeExists("KOD")).isEqualTo(true);
		Assertions.assertThat(currencyRepository.rateExists("KOD", LocalDate.of(2021, 5, 23))).isEqualTo(true);

		// when
		currencyRepository.deleteCurrency("KOD");

		// then
		Assertions.assertThat(currencyRepository.codeExists("KOD")).isEqualTo(false);
		Assertions.assertThat(currencyRepository.rateExists("KOD", LocalDate.of(2021, 5, 23))).isEqualTo(false);

	}

	@Test
	public void should_return_currency_obj_from_db() {

		// given
		Currency currency = new Currency("NAZWA", "KOD", LocalDate.of(2021, 5, 23), new BigDecimal("1"));
		CurrencyRepository currencyRepository = new CurrencyRepository();
		currencyRepository.addCurrency(currency);
		Assertions.assertThat(currencyRepository.rateExists("KOD", LocalDate.of(2021, 5, 23))).isEqualTo(true);
		Assertions.assertThat(currencyRepository.codeExists("KOD")).isEqualTo(true);

		// when

		Currency currencyFromDB = currencyRepository.getCurrency("KOD", LocalDate.of(2021, 5, 23));

		// then
		Assertions.assertThat(currency.getCode().equals(currencyFromDB.getCode())
				&& currency.getName().equals(currencyFromDB.getName())
				&& currency.getDate().equals(currencyFromDB.getDate())
				&& currency.getRate().equals(currencyFromDB.getRate()));

	}

}
