package pl.streamsoft.test;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.data_converters.AdaptJsonStringNBP;
import pl.streamsoft.data_converters.NbpJsonConverter;
import pl.streamsoft.exceptions.MappingJsonException;
import pl.streamsoft.services.Currency;

public class NbpJsonConverterTest {

	@Test
	public void should_return_correct_json_string_ready_for_mapping() {

		// given
		String NBPJson = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"050/A/NBP/2021\",\"effectiveDate\":\"2021-03-15\",\"mid\":4.5836}]}";
		String NBPConvertedJson = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"no\":\"050/A/NBP/2021\",\"effectiveDate\":\"2021-03-15\",\"mid\":4.5836}";

		// when
		String convertedJson = AdaptJsonStringNBP.adapt(NBPJson);

		// then
		Assertions.assertThat(NBPConvertedJson).isEqualTo(convertedJson);

	}

	@Test
	public void should_return_correct_mapped_currency_object() {

		// given
		String result = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"050/A/NBP/2021\",\"effectiveDate\":\"2021-03-15\",\"mid\":4.5836}]}";
		String shouldReturnCode = "EUR";
		String shouldReturnName = "euro";
		LocalDate shouldReturnDate = LocalDate.of(2021, 3, 15);
		BigDecimal shouldReturnRateBigDecimal = new BigDecimal("4.5836");
		Currency expectedCurrency = new Currency(shouldReturnName, shouldReturnCode, shouldReturnDate,
				shouldReturnRateBigDecimal);

		// when
		Currency currency = new NbpJsonConverter().convertDataToObj(result);

		// then
		Assertions.assertThat(currency).isEqualToComparingFieldByField(expectedCurrency);

	}

	@Test
	public void should_return_json_mapping_exception() {

		// given
		String jsonString = "{,\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"050/A/NBP/2021\",\"effectiveDate\":\"2021-03-15\",\"mid\":4.5836}]}";
		String shouldReturnMsgString = "B³¹d mapowania Json / JsonObjMapper.java";

		// when

		Throwable thrown = catchThrowable(() -> new NbpJsonConverter().convertDataToObj(jsonString));

		// then
		Assertions.assertThat(thrown).isInstanceOf(MappingJsonException.class)
				.hasMessageContaining(shouldReturnMsgString);

	}

}
