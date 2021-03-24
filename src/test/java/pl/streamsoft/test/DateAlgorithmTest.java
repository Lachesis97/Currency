package pl.streamsoft.test;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.exceptions.DateValidationException;
import pl.streamsoft.services.FutureDateToTodaysDate;
import pl.streamsoft.services.RateService;
import pl.streamsoft.services.ReturnValidateDate;

public class DateAlgorithmTest {

	@Test
	public void should_return_todays_date() {

		// given
		LocalDate date = LocalDate.of(2022, 03, 31);
		LocalDate now = LocalDate.now();

		// when
		LocalDate today = FutureDateToTodaysDate.dataValidation(date);

		// then
		Assertions.assertThat(now).isEqualTo(today);

	}

	@Test
	public void should_substract_max_10_days1() {

		// given
		LocalDate dateWithoutRate = LocalDate.of(2021, 3, 21);
		LocalDate shouldReturn = LocalDate.of(2021, 3, 19);
		String code = "eur";
		RateService strategy = new GetCurrencyJsonNBP();

		// when
		LocalDate checkedDate = ReturnValidateDate.dataValidation(code, dateWithoutRate, strategy);

		// then
		Assertions.assertThat(shouldReturn).isEqualTo(checkedDate);

	}

	@Test
	public void should_substract_max_10_days2() {

		// given
		LocalDate dateWithoutRate = LocalDate.of(2021, 1, 6);
		LocalDate shouldReturn = LocalDate.of(2021, 1, 5);
		String code = "eur";
		RateService strategy = new GetCurrencyJsonNBP();

		// when
		LocalDate checkedDate = ReturnValidateDate.dataValidation(code, dateWithoutRate, strategy);

		// then
		Assertions.assertThat(shouldReturn).isEqualTo(checkedDate);

	}

	@Test
	public void should_throw_after_10_days() {

		// given
		LocalDate dateWithoutRate = LocalDate.of(2023, 3, 13);
		String code = "eur";
		String shouldReturnMsg = "B³êdne argumenty wyszukiwania lub nie ma takiego kursu.";
		RateService strategy = new GetCurrencyJsonNBP();

		// when
		Throwable thrown = catchThrowable(() -> ReturnValidateDate.dataValidation(code, dateWithoutRate, strategy));

		// then
		Assertions.assertThat(thrown).isInstanceOf(DateValidationException.class).hasMessageContaining(shouldReturnMsg);
	}

}
