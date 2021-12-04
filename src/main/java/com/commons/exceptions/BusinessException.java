package com.commons.exceptions;

/**
 * Clase encargada del manejo de excepciones generadas en la capa de negocio
 * 
 * @author Gonzalo Javier Díaz
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1692145870842039380L;
	
	/**
     * Constructor sin parámetros
     */
    public BusinessException() {
        super();
    }

    /**
     * Constructor con el mensaje de error
     *
     * @param message Mensaje de error
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Contructor con el mensaje de error y la causa de la excepción
     *
     * @param message Mensaje de error
     * @param cause Causa de la excepción
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
