package software.cafeteria.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Recibo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private ArrayList<ProductosVentas> productosV;
	private GregorianCalendar fecha;
	private boolean tarjeta;
	private int EfectivoRegistrado;
	private int valorBolsas;

	public Recibo(boolean tarjeta) {
		this.id = "";
		this.productosV = new ArrayList<ProductosVentas>();
		this.fecha = new GregorianCalendar();
		this.tarjeta = tarjeta;
	}

	public boolean agregarProductos(ProductosInventario producto, int cantidad) {
		ProductosVentas a = new ProductosVentas(producto, cantidad);
		productosV.add(a);
		EfectivoRegistrado = 0;
		return true;
	}

	public int getPrecioTotal() {
		int suma = 0;
		for (ProductosVentas a : productosV) {
			suma += a.getProducto().getPrecio() * a.getCantidad();
		}
		return suma + valorBolsas;
	}

	public int getCambio() {
		if (tarjeta) {
			return 0;
		} else {
			return (EfectivoRegistrado - getPrecioTotal());
		}
	}

	// --------------------------------------------------------------------------//
	public String getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id + "";
	}

	public ArrayList<ProductosVentas> getProductosV() {
		return productosV;
	}

	public void setProductosV(ArrayList<ProductosVentas> productosV) {
		this.productosV = productosV;
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}

	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	public boolean getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(boolean tarjeta) {
		this.tarjeta = tarjeta;
	}

	public int getEfectivoRegistrado() {
		return EfectivoRegistrado;
	}

	public void setEfectivoRegistrado(int efectivoRegistrado) {
		EfectivoRegistrado = efectivoRegistrado;
	}

	public int getValorBolsas() {
		return valorBolsas;
	}

	public void setValorBolsas(int valorBolsas) {
		this.valorBolsas = valorBolsas;
	}

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
		ArrayList<Integer> vi = new ArrayList<Integer>();
		for (int j = 0; j < 100; j++) {
			if (i[j] != null) {
				vi.add(j);
			}
		}
		Integer[][] iva = new Integer[vi.size()][2];
		int j = 0;
		for (int n : vi) {
			iva[j][0] = n;
			iva[j][1] = i[n];
			j++;
		}
		return iva;
	}

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
		ArrayList<Integer> vi = new ArrayList<Integer>();
		for (int j = 0; j < 100; j++) {
			if (g[j] != null) {
				vi.add(j);
			}
		}
		Integer[][] ganancia = new Integer[vi.size()][2];
		int j = 0;
		for (int n : vi) {
			ganancia[j][0] = n;
			ganancia[j][1] = g[n];
			j++;
		}
		return ganancia;
	}

}
