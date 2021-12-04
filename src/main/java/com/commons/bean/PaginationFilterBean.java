package com.commons.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class PaginationFilterBean.
 *
 * @author gonzalo.tamos
 */
public class PaginationFilterBean {

	/** The cant registers. */
	@JsonProperty("cantRegisters")
	private int cantRegisters;
	
	/** The nro pagina. */
	@JsonProperty("nroPagina")
	private int nroPagina;
	
	/**
	 * Gets the cant registers.
	 *
	 * @return the cant registers
	 */
	public int getCantRegisters() {
		return cantRegisters;
	}

	/**
	 * Sets the cant registers.
	 *
	 * @param cantRegisters the new cant registers
	 */
	public void setCantRegisters(int cantRegisters) {
		this.cantRegisters = cantRegisters;
	}

	/**
	 * Gets the nro pagina.
	 *
	 * @return the nro pagina
	 */
	public int getNroPagina() {
		return nroPagina;
	}

	/**
	 * Sets the nro pagina.
	 *
	 * @param nroPagina the new nro pagina
	 */
	public void setNroPagina(int nroPagina) {
		this.nroPagina = nroPagina;
	}	
}
