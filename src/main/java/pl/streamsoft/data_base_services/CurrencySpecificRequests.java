package pl.streamsoft.data_base_services;

import java.time.LocalDate;
import java.util.ArrayList;
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
		query.setParameter("cid", getCurrencyId(code.toUpperCase()));
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
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzajπcy siÍ rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} finally {
			entityManager.close();
		}

	}

	public Currency getMinRate(String code, LocalDate start, LocalDate end) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetMinRate");
		query.setParameter("cid", getCurrencyId(code.toUpperCase()));
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
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzajπcy siÍ rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} finally {
			entityManager.close();
		}

	}

	public List<Currency> getFiveBestRates(String code) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.getFiveBestRates").setMaxResults(5);
		query.setParameter("code", code.toUpperCase());

		List<Currency> currencyList = new ArrayList<>();
		List<CurrencyCodeTable> dbResult;

		try {
			dbResult = query.getResultList();

			for (CurrencyCodeTable currencyCodeTable : dbResult) {
				for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
					currencyList.add(new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
							currencyRatesTable.getDate(), currencyRatesTable.getRate()));
				}
			}

			return currencyList;
		} catch (NoResultException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzajπcy siÍ rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} finally {
			entityManager.close();
		}

	}

	public List<Currency> getFiveWorstRates(String code) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.getFiveWorstRates");
		query.setParameter("code", code.toUpperCase());
		query.setMaxResults(5);
		List<Currency> currencyList = new ArrayList<>();
		List<CurrencyCodeTable> dbResult;

		try {
			dbResult = query.getResultList();
			for (CurrencyCodeTable currencyCodeTable : dbResult) {
				for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
					currencyList.add(new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
							currencyRatesTable.getDate(), currencyRatesTable.getRate()));
				}
			}

			return currencyList;
		} catch (NoResultException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzajπcy siÍ rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ ID podanego kodu.", e);
		} finally {
			entityManager.close();
		}

	}

}
