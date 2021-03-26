package pl.streamsoft.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.CurrencyRate.CurrencyConversion;
import pl.streamsoft.services.Currency;

public class CacheMapTest {

	@Test
	public void should_return_same_object_from_cache() {

		// given
		String code = "eur";
		int maxEntries = 10;

		Map<String, Currency> cache = new LinkedHashMap<String, Currency>();
		CurrencyConversion conversion = new CurrencyConversion(maxEntries);

		// when

		conversion.conversion(code, LocalDate.of(2021, 3, 22), new BigDecimal("1"));
		Currency currency = conversion.getExistCurrency();

		conversion.conversion(code, LocalDate.of(2021, 3, 22), new BigDecimal("1"));
		cache.put(code + LocalDate.of(2021, 3, 22).toString(), conversion.getExistCurrency());

		// then
		Assertions.assertThat(currency).isEqualTo(null);
		Assertions.assertThat(conversion.getCache()).isEqualTo(cache);

	}

	@Test
	public void should_return_map_with_unique_currency_objects() {

		// given
		String code = "eur";
		int maxEntries = 10;

		Map<String, Currency> cache = new LinkedHashMap<String, Currency>();
		CurrencyConversion conversion = new CurrencyConversion(maxEntries);

		// when

		conversion.conversion(code, LocalDate.of(2021, 3, 22), new BigDecimal("1"));
		cache.put(code + LocalDate.of(2021, 3, 22).toString(), conversion.getNewCurrency());

		conversion.conversion(code, LocalDate.of(2021, 3, 23), new BigDecimal("1"));
		cache.put(code + LocalDate.of(2021, 3, 23).toString(), conversion.getNewCurrency());

		conversion.conversion(code, LocalDate.of(2021, 3, 24), new BigDecimal("1"));
		cache.put(code + LocalDate.of(2021, 3, 24).toString(), conversion.getNewCurrency());

		conversion.conversion(code, LocalDate.of(2021, 3, 25), new BigDecimal("1"));
		cache.put(code + LocalDate.of(2021, 3, 25).toString(), conversion.getNewCurrency());

		conversion.conversion(code, LocalDate.of(2021, 3, 22), new BigDecimal("1"));
		conversion.conversion(code, LocalDate.of(2021, 3, 23), new BigDecimal("1"));
		conversion.conversion(code, LocalDate.of(2021, 3, 24), new BigDecimal("1"));

		// then
		Assertions.assertThat(conversion.getCache()).isEqualTo(cache);

	}

	@Test
	public void should_return_specified_number_of_recent_entries() {

		// given
		String code = "eur";
		int maxEntries = 2;

		Map<String, Currency> cache = new LinkedHashMap<String, Currency>();
		CurrencyConversion conversion = new CurrencyConversion(maxEntries);

		// when
		conversion.conversion(code, LocalDate.of(2021, 3, 22), new BigDecimal("1"));

		conversion.conversion(code, LocalDate.of(2021, 3, 23), new BigDecimal("1"));

		conversion.conversion(code, LocalDate.of(2021, 3, 24), new BigDecimal("1"));
		cache.put(code + LocalDate.of(2021, 3, 24).toString(), conversion.getNewCurrency());

		conversion.conversion(code, LocalDate.of(2021, 3, 25), new BigDecimal("1"));
		cache.put(code + LocalDate.of(2021, 3, 25).toString(), conversion.getNewCurrency());

		// then
		Assertions.assertThat(conversion.getCache()).isEqualTo(cache);

	}

}
