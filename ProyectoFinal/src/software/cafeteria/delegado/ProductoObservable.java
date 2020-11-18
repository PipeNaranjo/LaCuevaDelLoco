package software.cafeteria.delegado;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import software.cafeteria.entidades.Producto;
import software.cafeteria.entidades.ProductosInventario;

public class ProductoObservable {

	private StringProperty nombre;
	private StringProperty costo;
	private StringProperty iva;
	private StringProperty tipoProducto;
	private StringProperty cantidad;
	private StringProperty precio;
	private StringProperty codigoBarras;
	private StringProperty presentacion;
	private StringProperty empresa;
	private ProductosInventario productoInventario;
	private Producto producto;

	public ProductoObservable() {

	}

	public ProductoObservable(Producto producto, int cantidad) {
		this.nombre = new SimpleStringProperty(producto.getNombre());
		this.precio = new SimpleStringProperty(producto.getPrecio() + "");
		this.costo = new SimpleStringProperty(producto.getCosto() + "");
		if (producto.getIva() == 0) {
			this.iva = new SimpleStringProperty("Exento");
		} else {
			this.iva = new SimpleStringProperty(producto.getIva() + "");
		}

		this.cantidad = new SimpleStringProperty(cantidad + "");
		this.codigoBarras = new SimpleStringProperty(producto.getCodigoDeBarras());
		this.presentacion = new SimpleStringProperty(producto.getPresentacion() + "");
		this.empresa = new SimpleStringProperty(producto.getEmpresa().getNombre());
		this.producto = producto;

	}

	public ProductoObservable(ProductosInventario producto) {
		this.nombre = new SimpleStringProperty(producto.getNombre());
		this.precio = new SimpleStringProperty(producto.getPrecio() + "");
		this.costo = new SimpleStringProperty(producto.getCosto() + "");
		if (producto.getIva() == 0) {
			this.iva = new SimpleStringProperty("Exento");
		} else {
			this.iva = new SimpleStringProperty(producto.getIva() + "");
		}
		this.tipoProducto = new SimpleStringProperty(producto.getTipo());
		this.cantidad = new SimpleStringProperty(producto.getCantidad() + "");
		this.codigoBarras = new SimpleStringProperty(producto.getCodigoDeBarras());
		this.presentacion = new SimpleStringProperty(producto.getPresentacion() + "");
		this.empresa = new SimpleStringProperty(producto.getEmpresa().getNombre());
		this.productoInventario = producto;

	}

	public ProductoObservable(String nombre, String costo, String iva, String tipo, String cantidad, String precio,
			String codigoBarras, String presentacion, String empresa, ProductosInventario productoInventario) {
		super();
		this.nombre = new SimpleStringProperty(productoInventario.getNombre());
		this.precio = new SimpleStringProperty(productoInventario.getPrecio() + "");
		this.costo = new SimpleStringProperty(productoInventario.getCosto() + "");
		if (productoInventario.getIva() == 0) {
			this.iva = new SimpleStringProperty("Exento");
		} else {
			this.iva = new SimpleStringProperty(productoInventario.getIva() + "");
		}
		this.tipoProducto = new SimpleStringProperty(productoInventario.getTipo());
		this.cantidad = new SimpleStringProperty(cantidad);
		this.codigoBarras = new SimpleStringProperty(productoInventario.getCodigoDeBarras());
		this.presentacion = new SimpleStringProperty(productoInventario.getPresentacion() + "");
		this.empresa = new SimpleStringProperty(productoInventario.getEmpresa().getNombre());
		this.productoInventario = productoInventario;
	}

	public ProductosInventario getProductoInventario() {
		return productoInventario;
	}

	public void setProductoInventario(ProductosInventario productoInventario) {
		this.productoInventario = productoInventario;
	}

	public StringProperty getCosto() {
		return costo;
	}

	public void setCosto(StringProperty costo) {
		this.costo = costo;
	}

	public StringProperty getIva() {
		return iva;
	}

	public void setIva(StringProperty iva) {
		this.iva = iva;
	}

	public StringProperty getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(StringProperty tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public StringProperty getCantidad() {
		return cantidad;
	}

	public void setCantidad(StringProperty cantidad) {
		this.cantidad = cantidad;
	}

	public StringProperty getPrecio() {
		return precio;
	}

	public void setPrecio(StringProperty precio) {
		this.precio = precio;
	}

	public StringProperty getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(StringProperty codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public StringProperty getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(StringProperty presentacion) {
		this.presentacion = presentacion;
	}

	public StringProperty getEmpresa() {
		return empresa;
	}

	public void setEmpresa(StringProperty empresa) {
		this.empresa = empresa;
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Producto getProducto() {
		return producto;
	}

}
