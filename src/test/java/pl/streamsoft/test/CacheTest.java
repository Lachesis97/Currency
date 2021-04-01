package pl.streamsoft.test;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.CurrencyRate.CurrencyConversion;
import pl.streamsoft.services.Currency;

public class CacheTest {

	@Test
	public void should_add_to_cache() {

		// given
		CurrencyConversion conversion = new CurrencyConversion();

		Assertions.assertThat(conversion.getCache().get("eur2021-03-22")).isNull();

		// when

		Currency currency = conversion.conversion("eur", LocalDate.of(2021, 3, 22));

		// then
		Assertions.assertThat(conversion.getCache().get("eur2021-03-22")).isNotNull();
		Assertions.assertThat(conversion.getCache().get("eur2021-03-22")).isEqualTo(currency);

	}

	@Test
	public void should_cache_with_specified_number_of_recent_entries() {

		// given
		int maxEntries = 2;
		String key1 = "eur2021-03-22";
		String key2 = "eur2021-03-23";
		String key3 = "eur2021-03-24";
		CurrencyConversion conversion = new CurrencyConversion(maxEntries);

		Assertions.assertThat(conversion.getCache().get(key1)).isNull();
		Assertions.assertThat(conversion.getCache().get(key2)).isNull();
		Assertions.assertThat(conversion.getCache().get(key3)).isNull();

		// when
		Currency currency1 = conversion.conversion("eur", LocalDate.of(2021, 3, 22));
		Currency currency2 = conversion.conversion("eur", LocalDate.of(2021, 3, 23));
		Currency currency3 = conversion.conversion("eur", LocalDate.of(2021, 3, 24));

		// then
		Assertions.assertThat(conversion.getCache().get(key1)).isNull();
		Assertions.assertThat(conversion.getCache().get(key2)).isEqualTo(currency2);
		Assertions.assertThat(conversion.getCache().get(key3)).isEqualTo(currency3);

	}

	@Test
	public void should_clear_cache() {

		// given
		CurrencyConversion conversion = new CurrencyConversion();
		conversion.conversion("eur", LocalDate.of(2021, 3, 22));
		Assertions.assertThat(conversion.getCache().get("eur2021-03-22")).isNotNull();

		// when

		conversion.clearCache();

		// then
		Assertions.assertThat(conversion.getCache().get("eur2021-03-22")).isNull();

	}

}
