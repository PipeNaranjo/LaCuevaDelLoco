package main.java.software.cafeteria.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.ProductosInventario;

/**
 * clase que guarda los registros del inventario
 * 
 * @author Sara Arias Quiroga
 * @author Andres Felipe Naranjo
 * @author Daniel Vargas Pelaez
 */
public class Inventario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// variable donde se guarda la lista de productos del inventario
	private List<ProductosInventario> productosI;
	// variable donde se guarda la lista de empresas que proveen los productos
	private List<Empresa> empresas;

	// -----------------------------------------------------------------------------------------
	// //
	/**
	 * agrega un nuevo producto a lista de productos
	 * 
	 * @param productoI
	 *            el objeto ya instanciado que se desea agregar a la lista
	 * @return regresa un booleano que indica si el elemento se ha a�adido a la
	 *         lista
	 */
	public boolean agregarProducto(ProductosInventario productoI) {
		if (!verficarExistenciaProducto(productoI.getCodigoDeBarras())) {
			this.productosI.add(productoI);
			return true;
		}
		return false;
	}

	/**
	 * metodo que verifica si el codigo de barras pertenece a un objeto
	 * existente en la lista de inventario
	 * 
	 * @param codigoDeBarras
	 *            el codigo de barras que se quiere verificar
	 * @return retorna un boolean tue si el elemto existe y false de lo
	 *         contrario
	 */
	public boolean verficarExistenciaProducto(String codigoDeBarras) {
		boolean respuesta = false;
		if (obtenerproductoI(codigoDeBarras) != null) {
			respuesta = true;
		}
		return respuesta;
	}

	/**
	 * metodo que sirve para obtener un objeto en base a su codigo de barras
	 * 
	 * @param codigoDeBarras
	 *            el codigo de barras del producto a obtener
	 * @return retorna el objeto el cual coincida su codigo de barras o
	 *         retororna null si no se encuentra el objeto
	 */
	public ProductosInventario obtenerproductoI(String codigoDeBarras) {
		for (ProductosInventario a : productosI) {
			if (a.getCodigoDeBarras().equals(codigoDeBarras)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * metodo que se usa para modificar los datos de un objeto de la lista
	 * 
	 * @param producto
	 *            el objeto antiguo que se quiere modificar
	 * @param nuevoProductoMod
	 *            un producto con los nuevos datos para modificar
	 */
	public boolean modificarProducto(ProductosInventario producto, ProductosInventario nuevoProductoMod) {
		ProductosInventario a = obtenerproductoI(producto.getCodigoDeBarras());
		if (a != null && (!verficarExistenciaProducto(nuevoProductoMod.getCodigoDeBarras())
				|| producto.getCodigoDeBarras().equals(nuevoProductoMod.getCodigoDeBarras()))) {
			a.setCodigoDeBarras(nuevoProductoMod.getCodigoDeBarras());
			a.setNombre(nuevoProductoMod.getNombre());
			a.setEmpresa(nuevoProductoMod.getEmpresa());
			a.setPresentacion(nuevoProductoMod.getPresentacion());
			a.setIva(nuevoProductoMod.getIva());
			a.setCosto(nuevoProductoMod.getCosto());
			a.setCantidad(nuevoProductoMod.getCantidad());
			a.setTipo(nuevoProductoMod.getTipo());
			a.setPrecio(nuevoProductoMod.getPrecio());
			return true;
		}
		return false;
	}

	/**
	 * metodo que borra un producto del inventario
	 * 
	 * @param codigoDeBarras
	 *            el codigo de barras del producto a borrar
	 * @return retorna un boolean que indica si se elimino el obejto de la lista
	 */
	public boolean borrarProductoI(String codigoDeBarras) {
		if (verficarExistenciaProducto(codigoDeBarras)) {
			productosI.remove(obtenerproductoI(codigoDeBarras));
			return true;
		}
		return false;
	}

	/**
	 * metodo que agrega mas unidades a un objeto de el inventario ya existente
	 * 
	 * @param producto
	 *            producto a modificar
	 * @param cantidad
	 *            cantidad de unidades que desea a�adir
	 * @return retorna un boolean si se a�aden unidades del inventario de manera
	 *         exitosa
	 */
	public boolean agregarAlInventario(ProductosInventario producto, int cantidad) {
		ProductosInventario a = obtenerproductoI(producto.getCodigoDeBarras());
		if (a != null) {
			a.agregarAlInventario(cantidad);
			return true;
		}
		return false;
	}

	/**
	 * metodo que agrega mas unidades a un objeto de el inventario ya existente
	 * 
	 * @param producto
	 *            producto a modificar
	 * @param cantidad
	 *            cantidad de unidades que desea sacar
	 * @return retorna un boolean si se sacan unidades del inventario de manera
	 *         exitosa
	 */
	public boolean restarAlInventario(ProductosInventario producto, int cantidad) {
		ProductosInventario a = obtenerproductoI(producto.getCodigoDeBarras());
		if (a != null) {
			a.restarAlInventario(cantidad);
			return true;
		}
		return false;
	}

	// -----------------------------------------------------------------------------------------
	// //
	/**
	 * metodo que sirve para agregar una empresa a la lista de empresas
	 * 
	 * @param empresa
	 *            el objeto ya instanciado que se desea agregar a la lista
	 * @return regresa un booleano que indica si el elemento se ha a�adido a la
	 *         lista
	 */
	public boolean agregarEmpresa(Empresa empresa) {
		if (!verficarExistenciaEmpresa(empresa.getNombre())) {
			this.empresas.add(empresa);
			return true;
		}
		return false;
	}

	/**
	 * metodo que crea un objeto de tipo Empresa y despues lo agrega a la lista
	 * de productos
	 * 
	 * @param nombre
	 *            nombre de la empresa
	 * @return regresa un booleano que indica si el elemento se ha a�adido a la
	 *         lista
	 */
	public boolean agregarEmpresa(String nombre) {
		return this.empresas.add(new Empresa(nombre));
	}

	/**
	 * modifica una empresa que ya existe
	 * 
	 * @param empresa
	 *            el objeto instanciado de la empresa a modificar
	 * @param nombre
	 *            el nuevo nombre de la empresa
	 * @return retorna un boolean que indica si se modifico o no la empresa
	 */
	public boolean modificarEmpresa(Empresa empresa, String nombre) {
		Empresa a = obtenerEmpresa(empresa.getNombre());
		if (a != null) {
			a.setNombre(nombre);
			return true;
		}
		return false;
	}

	/**
	 * metodo para verificar si existe una empresa
	 * 
	 * @param nombre
	 *            nombre de la empresa que se quiere verificar si existe
	 * @return retorna un boolean que es true si la encuentra y false en caso
	 *         contrario
	 */
	public boolean verficarExistenciaEmpresa(String nombre) {
		boolean respuesta = false;
		if (obtenerEmpresa(nombre) != null) {
			respuesta = true;
		}
		return respuesta;
	}

	/**
	 * metodo que sirve para obtener una empresa si existe
	 * 
	 * @param nombre
	 *            el nombre de la empresa que se quiere verificar que existe
	 * @return retorna un objeto empresa si lo encuentra en la lista, de lo
	 *         contrario retorna null
	 */
	public Empresa obtenerEmpresa(String nombre) {
		for (Empresa a : empresas) {
			if (a.getNombre().equals(nombre)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * metodo que sirve para quitar de la lista una empresa creada
	 * 
	 * @param nombre
	 *            es el nombre de la empresa que se quiere quitar de la lista
	 * @return regresa un boolean que indica si se elimino el obejto de la lista
	 */
	public boolean borrarEmpresas(String nombre) {
		if (verficarExistenciaEmpresa(nombre)) {
			empresas.remove(obtenerEmpresa(nombre));
			return true;
		}
		return false;
	}

	// -----------------------------------------------------------------------------------------
	// //
	/**
	 * metodo constructor del inventario
	 */
	public Inventario() {
		this.productosI = new ArrayList<ProductosInventario>();
		this.empresas = new ArrayList<Empresa>();
	}

	/**
	 * metodo get para la lista de productos
	 * 
	 * @return regresa la lista de productos
	 */
	public List<ProductosInventario> getProductosI() {
		return productosI;
	}

	/**
	 * metodo set de la lista de productos
	 * 
	 * @param productosI
	 *            la nueva lista de productos
	 */
	public void setProductosI(List<ProductosInventario> productosI) {
		this.productosI = productosI;
	}

	/**
	 * meto get para la lista de empresas
	 * 
	 * @return regresa la lista de empresas
	 */
	public List<Empresa> getEmpresas() {
		return empresas;
	}

	/**
	 * metodo set para la lista de empresas
	 * 
	 * @param empresas
	 *            es la nueva lista de empresas
	 */
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

}
