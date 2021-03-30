package pl.streamsoft.test;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.exceptions.ExecuteHttpRequestException;
import pl.streamsoft.services.Currency;

public class GetCurrencyJsonNbpTest {

	@Test
	public void should_return_null_if_rate_does_not_exist_and_if_url_is_bad() {

		// given
		String url = "http://api.nbp.pl/api/exchangerates/rates/a/";
		String badUrl = "http://api.nbp.pl/api/exchangerates/rates/";

		// when
		Currency result1 = new GetCurrencyJsonNBP().validateDate("EUR", LocalDate.of(2021, 3, 22));
		Currency result2 = new GetCurrencyJsonNBP(url).validateDate("EUR", LocalDate.of(2021, 3, 22));
		Currency result3 = new GetCurrencyJsonNBP().validateDate("EUR", LocalDate.of(2021, 3, 21));
		Currency result4 = new GetCurrencyJsonNBP(badUrl).validateDate("EUR", LocalDate.of(2021, 3, 22));

		// then
		Assertions.assertThat(result1 != null && result2 != null && result3 == null && result4 == null);

	}

	@Test
	public void should_return_unknown_host_exception() {

		// given
		String url = "http://api.nbp.pl6060/api/exchangerates/rates/a/";
		String shouldReturnMsg = "Nieznany host / GetCurrencyJsonNBP.java";

		// when
		Throwable thrown = catchThrowable(
				() -> new GetCurrencyJsonNBP(url).validateDate("EUR", LocalDate.of(2021, 3, 22)));

		// then
		Assertions.assertThat(thrown).isInstanceOf(ExecuteHttpRequestException.class)
				.hasMessageContaining(shouldReturnMsg);

	}

	@Test
	public void should_return_client_protocol_exception() {

		// given
		String url = "ht://api.nbp.pl6060/api/exchangerates/rates/a/";
		String shouldReturnMsg = "B³¹d ¿¹dania Http / GetCurrencyJsonNBP.java";

		// when
		Throwable thrown = catchThrowable(
				() -> new GetCurrencyJsonNBP(url).validateDate("EUR", LocalDate.of(2021, 3, 22)));

		// then
		Assertions.assertThat(thrown).isInstanceOf(ExecuteHttpRequestException.class)
				.hasMessageContaining(shouldReturnMsg);

	}

}
