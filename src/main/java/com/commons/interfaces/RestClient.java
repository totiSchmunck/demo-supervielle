package com.commons.interfaces;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

public interface RestClient {
	
	public <T> T  doPost(String restServiceUrl, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception;
	
	public <T> List<T>  doPost(String restServiceUrl, Object bodyBean, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass) throws Exception;
	
	public <T> List<T> doGet (String restServiceUrl, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass) throws Exception;
	
	public <T> T doGet (String restServiceUrl, MediaType mediaType, Class<T> resultClass, String token) throws Exception;

	public <T> T doGet(String restServiceUrl, MediaType mediaType, Class<T> resultClass);
}
