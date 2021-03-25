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
import pl.streamsoft.services.RateService;

public class GetCurrencyJsonNBP implements RateService {

	String url;

	public GetCurrencyJsonNBP() {
		url = "http://api.nbp.pl/api/exchangerates/rates/a/";
	}

	public GetCurrencyJsonNBP(String url) {
		this.url = url;
	}

	public String getCurrency(String code, LocalDate date) {
		if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			return null;
		}

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet request = new HttpGet(url + code + "/" + date + "/?format=json");

			CloseableHttpResponse response = httpClient.execute(request);

			HttpEntity entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() == 200) {

				String result = EntityUtils.toString(entity);

				return result;
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
