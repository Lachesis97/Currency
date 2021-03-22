package pl.streamsoft.Get;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pl.streamsoft.DbServices.CurrencyCodeTable;
import pl.streamsoft.DbServices.CurrencyRatesTable;
import pl.streamsoft.exceptions.NoDbResultException;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.Strategy;

public class GetCurrencyDB implements Strategy {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");

	@SuppressWarnings("unused")
	public Currency getCurrency(String code, LocalDate date) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("GetCurrencyDB");
		query.setParameter("code", code);
		query.setParameter("date", date);

		CurrencyCodeTable currencyCodeTable = null;
		CurrencyRatesTable currencyRatesTable = null;

		try {

			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			if (currencyCodeTable != null && currencyRatesTable != null) {
				Currency currency = new Currency();

				currency.setName(currencyCodeTable.getName());
				currency.setCode(currencyCodeTable.getName());
				currency.setDate(currencyRatesTable.getDate());
				currency.setRate(currencyRatesTable.getRate());
				return currency;
			} else {
				return null;
			}

		} catch (NoResultException e) {
			if (currencyCodeTable == null) {
				return null;
			} else {
				throw new NoDbResultException("Brak wyniku w bazie danych.", e);
			}
		} finally {
			entityManager.close();
		}

	}

}
