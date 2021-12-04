package com.commons.rest;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.commons.interfaces.RestClient;
import com.commons.logging.annotations.Auditable;
import com.commons.utils.CommonsUtils;

@Component
public class RestClientImpl implements RestClient {
	
	protected RestTemplate restTemplate;
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(RestClientImpl.class);
	
	public RestClientImpl(MappingJackson2HttpMessageConverter messageConverter){

		restTemplate = buildRestTemplate(null, messageConverter);
	}
	
	private RestTemplate buildRestTemplate(ClientHttpRequestFactory requestFactory, MappingJackson2HttpMessageConverter messageConverter){

		RestTemplate restTemplate;

		if(requestFactory != null){
			restTemplate = new RestTemplate(requestFactory);
		}else{
			restTemplate = new RestTemplate();
		}

		//Verificar si ya existe un converter del tipo enviado por parametro
		AbstractHttpMessageConverter<?> existentConverter = CollectionUtils.findValueOfType(restTemplate.getMessageConverters(), messageConverter.getClass());

		if(existentConverter != null){
			//Remover converter existente para reemplazarlo por el recibido por parametro
			restTemplate.getMessageConverters().remove(existentConverter);
		}

		restTemplate.getMessageConverters().add(messageConverter);
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

		return restTemplate;


	}
	
	private HttpHeaders buildHttpHeaders (MediaType mediaType, String authorization){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);		

		if (StringUtils.isNotEmpty(authorization)){
			headers.add("Authorization", authorization);
		}

		return headers;
	}
	
	private HttpHeaders buildHttpHeaders (MediaType mediaType){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);	

		return headers;
	}
	
	private HttpEntity<Object> buildHttpEntity(HttpHeaders headers, MediaType mediaType, Object bodyBean) throws Exception{
		HttpEntity<Object> httpEntity = null;
	
			
			if(MediaType.APPLICATION_FORM_URLENCODED.equals(mediaType)){
				MultiValueMap<String, Object> entityToMap;

				entityToMap = CommonsUtils.introspect(bodyBean);

				httpEntity = new HttpEntity<Object>(entityToMap,headers);
			}else{
				httpEntity = new HttpEntity<Object>(bodyBean,headers);
			}

		return httpEntity;
	}
	
	@Auditable
	public <T> T doPost (String restServiceUrl, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception  {
		HttpHeaders headers = buildHttpHeaders(mediaType, null); 

		HttpEntity<Object> entity = buildHttpEntity(headers, mediaType, bodyBean);

		return restTemplate.postForObject(restServiceUrl, entity, resultClass);
	}
	
	@Auditable
	public <T> List<T> doPost (String restServiceUrl, Object bodyBean, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass) throws Exception {
		HttpHeaders headers = buildHttpHeaders(mediaType, null);

		HttpEntity<Object> entity = buildHttpEntity(headers, mediaType, bodyBean);
		
		ResponseEntity<List<T>> response = restTemplate.exchange(restServiceUrl, HttpMethod.POST, entity, resultClass);
		
		return response.getBody();
	}
	
	@Auditable
	public <T> List<T> doGet (String restServiceUrl, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass) {
		
		HttpHeaders headers = buildHttpHeaders(mediaType, null);
		
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		
		ResponseEntity<List<T>> response = restTemplate.exchange(restServiceUrl , HttpMethod.GET, entity, resultClass);
		
		return response.getBody();
	}
	
	@Auditable
	@Override
	public <T> T doGet (String restServiceUrl, MediaType mediaType, Class<T> resultClass, String token) {
		
		HttpHeaders headers = buildHttpHeaders(mediaType, token);
		
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		
		ResponseEntity<T> response = restTemplate.exchange(restServiceUrl , HttpMethod.GET, entity, resultClass);
		
		return response.getBody();
	}
	
	@Auditable
	@Override
	public <T> T doGet (String restServiceUrl, MediaType mediaType, Class<T> resultClass) {
		
		HttpHeaders headers = buildHttpHeaders(mediaType);
		
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		
		ResponseEntity<T> response = restTemplate.exchange(restServiceUrl , HttpMethod.GET, entity, resultClass);
		
		return response.getBody();
	}
}
