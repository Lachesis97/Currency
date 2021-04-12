package pl.streamsoft.data_base_services;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.NonUniqueResultException;
import org.hibernate.QueryTimeoutException;

import pl.streamsoft.exceptions.GetFromDataBaseException;
import pl.streamsoft.get_or_save_data.CurrencyRepository;
import pl.streamsoft.services.Currency;

public class CurrencySpecificRequests extends CurrencyRepository {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");

	public Currency getMaxRate(String code, LocalDate start, LocalDate end) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetMaxRate");
		query.setParameter("code", code.toUpperCase());
		query.setParameter("start", start);
		query.setParameter("end", end);

		Currency currency = null;

		try {
			CurrencyCodeTable currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
				currency = new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
						currencyRatesTable.getDate(), currencyRatesTable.getRate());
			}

			return currency;
		} catch (NoResultException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� MAX Kursu.", e);
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzaj�cy si� rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("�le sformu�owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� MAX Kursu.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� MAX Kursu.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� MAX Kursu.", e);
		} finally {
			entityManager.close();
		}

	}

	public Currency getMinRate(String code, LocalDate start, LocalDate end) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetMinRate");
		query.setParameter("code", code.toUpperCase());
		query.setParameter("start", start);
		query.setParameter("end", end);

		Currency currency = null;

		try {
			CurrencyCodeTable currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
				currency = new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
						currencyRatesTable.getDate(), currencyRatesTable.getRate());
			}

			return currency;
		} catch (NoResultException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� MIN Kursu.", e);
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzaj�cy si� rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("�le sformu�owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� MIN Kursu.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� MIN Kursu.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� MIN Kursu.", e);
		} finally {
			entityManager.close();
		}

	}

	public List<CurrencyRatesTable> getFiveBestRates(String code) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			Query query = entityManager.createNamedQuery("CurrencyRatesTable.getFiveBestRates");
			query.setParameter("code", code.toUpperCase());
			query.setMaxResults(5);

			List<CurrencyRatesTable> dbResult = query.getResultList();

			return dbResult;
		} catch (NoResultException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� ID podanego kodu.", e);
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzaj�cy si� rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("�le sformu�owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� ID podanego kodu.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� ID podanego kodu.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� ID podanego kodu.", e);
		} finally {
			entityManager.close();
		}

	}

	public List<CurrencyRatesTable> getFiveWorstRates(String code) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			Query query = entityManager.createNamedQuery("CurrencyRatesTable.getFiveWorstRates");
			query.setParameter("code", code.toUpperCase());
			query.setMaxResults(5);
			List<CurrencyRatesTable> dbResult = query.getResultList();

			return dbResult;
		} catch (NoResultException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� ID podanego kodu.", e);
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzaj�cy si� rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("�le sformu�owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� ID podanego kodu.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� ID podanego kodu.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda�o si� pobra� ID podanego kodu.", e);
		} finally {
			entityManager.close();
		}

	}

}
