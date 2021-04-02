package pl.streamsoft.data_converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import pl.streamsoft.exceptions.MappingJsonException;
import pl.streamsoft.services.Currency;

public class NbpJsonConverter implements ConvertService {

	public Currency convertDataToObj(String data) {
		data = AdaptJsonStringNBP.adapt(data);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		try {
			Currency currency = objectMapper.readValue(data, Currency.class);
			return currency;

		} catch (JsonMappingException e) {
			throw new MappingJsonException("B³¹d mapowania Json / JsonObjMapper.java", e);
		} catch (JsonProcessingException e) {
			throw new MappingJsonException("B³¹d mapowania Json / JsonObjMapper.java", e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
