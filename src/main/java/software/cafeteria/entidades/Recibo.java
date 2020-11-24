package main.java.software.cafeteria.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * clase que guarda todos los datos del recibo de una venta
 * 
 * @author Sara Arias Quiroga
 * @author Andres Felipe Naranjo
 * @author Daniel Vargas Pelaez
 */
public class Recibo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// el numero de identificiacion del recibo
	private String id;
	// la lista de productos que se vendieron esta venta
	private ArrayList<ProductosVentas> productosV;
	// fecha en la que se realizo la venta
	private GregorianCalendar fecha;
	// un boolean que indica si el pago fue por tarjeta (true) o en efectivo
	// (false)
	private boolean tarjeta;
	// valor en que el cliente paga en efectivo en la caja
	private int EfectivoRegistrado;
	// valor del dinero total recaudado por la venta de bolsas
	private int valorBolsas;

	/**
	 * metodo constructor de la clase Recibo
	 * 
	 * @param tarjeta
	 *            envia verdadero si es el pago es con tarjeta y falso de lo
	 *            contrario
	 */
	public Recibo(boolean tarjeta) {
		this.id = "";
		this.productosV = new ArrayList<ProductosVentas>();
		this.fecha = new GregorianCalendar();
		this.tarjeta = tarjeta;
	}

	/**
	 * metodo que sirve para a�adir el producto a la lista de articulos
	 * comprados
	 * 
	 * @param producto
	 *            el producto del inventario que se quiere comprar
	 * @param cantidad
	 *            la cantidad de unidades del producto que se vende
	 * @return
	 */
	public boolean agregarProductos(ProductosInventario producto, int cantidad) {
		ProductosVentas a = new ProductosVentas(producto, cantidad);
		productosV.add(a);
		EfectivoRegistrado = 0;
		return true;
	}

	/**
	 * metodo para calcular el precio total a pagar por la compra
	 * 
	 * @return el precio a pagar + valor de las bolsas
	 */
	public int getPrecioTotal() {
		int suma = 0;
		for (ProductosVentas a : productosV) {
			suma += a.getProducto().getPrecio() * a.getCantidad();
		}
		return suma + valorBolsas;
	}

	/**
	 * metodo que sirve para calcular el cambio que debe recibir el cliente
	 * 
	 * @return cantidad de dinero a devolver al cliente
	 */
	public int getCambio() {
		if (tarjeta) {
			return 0;
		} else {
			return (EfectivoRegistrado - getPrecioTotal());
		}
	}

	// --------------------------------------------------------------------------//

	/**
	 * metodo metodo get del id de compra
	 * 
	 * @return regresa el id del recibo
	 */
	public String getId() {
		return id;
	}

	/**
	 * metodo set del id de compra
	 * 
	 * @param id
	 *            el valor del id del recibo como numero entero
	 */
	public void setId(int id) {
		this.id = id + "";
	}

	/**
	 * metodo get de la lista de productos
	 * 
	 * @return regresa la lista de productos
	 */
	public ArrayList<ProductosVentas> getProductosV() {
		return productosV;
	}

	/**
	 * metodo set de la lista de productos
	 * 
	 * @param ListaProductos
	 *            la nueva lista de los productos
	 */
	public void setProductosV(ArrayList<ProductosVentas> productosV) {
		this.productosV = productosV;
	}

	/**
	 * metodo get de la fecha del recibo
	 * 
	 * @return regresa la fecha del recibo en GregorianCalendar
	 */
	public GregorianCalendar getFecha() {
		return fecha;
	}

	/**
	 * metodo set de la fecha del recibo
	 * 
	 * @param fecha
	 *            una fecha en formato GregorianCalendar
	 */
	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	/**
	 * metodo get para obtener si la compra fue hecha por tarjeto o efectivo
	 * 
	 * @return regresa true si fue hecha por tarjeta o regresa false si fue
	 *         hecha con efectivo
	 */
	public boolean getTarjeta() {
		return tarjeta;
	}

	/**
	 * metodo set para determinar si la compra fue hecha por tarjeta o esfectivo
	 * 
	 * @param tarjeta
	 *            se le envia true para establecer pago por tarjeta o false para
	 *            efectivo
	 */
	public void setTarjeta(boolean tarjeta) {
		this.tarjeta = tarjeta;
	}

	/**
	 * metodo get para el efectivo que ha registrado el usuario
	 * 
	 * @return
	 */
	public int getEfectivoRegistrado() {
		return EfectivoRegistrado;
	}

	/**
	 * metodo set para el efectivo que el usuario entrega en caja al pagar
	 * 
	 * @param efectivoRegistrado
	 *            el valor que el usuario entrega en la caja
	 */
	public void setEfectivoRegistrado(int efectivoRegistrado) {
		EfectivoRegistrado = efectivoRegistrado;
	}

	/**
	 * Metodo que retorna el valor de las bolsas
	 * 
	 * @return valor de las bolsas recaudado
	 */
	public int getValorBolsas() {
		return valorBolsas;
	}

	/**
	 * Metodo set para el valor de las bolsas
	 * 
	 * @param valorBolsas
	 */
	public void setValorBolsas(int valorBolsas) {
		this.valorBolsas = valorBolsas;
	}

	/**
	 * metodo que calcula el iva total del recibo separado por el valor del iva
	 * 
	 * @return una matriz que contiene de cuanto es el iva y cual es el valor
	 *         del iva
	 */
	public Integer[][] getTotalIvaRecibo() {
		Integer[] i = new Integer[100];
		for (ProductosVentas p : productosV) {
			int ivaP = p.getProducto().getIva();
			int $piva = (p.getProducto().getPrecio() * ivaP) / 100;
			if (i[ivaP] == null) {
				i[ivaP] = 0;
			}
			i[ivaP] += $piva * p.getCantidad();
		}
		return organizarArreglo(i);
	}

	/**
	 * metodo que calcula la ganancia total del recibo separado por el valor del
	 * iva
	 * 
	 * @return una matriz que contiene de cuanto es el iva y cual es el valor de
	 *         la ganancia
	 */
	public Integer[][] getTotalGananciaRecibo() {
		Integer[] g = new Integer[100];
		for (ProductosVentas p : productosV) {
			int ivaP = p.getProducto().getIva();
			int $p = p.getProducto().getPrecio();
			int $piva = ($p * ivaP) / 100;
			if (g[ivaP] == null) {
				g[ivaP] = 0;
			}
			g[ivaP] += ($p - $piva) * p.getCantidad();
		}
		return organizarArreglo(g);
	}

	/**
	 * metodo que sirve para convertir un arreglo en una matriz que contiene
	 * solamente los los ivas que tienen valores y los valores respectivos
	 * 
	 * @param arreglo
	 *            el arreglo con los valores separados con iva sin organizar
	 * @return los ivas que tienen un valor y su valor
	 */
	public Integer[][] organizarArreglo(Integer[] arreglo) {
		ArrayList<Integer> vi = new ArrayList<Integer>();
		for (int j = 0; j < arreglo.length; j++) {
			if (arreglo[j] != null) {
				vi.add(j);
			}
		}
		Integer[][] array = new Integer[vi.size()][2];
		int j = 0;
		for (int n : vi) {
			array[j][0] = n;
			array[j][1] = arreglo[n];
			j++;
		}
		return array;
	}

}
