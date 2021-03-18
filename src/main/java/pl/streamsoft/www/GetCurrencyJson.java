package pl.streamsoft.www;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.RuntimeCryptoException;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.streamsoft.exceptions.CloseConnectionException;
import pl.streamsoft.services.Context;
import pl.streamsoft.services.GetCurrancyDateCheckService;
import pl.streamsoft.services.JsonStringConvert;
import pl.streamsoft.services.Strategy;
import pl.streamsoft.services.StringToDate;

public class GetCurrencyJson implements Strategy {
	
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public Currency getCurrency(String code, String date){
		
		Currency currency = new Currency();
		
		try {
                
              ObjectMapper objectMapper = new ObjectMapper();
              GetCurrancyDateCheckService getCurrancyDateCheckService = new GetCurrancyDateCheckService();
              
              
              currency = objectMapper.readValue(getCurrancyDateCheckService.checkDate(code, date), Currency.class);
              
              return currency;
                
            

		} catch(Exception e) {
			throw new RuntimeException(e);
		}  finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new CloseConnectionException("Nie uda³o siê zamkn¹æ po³¹czenia");
			}
		}

		
	}


}
