package pl.streamsoft.services;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import pl.streamsoft.exceptions.CloseConnectionException;
import pl.streamsoft.exceptions.ExecuteHttpRequestException;

public class GetCurrancyNbpDateCheckService {

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	int i = 0;

	public LocalDate checkDate(String code, LocalDate date) {

		try {

			FutureDateCheckService getCurrancyDateService = new FutureDateCheckService();
			date = getCurrancyDateService.datacheck(date);

			HttpGet request = new HttpGet(
					"http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + date + "/?format=json");

			CloseableHttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() == 404) {
				while (response.getStatusLine().getStatusCode() != 200) {

					request.releaseConnection();
					response.close();

					date = date.minusDays(1);

					HttpGet requestt = new HttpGet(
							"http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + date + "/?format=json");
					response = httpClient.execute(requestt);
					i++;

				}
			}

			if (i != 0) {
				System.out.println("Dzisiejszy kurs jest niedostêpny.");
			}

			return date;

		} catch (ClientProtocolException e) {
			throw new ExecuteHttpRequestException("B³¹d ¿¹dania Http / GetCurrencyNBPDatacheckService.java", e);
		} catch (IOException e) {
			throw new CloseConnectionException("Nie uda³o siê zamkn¹æ po³¹czenia / GetCurrencyNBPDatacheckService.java",
					e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new CloseConnectionException(
						"Nie uda³o siê zamkn¹æ po³¹czenia / GetCurrencyNBPDatacheckService.java", e);
			}
		}

	}

}
