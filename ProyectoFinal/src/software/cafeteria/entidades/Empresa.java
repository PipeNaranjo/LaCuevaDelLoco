package software.cafeteria.entidades;

import java.io.Serializable;

/**
 * clase que guarda todos los datos de una empresa proveedora
 * 
 * @author Sara Arias Quiroga
 * @author Andres Felipe Naranjo
 * @author Daniel Vargas Pelaez
 */
public class Empresa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// nombre de la empresa que provee los productos
	private String nombre;

	/**
	 * metodo contructor de la empresa
	 * 
	 * @param nombre
	 *            el nombre de la empresa
	 */
	public Empresa(String nombre) {
		super();
		this.nombre = nombre;
	}

	/**
	 * metodo get del nombre de la empresa
	 * 
	 * @return regresa el nombre de la empresa
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * metodo set del nombre de la empresa
	 * 
	 * @param nombre
	 *            es el nuevo nombre de la empresa
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}