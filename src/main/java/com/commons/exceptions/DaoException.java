package com.commons.exceptions;

/**
 * Clase encargada del manejo de excepciones generadas en la capa de acceso a
 * datos
 *
 * @author Gonzalo Javier Díaz
 */
public class DaoException extends Exception {

	private static final long serialVersionUID = 1170813398577579401L;

	/**
     * Constructor sin parámetros
     */
    public DaoException() {
        super();
    }

    /**
     * Constructor con el mensaje de error
     *
     * @param message Mensaje de error
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Contructor con el mensaje de error y la causa de la excepción
     *
     * @param message Mensaje de error
     * @param cause Causa de la excepción
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
