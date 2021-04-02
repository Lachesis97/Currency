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
import pl.streamsoft.data_base_services.CurrencyCodeTablePersist;
import pl.streamsoft.data_base_services.CurrencyRatesTable;
import pl.streamsoft.data_base_services.CurrencyRatesTablePersist;
import pl.streamsoft.data_base_services.CurrencyRepo;
import pl.streamsoft.exceptions.DeleteFromDataBaseException;
import pl.streamsoft.exceptions.GetFromDataBaseException;
import pl.streamsoft.exceptions.UpdateDataBaseException;
import pl.streamsoft.services.Currency;

public class CurrencyRepository implements CurrencyRepo, DataProviderService {
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");
	private DataProviderService nextStrategy;

	public CurrencyRepository() {

	}

	public DataProviderService getNextStrategy() {
		return nextStrategy;
	}

	public CurrencyRepository(DataProviderService dataProviderService) {
		this.nextStrategy = dataProviderService;
	}

	@Override
	public void addCurrency(Currency currency) {

		if (!codeExists(currency.getCode())) {
			CurrencyCodeTablePersist codeTablePersist = new CurrencyCodeTablePersist();
			codeTablePersist.persist(currency.getName(), currency.getCode());
		}

		if (!rateExists(currency.getCode(), currency.getDate())) {
			CurrencyRatesTablePersist ratesTablePersist = new CurrencyRatesTablePersist();
			ratesTablePersist.persist(currency.getDate(), currency.getRate(), getCurrencyId(currency.getCode()));
		}
	}

	public void deleteSingleRate(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction;

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Query query = entityManager.createQuery("DELETE CurrencyRatesTable r WHERE r.date = :date AND r.cid = :cid");
		query.setParameter("date", date);
		query.setParameter("cid", getCurrencyId(code.toUpperCase()));
		try {
			query.executeUpdate();
			entityTransaction.commit();

		} catch (IllegalArgumentException e) {
			throw new DeleteFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (QueryTimeoutException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (PersistenceException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		}

	}

	@Override
	public void deleteCurrency(String code) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction;

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Query query1 = entityManager.createQuery("DELETE CurrencyRatesTable r WHERE r.cid = :cid");
		query1.setParameter("cid", getCurrencyId(code.toUpperCase()));
		Query query2 = entityManager.createQuery("DELETE CurrencyCodeTable WHERE code = :code");
		query2.setParameter("code", code);

		try {
			query1.executeUpdate();
			query2.executeUpdate();
			entityTransaction.commit();

		} catch (IllegalArgumentException e) {
			throw new DeleteFromDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (QueryTimeoutException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (PersistenceException e) {
			throw new DeleteFromDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		}
	}

	@Override
	public void updateSingleRate(String code, LocalDate date, LocalDate newDate, BigDecimal newRate) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction;

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Query query = entityManager.createQuery(
				"UPDATE CurrencyRatesTable SET date = :newd, rate = :newr WHERE date = :date AND cid = :cid");
		query.setParameter("newd", newDate);
		query.setParameter("newr", newRate);
		query.setParameter("date", date);
		query.setParameter("cid", getCurrencyId(code.toUpperCase()));
		try {
			query.executeUpdate();
			entityTransaction.commit();

		} catch (IllegalArgumentException e) {
			throw new UpdateDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (QueryTimeoutException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (PersistenceException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		}
	}

	@Override
	public void updateSingleCode(String code, String name, String newCode, String newName) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction;

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Query query = entityManager.createQuery(
				"UPDATE CurrencyCodeTable SET code = :newc, name = :newn WHERE code = :code AND name = :name");
		query.setParameter("newc", newCode);
		query.setParameter("newn", newName);
		query.setParameter("code", code);
		query.setParameter("name", name);
		try {
			query.executeUpdate();
			entityTransaction.commit();

		} catch (IllegalArgumentException e) {
			throw new UpdateDataBaseException("èle sformu≥owane zapytanie.", e);
		} catch (IllegalStateException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (QueryTimeoutException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		} catch (PersistenceException e) {
			throw new UpdateDataBaseException("Nie uda≥o siÍ usunπÊ rekordu z bazy danych.", e);
		}
	}

	@Override
	public long getCurrencyId(String code) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCode");
		query.setParameter("code", code.toUpperCase());
		CurrencyCodeTable currencyCodeTable = null;

		try {
			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();
			return currencyCodeTable.getId();
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

	@Override
	public boolean rateExists(String code, LocalDate date) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetRate");
		query.setParameter("date", date);
		query.setParameter("cid", getCurrencyId(code.toUpperCase()));

		try {
			CurrencyRatesTable currencyRatesTable = (CurrencyRatesTable) query.getSingleResult();

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

	@Override
	public boolean codeExists(String code) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCode");
		query.setParameter("code", code.toUpperCase());
		CurrencyCodeTable currencyCodeTable = null;

		try {
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

	@Override
	public Currency getCurrency(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCurrency");
		query.setParameter("code", code.toUpperCase());
		query.setParameter("date", date);
		CurrencyCodeTable currencyCodeTable = null;
		Currency currency = null;

		try {
			currencyCodeTable = (CurrencyCodeTable) query.getSingleResult();

			if (currencyCodeTable != null) {
				for (CurrencyRatesTable currencyRatesTable : currencyCodeTable.getRate()) {
					currency = new Currency(currencyCodeTable.getName(), currencyCodeTable.getCode(),
							currencyRatesTable.getDate(), currencyRatesTable.getRate());
				}
				return currency;
			} else {
				if (nextStrategy != null) {
					return nextStrategy.getCurrency(code, date);
				}
				return null;

			}

		} catch (NoResultException e) {
			if (nextStrategy != null) {
				return nextStrategy.getCurrency(code, date);
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

	@Override
	public Currency validateDate(String code, LocalDate date) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery("CurrencyCodeTable.GetCurrency");
		query.setParameter("code", code.toUpperCase());
		query.setParameter("date", date);
		CurrencyCodeTable currencyCodeTable = null;
		Currency currency = null;

		try {
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
