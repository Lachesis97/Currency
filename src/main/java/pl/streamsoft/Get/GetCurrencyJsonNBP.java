package pl.streamsoft.Get;

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
import pl.streamsoft.services.Currency;
import pl.streamsoft.services.GetCurrancyDateCheckService;
import pl.streamsoft.services.JsonStringConvert;
import pl.streamsoft.services.Strategy;
import pl.streamsoft.services.StringToDate;

public class GetCurrencyJsonNBP implements Strategy {
	
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	String url;
	
	
	public GetCurrencyJsonNBP(String url) {
		this.url = url;
	}

	public Currency getCurrency(String code, String date){
			
		
		try {
                
              
              GetCurrancyDateCheckService getCurrancyDateCheckService = new GetCurrancyDateCheckService();
              
              String checkedDate = getCurrancyDateCheckService.checkDate(code, date);
              
              
              
              HttpGet request = new HttpGet(url + code + "/" + checkedDate + "/?format=json");
              
              CloseableHttpResponse response = httpClient.execute(request);
              
              HttpEntity entity = response.getEntity();
 			 
 			 if (entity != null) {
 				 
 				 String result = EntityUtils.toString(entity);  
 			 
 				 JsonStringConvert convert = new JsonStringConvert(result);
 				 
 				 String json = convert.convert();
 				 
 				 Currency currency = new Currency();
 	              
 	             ObjectMapper objectMapper = new ObjectMapper();
 	              
 	             currency = objectMapper.readValue(json, Currency.class);
 	             
 	             System.out.println("Pobrano kurs z dnia: \"" + checkedDate + "\"");
 				 
 	             return currency;
 			 }
              
		} catch(Exception e) {
			throw new RuntimeException(e);
		}  finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new CloseConnectionException("Nie uda³o siê zamkn¹æ po³¹czenia");
			}
		}
		return null;

		
	}


}
