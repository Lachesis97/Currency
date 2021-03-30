package pl.streamsoft.DbServices;

import java.time.LocalDate;

import pl.streamsoft.services.Currency;

public interface CurrencyRepo {

	public void addCurrency(Currency currency);

	public void deleteSingleRate(String code, LocalDate date);

	public void updateSingleRate(String code, LocalDate date);

	public boolean rateExists(String code, LocalDate date);

	public boolean codeExists(String code);

	public long getCurrencyId(String code);

}
