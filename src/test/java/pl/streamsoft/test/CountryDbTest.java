package pl.streamsoft.test;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import pl.streamsoft.data_base_services.CountryRepository;
import pl.streamsoft.data_base_services.CountryTable;
import pl.streamsoft.data_base_services.CurrencyCodeTable;

public class CountryDbTest {

	@Test
	public void should_add_new_country() {

		// given
		clearTestDB();
		CountryRepository repository = new CountryRepository();
		Assertions.assertThat(repository.countryExists("countryTest", "COUNTRYTEST")).isEqualTo(false);
		// when

		repository.addCountry("COUNTRYTEST", "countryTest", "CURRENCYTEST", "currencyTest");

		// then
		Assertions.assertThat(repository.countryExists("countryTest", "COUNTRYTEST")).isEqualTo(true);

	}

	@Test
	public void should_delete_existing_country() {

		// given
		CountryRepository repository = new CountryRepository();
		Assertions.assertThat(repository.countryExists("countryTest", "COUNTRYTEST")).isEqualTo(true);

		// when
		repository.deleteCountry("COUNTRYTEST");

		// then
		Assertions.assertThat(repository.countryExists("countryTest", "COUNTRYTEST")).isEqualTo(false);

	}

	@Test
	public void should_return_true_if_country_exist() {

		// given
		clearTestDB();
		CountryRepository repository = new CountryRepository();
		Assertions.assertThat(repository.countryExists("countryTest", "COUNTRYTEST")).isEqualTo(false);

		// when
		repository.addCountry("COUNTRYTEST", "countryTest", "CURRENCYTEST", "currencyTest");

		// then
		Assertions.assertThat(repository.countryExists("countryTest", "COUNTRYTEST")).isEqualTo(true);
	}

	@Test
	public void should_return_true_if_country_already_has_that_currency() {

		// given
		clearTestDB();
		CountryRepository repository = new CountryRepository();
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST", "CURRENCYTEST")).isEqualTo(false);

		// when
		repository.addCountry("COUNTRYTEST", "countryTest", "CURRENCYTEST", "currencyTest");

		// then
		Assertions.assertThat(repository.countryExists("countryTest", "COUNTRYTEST")).isEqualTo(true);

	}

	@Test
	public void should_return_country_with_currency_list() {
		// given
		clearTestDB();
		CountryRepository repository = new CountryRepository();
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST", "CURRENCYTEST")).isEqualTo(false);
		repository.addCountry("COUNTRYTEST", "countryTest", "CURRENCYTEST", "currencyTest");
		repository.addCountry("COUNTRYTEST", "countryTest", "GBP", "funt szterling");
		repository.addCountry("COUNTRYTEST", "countryTest", "EUR", "euro");
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST", "CURRENCYTEST")).isEqualTo(true);
		CurrencyCodeTable cur = new CurrencyCodeTable();
		List<CurrencyCodeTable> resultList = new ArrayList<CurrencyCodeTable>();

		// when
		CountryTable countryTable = repository.getCountryCurrencyList("PL");
		for (CurrencyCodeTable currencyCodeTable : countryTable.getCodetable()) {
			cur.setCode(currencyCodeTable.getCode());
			cur.setName(currencyCodeTable.getName());

			resultList.add(cur);

		}

		// then
		Assertions.assertThat(resultList.get(0).getCode().equals("CURRENCYTEST")
				&& resultList.get(0).getName().equals("currencyTest") && resultList.get(1).getCode().equals("GBP")
				&& resultList.get(1).getName().equals("funt szterling") && resultList.get(2).getCode().equals("EUR")
				&& resultList.get(2).getName().equals("euro"));
	}

	@Test
	public void should_return_list_of_countries_with_two_or_more_currencies() {
		// given
		clearTestDB();
		CountryRepository repository = new CountryRepository();
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST", "CURRENCYTEST")).isEqualTo(false);
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST1", "CURRENCYTEST")).isEqualTo(false);
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST2", "CURRENCYTEST")).isEqualTo(false);
		repository.addCountry("COUNTRYTEST", "countryTest", "CURRENCYTEST", "currencyTest");
		repository.addCountry("COUNTRYTEST", "countryTest", "GBP", "funt szterling");
		repository.addCountry("COUNTRYTEST", "countryTest", "EUR", "euro");
		repository.addCountry("COUNTRYTEST1", "countryTest1", "CURRENCYTEST", "currencyTest");
		repository.addCountry("COUNTRYTEST1", "countryTest1", "GBP", "funt szterling");
		repository.addCountry("COUNTRYTEST2", "countryTest2", "EUR", "euro");
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST", "CURRENCYTEST")).isEqualTo(true);
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST1", "CURRENCYTEST")).isEqualTo(true);
		Assertions.assertThat(repository.countryHaveRate("COUNTRYTEST2", "EUR")).isEqualTo(true);

		// when
		List<CountryTable> resultList = repository.getCountriesWithTwoOrMoreCurrencies();
		List<CountryTable> testList = new ArrayList<CountryTable>();
		for (CountryTable countryTable : resultList) {
			if (countryTable.getCountry_code().equals("COUNTRYTEST")
					|| countryTable.getCountry_code().equals("COUNTRYTEST1")
					|| countryTable.getCountry_code().equals("COUNTRYTEST2")) {

				testList.add(countryTable);

			}

		}

		// then
		Assertions.assertThat(testList.size() == 2);
		Assertions.assertThat(testList.get(0).getCountry_code().equals("COUNTRYTEST")
				&& testList.get(0).getCountry_name().equals("countryTest")
				&& testList.get(1).getCountry_code().equals("COUNTRYTEST1")
				&& testList.get(1).getCountry_name().equals("countryTest1"));
		Throwable thrown = catchThrowable(() -> testList.get(2));
		Assertions.assertThat(thrown).isInstanceOf(IndexOutOfBoundsException.class);

	}

	public void clearTestDB() {

		CountryRepository countryRepository = new CountryRepository();
		if (countryRepository.countryExists("countryTest", "COUNTRYTEST")) {
			countryRepository.deleteCountry("COUNTRYTEST");
		}
		if (countryRepository.countryExists("countryTest1", "COUNTRYTEST1")) {
			countryRepository.deleteCountry("COUNTRYTEST1");
		}
		if (countryRepository.countryExists("countryTest2", "COUNTRYTEST2")) {
			countryRepository.deleteCountry("COUNTRYTEST2");
		}

	}

}
