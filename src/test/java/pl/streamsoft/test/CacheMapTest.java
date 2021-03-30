package pl.streamsoft.test;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.CurrencyRate.CurrencyConversion;

public class CacheMapTest {

	@Test
	public void should_add_to_cache() {

		// given
		CurrencyConversion conversion = new CurrencyConversion();

		Assertions.assertThat(conversion.getCache()).isEmpty();

		// when

		conversion.conversion("eur", LocalDate.of(2021, 3, 22), new BigDecimal("1"));

		// then
		Assertions.assertThat(conversion.getCache()).isNotEmpty();

	}

	@Test
	public void should_get_cache_with_unique_currency_objects() {

		// given
		CurrencyConversion conversion = new CurrencyConversion();
		Assertions.assertThat(conversion.getCache()).isEmpty();

		// when
		conversion.conversion("eur", LocalDate.of(2021, 3, 22), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 22), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 23), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 23), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 24), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 24), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 25), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 25), new BigDecimal("1"));

		// then
		Assertions.assertThat(conversion.getCache()).size().isEqualTo(4);

	}

	@Test
	public void should_cache_with_specified_number_of_recent_entries() {

		// given
		int maxEntries = 2;

		CurrencyConversion conversion = new CurrencyConversion(maxEntries);

		// when
		conversion.conversion("eur", LocalDate.of(2021, 3, 22), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 23), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 24), new BigDecimal("1"));
		conversion.conversion("eur", LocalDate.of(2021, 3, 25), new BigDecimal("1"));

		// then
		Assertions.assertThat(conversion.getCache()).size().isEqualTo(2);

	}

}
