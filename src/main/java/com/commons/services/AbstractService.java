package com.commons.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.commons.interfaces.RestClient;

/**
 * @author gonzalojavierdiaz
 *
 */
public abstract class AbstractService {
	
	@Autowired
	protected RestClient restClient;
	
}
