package pl.streamsoft.test;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.exceptions.ExecuteHttpRequestException;

public class CheckGetCurrencyJsonNbpException {

	@Test
	public void should_return_unknown_host_exception_GetCurrencyJsonNBP() {

		// given
		String url = "http://api.nbp.pl6060/api/exchangerates/rates/a/";
		String shouldReturnMsg = "Nieznany host / GetCurrencyJsonNBP.java";

		// when
		Throwable thrown = catchThrowable(
				() -> new GetCurrencyJsonNBP(url).getCurrency("EUR", LocalDate.of(2021, 3, 22)));

		// then
		Assertions.assertThat(thrown).isInstanceOf(ExecuteHttpRequestException.class)
				.hasMessageContaining(shouldReturnMsg);

	}

	@Test
	public void should_return_client_protocol_exception_GetCurrencyJsonNBP() {

		// given
		String url = "ht://api.nbp.pl6060/api/exchangerates/rates/a/";
		String shouldReturnMsg = "B³¹d ¿¹dania Http / GetCurrencyJsonNBP.java";

		// when
		Throwable thrown = catchThrowable(
				() -> new GetCurrencyJsonNBP(url).getCurrency("EUR", LocalDate.of(2021, 3, 22)));

		// then
		Assertions.assertThat(thrown).isInstanceOf(ExecuteHttpRequestException.class)
				.hasMessageContaining(shouldReturnMsg);

	}

}
