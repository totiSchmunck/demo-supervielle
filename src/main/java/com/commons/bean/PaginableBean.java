package com.commons.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class PaginableBean.
 *
 * @author gonzalo.tamos
 */
public class PaginableBean {

	/** The cant total registros. */
	@JsonProperty("cantTotalRegistros")
	private Long cantTotalRegistros;
	
	/** The cant total paginas. */
	@JsonProperty("cantTotalPaginas")
	private Integer cantTotalPaginas;

	/**
	 * Gets the cant total registros.
	 *
	 * @return the cant total registros
	 */
	public Long getCantTotalRegistros() {
		return cantTotalRegistros;
	}

	/**
	 * Sets the cant total registros.
	 *
	 * @param cantTotalRegistros the new cant total registros
	 */
	public void setCantTotalRegistros(Long cantTotalRegistros) {
		this.cantTotalRegistros = cantTotalRegistros;
	}

	/**
	 * Gets the cant total paginas.
	 *
	 * @return the cant total paginas
	 */
	public Integer getCantTotalPaginas() {
		return cantTotalPaginas;
	}

	/**
	 * Sets the cant total paginas.
	 *
	 * @param cantTotalPaginas the new cant total paginas
	 */
	public void setCantTotalPaginas(Integer cantTotalPaginas) {
		this.cantTotalPaginas = cantTotalPaginas;
	}	
}
