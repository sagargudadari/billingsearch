package com.mastercard.billingsearch.rest;

import com.mastercard.billingsearch.entity.RequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Component
public class GenericRestClient<T, V> {

	@Autowired
	private RestTemplate restTemplate;

	public V execute(RequestDetails requestDetails, T data, ResponseErrorHandler errorHandler,
					 Class<V> genericClass) throws ResourceAccessException, Exception {
		
		restTemplate.setErrorHandler(errorHandler);
		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<T> entity = new HttpEntity<T>(data, headers);
		ResponseEntity<V> response = restTemplate.exchange(requestDetails.getUrl(), requestDetails.getRequestType(),
				entity, genericClass);
		return response.getBody();
	}

}