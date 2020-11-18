package software.cafeteria.logica;

import java.io.Serializable;
import java.util.ArrayList;

import software.cafeteria.entidades.Empresa;
import software.cafeteria.entidades.ProductosInventario;

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
	private ArrayList<ProductosInventario> productosI;
	// variable donde se guarda la lista de empresas que proveen los productos
	private ArrayList<Empresa> empresas;

	// -----------------------------------------------------------------------------------------
	// //
	/**
	 * agrega un nuevo producto a lista de productos
	 * 
	 * @param productoI
	 *            el objeto ya instanciado que se desea agregar a la lista
	 * @return regresa un booleano que indica si el elemento se ha añadido a la
	 *         lista
	 */
	public boolean agregarProducto(ProductosInventario productoI) {
		if (!verficarExistenciaProducto(productoI.getCodigoDeBarras())) {
			this.productosI.add(productoI);
			// organizar();
			return true;
		}
		return false;
	}

	/**
	 * metodo que crea un objeto de tipo productoI y despues lo agrega a la
	 * lista de productos
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
	 * @param cantidad
	 *            cantidad de unidades que se tienen en el inventario de la
	 *            tienda actualmente
	 * @param tipo
	 *            clasificacion interna que el tendero le pone al producto
	 * @param precio
	 *            precio de venta al cual se vende la tienda
	 * @return regresa un booleano que indica si el elemento se ha añadido a la
	 *         lista
	 */
	public boolean agregarProducto(String codigoDeBarras, String nombre, Empresa empresa, int presentacion, int iva,
			int costo, int cantidad, String tipo, int precio) {
		return agregarProducto(new ProductosInventario(codigoDeBarras, nombre, empresa, presentacion, iva, costo,
				cantidad, tipo, precio));
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
		if (obtenerproductoI(codigoDeBarras) != null) {
			return true;
		}
		return false;
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
	 * @param codigoDeBarras
	 *            el nuevo codigo de barras del producto
	 * @param nombre
	 *            el nuevo nombre del producto
	 * @param empresa
	 *            nueva empresa que trae el producto a la tienda
	 * @param presentacion
	 *            nueva cantidad en la que viene el producto
	 * @param iva
	 *            el nuevo porcentaje de iva que trae el producto
	 * @param costo
	 *            cuanto le valio a la tienda el producto
	 * @param cantidad
	 *            nueva cantidad de unidades que se tienen en el inventario de
	 *            la tienda actualmente
	 * @param tipo
	 *            clasificacion interna que el tendero le pone al producto
	 * @param precio
	 *            nuevo precio de venta al cual se vende la tienda
	 * @return retorna un boolean que indica si el producto ha sido modificado o
	 *         no
	 */
	public boolean modificarProducto(ProductosInventario producto, String codigoDeBarras, String nombre,
			Empresa empresa, int presentacion, int iva, int costo, int cantidad, String tipo, int precio) {
		ProductosInventario a = obtenerproductoI(producto.getCodigoDeBarras());
		if (a != null) {
			a.setCodigoDeBarras(codigoDeBarras);
			a.setNombre(nombre);
			a.setEmpresa(empresa);
			a.setPresentacion(presentacion);
			a.setIva(iva);
			a.setCosto(costo);
			a.setCantidad(cantidad);
			a.setTipo(tipo);
			a.setPrecio(precio);
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
	 *            cantidad de unidades que desea añadir
	 * @return retorna un boolean si se añaden unidades del inventario de manera
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
	 * @return regresa un booleano que indica si el elemento se ha añadido a la
	 *         lista
	 */
	public boolean agregarEmpresa(Empresa empresa) {
		if (!verficarExistenciaEmpresa(empresa.getNombre())) {
			this.empresas.add(empresa);
			// organizar();
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
	 * @return regresa un booleano que indica si el elemento se ha añadido a la
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
		if (obtenerEmpresa(nombre) != null) {
			return true;
		}
		return false;
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
		this.productosI = new ArrayList<>();
		this.empresas = new ArrayList<>();
	}

	/**
	 * metodo get para la lista de productos
	 * 
	 * @return regresa la lista de productos
	 */
	public ArrayList<ProductosInventario> getProductosI() {
		return productosI;
	}

	/**
	 * metodo set de la lista de productos
	 * 
	 * @param productosI
	 *            la nueva lista de productos
	 */
	public void setProductosI(ArrayList<ProductosInventario> productosI) {
		this.productosI = productosI;
	}

	/**
	 * meto get para la lista de empresas
	 * 
	 * @return regresa la lista de empresas
	 */
	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}

	/**
	 * metodo set para la lista de empresas
	 * 
	 * @param empresas
	 *            es la nueva lista de empresas
	 */
	public void setEmpresas(ArrayList<Empresa> empresas) {
		this.empresas = empresas;
	}

	// -----------------------------------------------------------------------------------------
	// //
	/**
	 * metodo que sirve para el ordenar por orden alfabetico la lista de
	 * productos del inventario utilizando el nombre
	 */
	@SuppressWarnings("unused")
	private void organizar() {
		if (productosI.size() > 1)
			quickSort(0, productosI.size() - 1);
	}

	/**
	 * metodo de ordenamiento que se usa para ordenar la lista de productos por
	 * nombres
	 * 
	 * @param limInferior
	 *            limite inferior de la lista (0)
	 * @param limSuperior
	 *            limite superior de la lista (productosI.size() - 1)
	 */
	private void quickSort(int limInferior, int limSuperior) {
		int i = limInferior, j = limSuperior;
		ProductosInventario pivote = productosI.get((limInferior + limSuperior) / 2);
		do {
			while ((productosI.get(i).CompareTo(pivote)) < 0) {
				i++;
			}
			while ((productosI.get(j).CompareTo(pivote)) > 0) {
				j--;
			}
			if (i <= j) {
				// Animation aux = animaciones[i];
				// animaciones[i] = animaciones[j];
				// animaciones[j] = aux;

				ProductosInventario aux = productosI.get(i);
				productosI.remove(i);
				productosI.add(i, productosI.get(j));
				productosI.remove(j);
				productosI.add(j, aux);
				i++;
				j--;
			}
		} while (i <= j);

		if (j > limInferior) {
			quickSort(limInferior, j);
		}
		if (i < limSuperior) {
			quickSort(i, limSuperior);
		}
	}

}
