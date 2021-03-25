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
import pl.streamsoft.services.RateService;

public class GetCurrencyDB implements RateService {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");

	@SuppressWarnings("unused")
	public String getCurrency(String code, LocalDate date) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("GetCurrencyDB");
		query.setParameter("code", code);
		query.setParameter("date", date);

		CurrencyCodeTable currencyCodeTable = null;
		CurrencyRatesTable currencyRatesTable = null;

		try {

			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			if (currencyCodeTable != null) {

				String currString;

				currString = (currencyCodeTable.getCode() + "," + currencyCodeTable.getName() + ","
						+ currencyRatesTable.getDate() + "," + currencyRatesTable.getRate());

				return currString;
			}
		} catch (NoResultException e) {
			if (currencyCodeTable == null) {
				throw new NoDbResultException("Brak wyniku w bazie danych.", e);

			}

		} finally {
			entityManager.close();
		}
		return null;

	}

}
