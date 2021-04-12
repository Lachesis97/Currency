package pl.streamsoft.get_or_save_data;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.NonUniqueResultException;
import org.hibernate.QueryTimeoutException;

import pl.streamsoft.data_base_services.CurrencyCodeTable;
import pl.streamsoft.data_base_services.CurrencyRatesTable;
import pl.streamsoft.exceptions.DeleteFromDataBaseException;
import pl.streamsoft.exceptions.GetFromDataBaseException;
import pl.streamsoft.exceptions.UpdateDataBaseException;
import pl.streamsoft.services.Currency;

public class CurrencyRepository implements DataProviderService {
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");

	private DataProviderService nextStrategy = null;

	public CurrencyRepository() {

	}

	public DataProviderService getNextStrategy() {
		return nextStrategy;
	}

	public CurrencyRepository(DataProviderService dataProviderService) {
		this.nextStrategy = dataProviderService;
	}

	public void addCurrency(Currency currency) {
		CurrencyCodeTable codeTable = new CurrencyCodeTable(currency.getCode(), currency.getName());
		CurrencyRatesTable ratesTable = new CurrencyRatesTable(currency.getDate(), currency.getRate(), codeTable);

		if (!rateExists(currency.getCode(), currency.getDate())) {
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();

			try {
				entityTransaction.begin();

				if (!codeExists(currency.getCode())) {
					entityManager.persist(codeTable);
				}

				entityManager.persist(ratesTable);

				entityTransaction.commit();

			} catch (IllegalArgumentException e) {
				throw new UpdateDataBaseException("Nie uda≥o siÍ dodaÊ rekordu do bazy danych.", e);
			} catch (IllegalStateException e) {
				throw new UpdateDataBaseException("Nie uda≥o siÍ dodaÊ rekordu do bazy danych.", e);
			} catch (PersistenceException e) {
				throw new UpdateDataBaseException("Nie uda≥o siÍ dodaÊ rekordu do bazy danych.", e);
			} finally {
				entityManager.close();
			}
		}

	}

	public void addCurrencyCode(String currencyCode, String currencyName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		CurrencyCodeTable codeTable = new CurrencyCodeTable(currencyCode, currencyName);

		try {

			if (!codeExists(currencyCode)) {
				entityTransaction.begin();

				entityManager.persist(codeTable);

				entityTransaction.commit();
			}

		} catch (IllegalArgumentException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ dodaÊ rekordu do bazy danych.", e);
		} catch (IllegalStateException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ dodaÊ rekordu do bazy danych.", e);

		} catch (PersistenceException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ dodaÊ rekordu do bazy danych.", e);
		} finally {
			entityManager.close();
		}
	}

	public boolean codeExists(String code) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCode");
			query.setParameter("code", code.toUpperCase());
			CurrencyCodeTable currencyCodeTable = null;

			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			if (currencyCodeTable != null) {
				return true;
			} else {
				return false;
			}
		} catch (NoResultException e) {
			return false;
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzajπcy siÍ rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ sprawdziÊ czy rekord istnieje.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ sprawdziÊ czy rekord istnieje.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ sprawdziÊ czy rekord istnieje.", e);
		} finally {
			entityManager.close();
		}

	}

	public boolean rateExists(String code, LocalDate date) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createNamedQuery("CurrencyRatesTable.GetRate");
			query.setParameter("date", date);
			query.setParameter("code", code.toUpperCase());

			CurrencyRatesTable currencyRatesTable = null;

			currencyRatesTable = (CurrencyRatesTable) query.getSingleResult();

			if (currencyRatesTable != null) {
				return true;
			} else {
				return false;
			}
		} catch (NoResultException e) {
			return false;
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzajπcy siÍ rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ sprawdziÊ czy rekord istnieje.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ sprawdziÊ czy rekord istnieje.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ sprawdziÊ czy rekord istnieje.", e);
		} finally {
			entityManager.close();
		}
	}

	public void deleteSingleRate(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {

			entityTransaction.begin();

			Query query = entityManager.createNamedQuery("CurrencyRatesTable.DeleteRate");
			query.setParameter("date", date);
			query.setParameter("code", code.toUpperCase());

			query.executeUpdate();

			entityTransaction.commit();

		} catch (IllegalArgumentException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (IllegalStateException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (QueryTimeoutException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (PersistenceException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} finally {
			entityManager.close();
		}

	}

	public void deleteCurrency(String code) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {

			entityTransaction.begin();
			CurrencyCodeTable codeTable = entityManager.find(CurrencyCodeTable.class, code);
			entityManager.remove(codeTable);

			entityTransaction.commit();

		} catch (IllegalArgumentException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (IllegalStateException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (QueryTimeoutException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (PersistenceException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		}
	}

	public void updateSingleRate(String code, LocalDate date, LocalDate newDate, BigDecimal newRate) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {

			entityTransaction.begin();

			Query query = entityManager.createQuery(
					"UPDATE CurrencyRatesTable SET date = :newd, rate = :newr WHERE date = :date AND code = :code");
			query.setParameter("newd", newDate);
			query.setParameter("newr", newRate);
			query.setParameter("date", date);
			query.setParameter("code", code.toUpperCase());

			query.executeUpdate();
			entityTransaction.commit();

		} catch (IllegalArgumentException e) {
			throw new UpdateDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ zaktualizowaÊ kursu w bazie danych.", e);
		} catch (QueryTimeoutException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ zaktualizowaÊ kursu w bazie danych.", e);
		} catch (PersistenceException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ zaktualizowaÊ kursu w bazie danych.", e);
		} finally {
			entityManager.close();
		}
	}

	public Currency getCurrency(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Currency currency = null;
		CurrencyCodeTable currencyCodeTable = null;

		try {
			Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCurrency");
			query.setParameter("code", code.toUpperCase());
			query.setParameter("date", date);

			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
				currency = new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
						currencyRatesTable.getDate(), currencyRatesTable.getRate());
			}
			return currency;

		} catch (NoResultException e) {
			if (nextStrategy != null) {
				currency = nextStrategy.getCurrency(code, date);

				addCurrency(currency);

				return currency;
			}
			return null;
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzajπcy siÍ rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ obiektu z bazy danych.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ obiektu z bazy danych.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ obiektu z bazy danych.", e);
		} finally {
			entityManager.close();
		}

	}

	public Currency validateDate(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		CurrencyCodeTable currencyCodeTable = null;
		Currency currency = null;

		try {

			Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCurrency");
			query.setParameter("code", code.toUpperCase());
			query.setParameter("date", date);

			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			if (currencyCodeTable != null) {
				for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
					currency = new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
							currencyRatesTable.getDate(), currencyRatesTable.getRate());
				}
				return currency;
			} else {
				return null;

			}

		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw new GetFromDataBaseException("Powtarzajπcy siÍ rekord w bazie danych.", e);
		} catch (IllegalArgumentException e) {
			throw new GetFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ obiektu z bazy danych.", e);
		} catch (QueryTimeoutException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ obiektu z bazy danych.", e);
		} catch (PersistenceException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ obiektu z bazy danych.", e);
		} finally {
			entityManager.close();
		}
	}

}
