package pl.streamsoft.test;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import pl.streamsoft.Get.GetCurrencyJsonNBP;
import pl.streamsoft.services.ChangeJsonNBP;
import pl.streamsoft.services.FutureDateToTodaysDate;
import pl.streamsoft.services.ReturnValidateDate;
import pl.streamsoft.services.RateService;

public class CheckDataAlgorithm {

	@Test
	public void should_return_todays_date_FutureDateCheckService() {

		// given
		LocalDate date = LocalDate.of(2022, 03, 31);
		LocalDate now = LocalDate.now();

		// when
		LocalDate today = FutureDateToTodaysDate.dataValidation(date);

		// then
		Assertions.assertThat(now).isEqualTo(today);

	}

	@Test
	public void should_return_first_date_at_which_the_rate_is_available_if_given_date_doesnt_have_rate_max_10_days_difference1_GetCurrencyNbpDataCheckService() {

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
	public void should_return_first_date_at_which_the_rate_is_available_if_given_date_doesnt_have_rate_max_10_days_difference2_GetCurrencyNbpDataCheckService() {

		// given
		LocalDate dateWithoutRate = LocalDate.of(2021, 3, 13);
		LocalDate shouldReturn = LocalDate.of(2021, 3, 12);
		String code = "eur";
		RateService strategy = new GetCurrencyJsonNBP();

		// when
		LocalDate checkedDate = ReturnValidateDate.dataValidation(code, dateWithoutRate, strategy);

		// then
		Assertions.assertThat(shouldReturn).isEqualTo(checkedDate);

	}

	@Test
	public void should_throw_after_10_days_GetCurrencyNbpDataCheckService() {

		// given
		LocalDate dateWithoutRate = LocalDate.of(2023, 3, 13);
		String code = "eur";
		String shouldReturnMsg = "B³êdne argumenty wyszukiwania lub nie ma takiego kursu.";
		RateService strategy = new GetCurrencyJsonNBP();

		// when
		Throwable thrown = catchThrowable(
				() -> ReturnValidateDate.dataValidation(code, dateWithoutRate, strategy));

		// then
		Assertions.assertThat(thrown).hasMessageContaining(shouldReturnMsg);
	}

	@Test
	public void should_return_correct_json_string_ready_for_mapping_ConvertJsonNBP() {

		// given
		String NBPJson = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"050/A/NBP/2021\",\"effectiveDate\":\"2021-03-15\",\"mid\":4.5836}]}";
		String NBPConvertedJson = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"no\":\"050/A/NBP/2021\",\"effectiveDate\":\"2021-03-15\",\"mid\":4.5836}";

		// when
		ChangeJsonNBP convert = new ChangeJsonNBP();

		String convertedJson = convert.change(NBPJson);

		// then
		Assertions.assertThat(NBPConvertedJson).isEqualTo(convertedJson);

	}

//	@Test
//	public void should_return_correct_mapped_currency_object_JsonOBJMapper() {
//
//		// given
//		String result = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"no\":\"050/A/NBP/2021\",\"effectiveDate\":\"2021-03-15\",\"mid\":4.5836}";
//		String shouldReturnCode = "EUR";
//		String shouldReturnName = "euro";
//		LocalDate shouldReturnDate = LocalDate.of(2021, 3, 15);
//		BigDecimal shouldReturnRateBigDecimal = new BigDecimal("4.5836");
//
//		// when
//		NbpJsonMaper jsonObjMapper = new NbpJsonMaper();
//
//		Currency currency = jsonObjMapper.convertToObj(result);
//
//		// then
//		Assert.assertTrue(currency.getCode().equals(shouldReturnCode) && currency.getName().equals(shouldReturnName)
//				&& currency.getDate().equals(shouldReturnDate)
//				&& currency.getRate().equals(shouldReturnRateBigDecimal));
//
//	}
//
//	@Test
//	public void should_return_json_mapping_exception_JsonOBJMapper() {
//
//		// given
//		String json = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"050/A/NBP/2021\",\"effectiveDate\":\"2021-03-15\",\"mid\":4.5836}]}";
//		String shouldReturnMsgString = "B³¹d mapowania Json / JsonObjMapper.java";
//
//		// when
//		NbpJsonMaper jsonObjMapper = new NbpJsonMaper();
//		Throwable thrown = catchThrowable(() -> jsonObjMapper.convertToObj(json));
//
//		// then
//		Assertions.assertThat(thrown).isInstanceOf(MappingJsonException.class)
//				.hasMessageContaining(shouldReturnMsgString);
//
//	}

	@Test
	public void should_return_null_if_rate_does_not_exist_and_if_url_is_bad_GetCurrencyJsonNBP() {

		// given
		String url = "http://api.nbp.pl/api/exchangerates/rates/a/";
		String badUrl = "http://api.nbp.pl/api/exchangerates/rates/";

		// when
		String result1 = new GetCurrencyJsonNBP().getCurrency("EUR", LocalDate.of(2021, 3, 22));
		String result2 = new GetCurrencyJsonNBP(url).getCurrency("EUR", LocalDate.of(2021, 3, 22));
		String result3 = new GetCurrencyJsonNBP().getCurrency("EUR", LocalDate.of(2021, 3, 21));
		String result4 = new GetCurrencyJsonNBP(badUrl).getCurrency("EUR", LocalDate.of(2021, 3, 22));

		// then
		Assert.assertTrue(result1 != null && result2 != null && result3 == null && result4 == null);

	}

}
