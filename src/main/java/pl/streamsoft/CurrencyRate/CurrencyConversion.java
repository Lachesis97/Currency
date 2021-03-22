/*
 * CurrencyConversion
 *
 * Za pomoc¹ klasy FutureDateCheckService sprawdza czy ktoœ nie poda³ daty z przyszlosci
 *
 * Przekazuje wybrane parametry wyszukiwania do wybranej strategi i finalnie otrzymuje obiekt waluty
 *
 * Na koniec zwraca wynik konwersji wybranej waluty na zlotowki
 *
 */

package pl.streamsoft.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.streamsoft.services.Currency;
import pl.streamsoft.services.Strategy;

public class CurrencyConversion {

	static Strategy strategy;

	public CurrencyConversion(Strategy strategy) {
		this.strategy = strategy;
	}

	public static BigDecimal conversion(String code, LocalDate date, BigDecimal amount) {

		Currency currency = strategy.getCurrency(code.toUpperCase(), date);

		BigDecimal rate = currency.getRate();

		BigDecimal multi;

		return multi = amount.multiply(rate);

	}

}
