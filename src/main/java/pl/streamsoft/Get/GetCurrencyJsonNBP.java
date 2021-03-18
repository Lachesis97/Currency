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
import pl.streamsoft.services.FutureDateCheckService;
import pl.streamsoft.services.GetCurrancyNbpDateCheckService;
import pl.streamsoft.services.JsonObjMapper;
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
			
              GetCurrancyNbpDateCheckService getCurrancyDateCheckService = new GetCurrancyNbpDateCheckService();
              
              String checkedDate = getCurrancyDateCheckService.checkDate(code, date);
              
              
              HttpGet request = new HttpGet(url + code + "/" + checkedDate + "/?format=json");
              
              CloseableHttpResponse response = httpClient.execute(request);
              
              HttpEntity entity = response.getEntity();
 			 
 			 if (entity != null) {
 				 
 				 String result = EntityUtils.toString(entity);  
 			 
 	             System.out.println("Pobrano kurs z dnia: \"" + checkedDate + "\"");
 	             
 	             JsonObjMapper jsonObjMapper = new JsonObjMapper();
 				 
 	             return jsonObjMapper.mapper(result);
 			 }
              
		} catch(Exception e) {
			throw new RuntimeException(e);
		}  finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new CloseConnectionException("Nie uda�o si� zamkn�� po��czenia");
			}
		}
		return null;

		
	}


}
