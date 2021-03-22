package pl.streamsoft.Get;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDate;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import pl.streamsoft.exceptions.CloseConnectionException;
import pl.streamsoft.exceptions.EntityToStringException;
import pl.streamsoft.exceptions.ExecuteHttpRequestException;
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.GetCurrancyNbpDateCheckService;
import pl.streamsoft.services.JsonObjMapper;
import pl.streamsoft.services.Strategy;

public class GetCurrencyJsonNBP implements Strategy {

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public Currency getCurrency(String code, LocalDate date) {

		String url = "http://api.nbp.pl/api/exchangerates/rates/a/";

		try {
			GetCurrancyNbpDateCheckService getCurrancyDateCheckService = new GetCurrancyNbpDateCheckService();

			LocalDate checkedDate = getCurrancyDateCheckService.checkDate(code, date);

			HttpGet request = new HttpGet(url + code + "/" + checkedDate + "/?format=json");

			CloseableHttpResponse response = httpClient.execute(request);

			HttpEntity entity = response.getEntity();

			if (entity != null) {

				String result = EntityUtils.toString(entity);

				System.out.println("Pobrano kurs z dnia: \"" + checkedDate + "\"");

				JsonObjMapper jsonObjMapper = new JsonObjMapper();

				return jsonObjMapper.mapper(result);
			} else {
				throw new ExecuteHttpRequestException(
						"Nie uda³o siê pobrac wyników z ¿¹dania Http / GetCurrencyJsonNBP.java");
			}

		} catch (ClientProtocolException e) {
			throw new ExecuteHttpRequestException("B³¹d ¿¹dania Http / GetCurrencyJsonNBP.java", e);
		} catch (ConnectException e) {
			throw new ExecuteHttpRequestException("Connection timeout / GetCurrencyJsonNBP.java", e);
		} catch (IOException e) {
			throw new EntityToStringException("B³¹d konwersji wyniku ¿¹dania Http na String / GetCurrencyJsonNBP.java",
					e);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new CloseConnectionException("Nie uda³o siê zamkn¹æ po³¹czenia / GetCurrencyJsonNBP.java", e);
			}
		}

	}

}
