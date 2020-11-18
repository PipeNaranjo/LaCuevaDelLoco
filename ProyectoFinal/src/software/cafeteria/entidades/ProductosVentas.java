package software.cafeteria.entidades;

import java.io.Serializable;

/**
 * clase que guarda todos los datos de los productos de los recibos
 * @author Sara Arias Quiroga
 * @author Andres Felipe Naranjo
 * @author Daniel Vargas Pelaez
 */
public class ProductosVentas implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// variable donde se guarda los datos comunes del producto
	private Producto producto;
	// variable donde  se guarda la cantidad de unidades que hay del producto en el inventario
	private int cantidad;

	
	/**
	 * metodo contructor de los productos de un recibo
	 * @param productoI producto del inventario que se quiere comprar
	 * @param cantidad cantidad a la que se vende el producto
	 */
	public ProductosVentas(ProductosInventario productoI, int cantidad) {
		this.producto = productoI.getProducto().clone();
		this.cantidad = 1;
		setCantidad(cantidad);
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
		if(cantidad>0) {
			this.cantidad = cantidad;
		}
	}
	
	/**
	 * metodo que permite aumentar la cantidad de unidades que hay del producto
	 * @param cantidad es la cantidad a increntar en el inventario
	 */
	public void agregarCantidad(int cantidad) {
		if(cantidad>0) {
			this.cantidad += cantidad;
		}
	}
	
	/**
	 * metodo que permite disminuir la cantidad de unidades que va a comprar del producto
	 * @param cantidad es la cantidad a incrementar en el inventario
	 */
	public void restarCantidad(int cantidad) {
		int aux=this.cantidad - cantidad;
		if(aux>0) {
			this.cantidad = aux;
		}	
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
	 public int CompareTo(ProductosVentas comp){
		 return CompareTo(comp.getProducto().getNombre());
	 }
	 
}
