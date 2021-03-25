package pl.streamsoft.test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.ReturnValidateDate;

@RunWith(MockitoJUnitRunner.class)
public class DataAlorithmWithoutNbpTest {

	@Mock
	GetCurrencyJsonNBP getCurrencyJsonNBP;

	@InjectMocks
	ReturnValidateDate returnValidateDate;

	@Before

	public void init() {

		Mockito.when(getCurrencyJsonNBP.getCurrency("eur", LocalDate.of(2021, 3, 21)))
				.thenReturn(prepareWeekendReaction(LocalDate.of(2021, 3, 19)));

	}

	@Test
	public void should_substract_weekend() {

		// given
		LocalDate dateWithoutRate = LocalDate.of(2021, 3, 21);
		LocalDate shouldReturn = LocalDate.of(2021, 3, 19);
		String code = "eur";

		// GetCurrencyJsonNBP getCurrencyJsonNBP = mock();

		// when
		@SuppressWarnings("static-access")
		LocalDate checkedDate = returnValidateDate.dataValidation(code, dateWithoutRate, getCurrencyJsonNBP);

		// then
		Assertions.assertThat(checkedDate).isEqualTo(shouldReturn);

	}

	private String prepareWeekendReaction(LocalDate dateWithoutRate) {
		if (dateWithoutRate.getDayOfWeek().equals(DayOfWeek.SUNDAY)
				|| dateWithoutRate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			System.out.println("nie");
			return null;
		}
		System.out.println("tak");
		return "This is not null";
	}

}
