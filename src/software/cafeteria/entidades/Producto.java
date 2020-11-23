package software.cafeteria.entidades;

import java.io.Serializable;

/**
 * Clase que guarda todos los datos de productos en general
 * 
 * @author Sara Arias Quiroga
 * @author Andres Felipe Naranjo
 * @author Daniel Vargas Pelaez
 */
public class Producto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// variable donde se guarda el codigo de barras del producto
	private String codigoDeBarras;
	// variable donde se guarda el nombre del producto
	private String nombre;
	// variable donde se guarda una instancia de la clase Empresa que provee el
	// producto
	private Empresa empresa;
	// variable donde se guarda la cantidad de unidades con las que se compra el
	// producto
	private int presentacion;

	// variable donde se guarda el porcentaje de iva que trae el producto
	private int iva;
	// variable donde se guarda el precio al que se compra el producto
	private int costo;
	// variable donde se guarda el precio al que se vende el producto en la
	// tienda
	private int precio;

	/**
	 * metodo constructor de la clase Producto
	 * 
	 * @param codigoDeBarras
	 *            el codigo de barras del producto
	 * @param nombre
	 *            el nombre del producto
	 * @param empresa
	 *            empresa que trae el producto a la tienda
	 * @param presentacion
	 *            cantidad en la que viene el producto
	 * @param iva
	 *            el porcentaje de iva que trae el producto
	 * @param costo
	 *            cuanto le valio a la tienda el producto
	 * @param precio
	 *            precio de venta al cual se vende la tienda
	 */
	public Producto(String codigoDeBarras, String nombre, Empresa empresa, int presentacion, int iva, int costo,
			int precio) {
		this.codigoDeBarras = codigoDeBarras;
		this.nombre = nombre;
		this.empresa = empresa;
		this.presentacion = 1;
		setPresentacion(presentacion);
		this.iva = 0;
		setIva(iva);
		this.costo = 50;
		setCosto(costo);
		this.precio = 50;
		setPrecio(precio);
	}

	/**
	 * metodo get para el codigo de barras
	 * 
	 * @return regresa el codigo de barras
	 */
	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	/**
	 * metdo set para el codigo de barras
	 * 
	 * @param codigoDeBarras
	 *            es el nuevo codigo e barras del producto
	 */
	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	/**
	 * metodo get del nombre del producto
	 * 
	 * @return el nombre del producto
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * metodo set del nombre del nombre del producto
	 * 
	 * @param nombre
	 *            es el nuevo nombre del producto
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * metodo get de la empresa proveedora del producto
	 * 
	 * @return la instancia del objeto Empresa asociado a el producto
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * metodo set de la empresa que provee el producto
	 * 
	 * @param empresa
	 *            es una instancia de la clase empresa que sera la nueva
	 *            proveedora del producto
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * metodo get del costo del producto
	 * 
	 * @return el costo al que se compra el producto por unidad
	 */
	public int getCosto() {
		return costo;
	}

	/**
	 * metodo set del costo del producto
	 * 
	 * @param costo
	 *            es el nuevo costo de compra que tendra el producto
	 */
	public void setCosto(int costo) {
		if (costo > 0) {
			this.costo = costo;
		}
	}

	/***
	 * metodo get de la presentacion del producto
	 * 
	 * @return regresa la cantidad de unidades con las que se compra el producto
	 */
	public int getPresentacion() {
		return presentacion;
	}

	/**
	 * metodo set de la presentacion del producto
	 * 
	 * @param presentacion
	 *            es la nueva cantidad de unidades con las que se vende el
	 *            producto
	 */
	public void setPresentacion(int presentacion) {
		if (presentacion > 0) {
			this.presentacion = presentacion;
		}
	}

	/**
	 * metodo get del iva
	 * 
	 * @return regresa el porcentaje del iva del producto
	 */
	public int getIva() {
		return iva;
	}

	/**
	 * metodo set del iva
	 * 
	 * @param iva
	 *            es el nuevo porcentaje del iba
	 */
	public void setIva(int iva) {
		if (iva >= 0) {
			this.iva = iva;
		}
	}

	/**
	 * metodo get del precio del producto
	 * 
	 * @return regresa el precio de venta del producto en la tienda
	 */
	public int getPrecio() {
		return precio;
	}

	/**
	 * metodo set para el precio del producto
	 * 
	 * @param precio
	 *            es el nuevo precio al que se va a vender el producto.
	 */
	public void setPrecio(int precio) {
		if ((precio / 50) % 2 == 0 && precio > 0) {
			this.precio = precio;
		}
	}

	/**
	 * metodo que sirve para duplicar los datos del productos
	 */
	public Producto clone() {
		return new Producto(codigoDeBarras, nombre, empresa, presentacion, iva, costo, precio);
	}

}
