package pl.streamsoft.data_base_services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.NonUniqueResultException;
import org.hibernate.QueryTimeoutException;

import pl.streamsoft.exceptions.DeleteFromDataBaseException;
import pl.streamsoft.exceptions.GetFromDataBaseException;
import pl.streamsoft.exceptions.UpdateDataBaseException;
import pl.streamsoft.get_or_save_data.CurrencyRepository;

public class CountryRepository extends CurrencySpecificRequests {
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Currency");

	public void addCountry(String countryCode, String countryName, String currencyCode, String currencyName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		CurrencyRepository currencyRepository = new CurrencyRepository();
		CurrencyCodeTable codeTable = new CurrencyCodeTable(currencyCode.toUpperCase(), currencyName);
		CountryTable countryTable = new CountryTable();

		try {
			if (!countryHaveRate(countryName, currencyCode)) {
				entityTransaction.begin();

				if (!currencyRepository.codeExists(currencyCode)) {
					entityManager.persist(codeTable);
				}

				if (countryExists(countryName, countryCode)) {

					countryTable = entityManager.getReference(CountryTable.class, countryCode);

					countryTable.setCountry_code(countryCode);
					countryTable.setCountry_name(countryName);
					countryTable.getCodetable().add(codeTable);

				} else {

					countryTable.setCountry_code(countryCode);
					countryTable.setCountry_name(countryName);
					countryTable.getCodetable().add(codeTable);
					entityManager.persist(countryTable);
				}

				entityManager.persist(countryTable);
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

	public boolean countryExists(String countryName, String countryCode) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createNamedQuery("CountryTable.GetCountry");
			query.setParameter("country_code", countryCode.toUpperCase());
			query.setParameter("country_name", countryName);
			CountryTable countryTable = null;

			countryTable = (CountryTable) query.getSingleResult();

			if (countryTable != null) {
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

	public boolean countryHaveRate(String countryCode, String currencyCode) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createNamedQuery("CountryTable.CountryHaveRate");
			query.setParameter("code", currencyCode.toUpperCase());
			query.setParameter("country_code", countryCode);
			CountryTable countryTable = null;

			countryTable = (CountryTable) query.getSingleResult();

			if (countryTable != null) {
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

	public List<CountryTable> getCountriesWithTwoOrMoreCurrencies() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<CountryTable> dbResult = new ArrayList<CountryTable>();

		try {
			Query query = entityManager.createNamedQuery("CountryTable.GetCountriesWithTwoOrMoreCurrencies");

			dbResult = query.getResultList();

			return dbResult;

		} catch (NoResultException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ obiektu z bazy danych.", e);
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

	public CountryTable getCountryCurrencyList(String countryCode) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Query query = entityManager.createNamedQuery("CountryTable.GetCountryCurrencyList");
			query.setParameter("country_code", countryCode.toUpperCase());

			CountryTable dbResult = (CountryTable) query.getSingleResult();

			return dbResult;

		} catch (NoResultException e) {
			throw new GetFromDataBaseException("Nie uda≥o siÍ pobraÊ obiektu z bazy danych.", e);
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

	public void deleteCountry(String countryCode) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {

			entityTransaction.begin();
			CountryTable countryTable = entityManager.find(CountryTable.class, countryCode);
			entityManager.remove(countryTable);

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

}
