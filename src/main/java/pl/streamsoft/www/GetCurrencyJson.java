package pl.streamsoft.www;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetCurrencyJson implements Strategy {
	
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public Currency getCurrency(String code){
		
		
		HttpGet request = new HttpGet("http://api.nbp.pl/api/exchangerates/rates/a/" + code +"/?format=json");
		
		
		try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
           
            

            if (entity != null) {
              String result = EntityUtils.toString(entity);  

              JsonStringConvert convert = new JsonStringConvert(result);
                
              ObjectMapper objectMapper = new ObjectMapper();
              
              Currency currency = objectMapper.readValue(convert.convert(), Currency.class);
              
              return currency;
                
            }
		
		} catch(Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new CloseConnectionException("Nie uda³o siê zamkn¹æ po³¹czenia");
			}
		}
		return null;
		
	}


}
