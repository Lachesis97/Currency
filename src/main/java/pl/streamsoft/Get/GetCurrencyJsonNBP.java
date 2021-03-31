package pl.streamsoft.Get;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.apache.http.HttpEntity;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import pl.streamsoft.exceptions.ExecuteHttpRequestException;
import pl.streamsoft.services.ConvertService;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.DataProviderService;
import pl.streamsoft.services.NbpJsonConverter;

public class GetCurrencyJsonNBP implements DataProviderService {

	private String url = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private ConvertService convertService = new NbpJsonConverter();
	private DataProviderService nextStrategy;

	public GetCurrencyJsonNBP() {

	}

	public GetCurrencyJsonNBP(ConvertService convertService) {
		this.convertService = convertService;

	}

	public GetCurrencyJsonNBP(String url) {
		this.url = url;

	}

	public GetCurrencyJsonNBP(DataProviderService dataProviderService) {
		this.nextStrategy = dataProviderService;

	}

	public GetCurrencyJsonNBP(ConvertService convertService, DataProviderService dataProviderService) {
		this.convertService = convertService;
		this.nextStrategy = dataProviderService;

	}

	public GetCurrencyJsonNBP(String url, ConvertService convertService, DataProviderService dataProviderService) {
		this.url = url;
		this.convertService = convertService;
		this.nextStrategy = dataProviderService;

	}

	public Currency getCurrency(String code, LocalDate date) {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet request = new HttpGet(url + code + "/" + date + "/?format=json");

			CloseableHttpResponse response = httpClient.execute(request);

			HttpEntity entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() == 200) {

				Currency currency = convertService.convertDataToObj(EntityUtils.toString(entity));

				return currency;
			} else {
				if (nextStrategy != null) {
					return nextStrategy.getCurrency(code, date);
				}
				return nextStrategy.getCurrency(code, date);
			}

		} catch (NoHttpResponseException e) {
			throw new ExecuteHttpRequestException("Brak odpowiedzi ze strony serwera / GetCurrencyJsonNBP.java", e);
		} catch (ClientProtocolException e) {
			throw new ExecuteHttpRequestException("B³¹d ¿¹dania Http / GetCurrencyJsonNBP.java", e);
		} catch (ConnectException e) {
			throw new ExecuteHttpRequestException("Connection timeout / GetCurrencyJsonNBP.java", e);
		} catch (UnknownHostException e) {
			throw new ExecuteHttpRequestException("Nieznany host / GetCurrencyJsonNBP.java", e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Currency validateDate(String code, LocalDate date) {
		if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			return null;
		}

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet request = new HttpGet(url + code + "/" + date + "/?format=json");

			CloseableHttpResponse response = httpClient.execute(request);

			HttpEntity entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() == 200) {

				Currency currency = convertService.convertDataToObj(EntityUtils.toString(entity));

				return currency;
			} else {
				return null;
			}

		} catch (NoHttpResponseException e) {
			throw new ExecuteHttpRequestException("Brak odpowiedzi ze strony serwera / GetCurrencyJsonNBP.java", e);
		} catch (ClientProtocolException e) {
			throw new ExecuteHttpRequestException("B³¹d ¿¹dania Http / GetCurrencyJsonNBP.java", e);
		} catch (ConnectException e) {
			throw new ExecuteHttpRequestException("Connection timeout / GetCurrencyJsonNBP.java", e);
		} catch (UnknownHostException e) {
			throw new ExecuteHttpRequestException("Nieznany host / GetCurrencyJsonNBP.java", e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
