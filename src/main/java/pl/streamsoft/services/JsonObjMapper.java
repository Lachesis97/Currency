package pl.streamsoft.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.streamsoft.exceptions.MappingJsonException;

public class JsonObjMapper {
	
	public Currency mapper(String json) {
		
		Currency currency = new Currency();
          
        ObjectMapper objectMapper = new ObjectMapper();
        
        JsonStringConvert convert = new JsonStringConvert();
		 
		String convertedJson = convert.convert(json);
           
        try {
			currency = objectMapper.readValue(convertedJson, Currency.class);
			return currency;
		} catch (JsonMappingException e) {
			throw new MappingJsonException("B³¹d mapowania Json / JsonObjMapper.java");
		} catch (JsonProcessingException e) {
			throw new MappingJsonException("B³¹d mapowania Json / JsonObjMapper.java");
		}
		
	}

}
