package software.cafeteria.entidades;

import java.io.Serializable;

/**
 * clase que guarda todos los datos de los productos del inventario
 * @author Sara Arias Quiroga
 * @author Andres Felipe Naranjo
 * @author Daniel Vargas Pelaez
 */
public class ProductosInventario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// variable donde se guarda los datos comunes del producto
	private Producto producto;
	// variable donde  se guarda la cantidad de unidades que hay del producto en el inventario
	private int cantidad;
	// variable en donde se guarda que tipo de producto puesto por el tendero
	private String tipo;
	
	/**
	 * metodo contructor de los productos del inventario
	 * @param codigoDeBarras el codigo de barras del producto
	 * @param nombre el nombre del producto
	 * @param empresa empresa que trae el producto a la tienda
	 * @param presentacion cantidad en la que viene el producto
	 * @param iva el porcentaje de iva que trae el producto
	 * @param costo cuanto le valio a la tienda el producto
	 * @param cantidad cantidad de unidades que se tienen en el inventario de la tienda actualmente
	 * @param tipo clasificacion interna que el tendero le pone al producto
	 * @param precio precio de venta al cual se vende la tienda
	 */
	public ProductosInventario(String codigoDeBarras, String nombre, Empresa empresa, int presentacion, int iva, int costo, int cantidad, String tipo, int precio) {
		this.producto = new Producto(codigoDeBarras, nombre, empresa, presentacion, iva, costo,precio);
		this.cantidad = 0;
		setCantidad(cantidad);
		this.tipo = tipo;
	}
	
	/**
	 * metodo get de la instancia de la clase Producto
	 * @return la instancia del producto
	 */
	public Producto getProducto() {
		return producto;
	}
	
	/**
	 * metodo set de la instancia de la clase Producto
	 * @param producto la nueva instancia del producto
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	/**
	 * metodo de get de la cantidad en el inventario
	 * @return regresa la cantidad de unidades que hay en el inventario
	 */
	public int getCantidad() {
		return cantidad;
	}
	
	/**
	 * metodo set de la cantidad, la cual debe ser mayor o igual a 0
	 * @param cantidad es la nueva cantidad de productos
	 */
	public void setCantidad(int cantidad) {
		if(cantidad>=0) {
			this.cantidad = cantidad;
		}else {
			this.cantidad = 0;
		}
	}
	
	/**
	 * metodo que permite aumentar la cantidad de unidades que hay del producto
	 * @param cantidad es la cantidad a increntar en el inventario
	 */
	public void agregarAlInventario(int cantidad) {
		if(cantidad>=0) {
			this.cantidad += cantidad;
		}
	}
	
	/**
	 * metodo que permite disminuir la cantidad de unidades que hay del producto
	 * @param cantidad es la cantidad a incrementar en el inventario
	 */
	public void restarAlInventario(int cantidad) {
		int aux=this.cantidad - cantidad;
		if(aux>=0) {
			this.cantidad = aux;
		}else {
			this.cantidad = 0;
		}
	}

	/**
	 * metodo get del tipo de producto
	 * @return regresa el tipo de producto
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * metodo set del tipo de producto
	 * @param tipo es el nuevo tipo de producto
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * metodo que sirve para comparar el nombre de con la cadena entrante
	 * @param comp es la cadena con la que se desea comparar el nombre
	 * @return retorna el numero que indica si la cadena es mayor, igual o menor
	 */
	 public int CompareTo(String comp){
		 return producto.getNombre().compareTo(comp);
	 }
	
	 /**
	  * metodo que sirve para comparar el nombre de con el nombre del producto del inventario entrante
	  * @param comp es el Objeto producto con el que se quiere comparar el nombre
	  * @return retorna el numero que indica si la cadena es mayor, igual o menor
	  */
	 public int CompareTo(ProductosInventario comp){
		 return CompareTo(comp.getProducto().getNombre());
	 }
	 
	 
//---------------------------------------------------------------------------------//
	/**
	 * metodo get para el codigo de barras del producto
	 * @return regresa el codigo de barras
	 */ 	
	public String getCodigoDeBarras() {
		return producto.getCodigoDeBarras();
	}
	
	/**
	 * metdo set para el codigo de barras del producto
	 * @param codigoDeBarras es el nuevo codigo e barras del producto
	 */
	public void setCodigoDeBarras(String codigoDeBarras) {
		producto.setCodigoDeBarras(codigoDeBarras);
	}
	/**
	 * metodo get del nombre del producto del producto
	 * @return el nombre del producto
	 */
	public String getNombre() {
		return producto.getNombre();
	}
	/**
	 * metodo set del nombre del nombre del producto 
	 * @param nombre es el nuevo nombre del producto
	 */
	public void setNombre(String nombre) {
		producto.setNombre(nombre);
	}
	/**
	 * metodo get de la empresa proveedora del producto
	 * @return la instancia del objeto Empresa asociado a el producto
	 */	
	public Empresa getEmpresa() {
		return producto.getEmpresa();
	}
	/**
	 * metodo set de la empresa que provee el producto
	 * @param empresa es una instancia de la clase empresa que sera la nueva proveedora del producto
	 */
	public void setEmpresa(Empresa empresa) {
		producto.setEmpresa(empresa);
	}
	/**
	 * metodo get del costo del producto
	 * @return el costo al que se compra el producto por unidad
	 */
	public int getCosto() {
		return producto.getCosto();
	}
	/**
	 * metodo set del costo del producto 
	 * @param costo es el nuevo costo de compra que tendra el producto
	 */
	public void setCosto(int costo) {
		producto.setCosto(costo);
	}
	/***
	 * metodo get de la presentacion del producto
	 * @return regresa la cantidad de unidades con las que se compra el producto
	 */
	public int getPresentacion() {
		return producto.getPresentacion();
	}
	/**
	 * metodo set de la presentacion del producto
	 * @param presentacion es la nueva cantidad de unidades con las que se vende el producto
	 */
	public void setPresentacion(int presentacion) {
		producto.setPresentacion(presentacion);
	}
	/**
	 * metodo get del iva del producto
	 * @return regresa el porcentaje del iva del producto
	 */
	public int getIva() {
		return producto.getIva();
	}
	/**
	 * metodo set del iva del producto
	 * @param iva es el nuevo porcentaje del iba
	 */
	public void setIva(int iva) {
		producto.setIva(iva);
	}
	/**
	 * metodo get del precio del producto
	 * @return regresa el precio de venta del producto en la tienda
	 */
	public int getPrecio() {
		return this.producto.getPrecio();
	}
	/**
	 * metodo set para el precio del producto
	 * @param precio es el nuevo precio al que se va a vender el producto.
	 */
	public void setPrecio(int precio) {
		this.producto.setPrecio(precio);
	}
}
