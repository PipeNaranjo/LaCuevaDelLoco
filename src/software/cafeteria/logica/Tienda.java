package software.cafeteria.logica;

import java.io.Serializable;
import java.util.ArrayList;

import software.cafeteria.entidades.RegistroDeVentas;

public class Tienda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> iva;
	private Inventario inventario;

	private RegistroDeVentas registroVentas;

	public Tienda() {
		inventario = new Inventario();
		registroVentas = new RegistroDeVentas(0, 0);
		iva = new ArrayList<String>();
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public RegistroDeVentas getRegistroVentas() {
		return registroVentas;
	}

	public void setRegistroVentas(RegistroDeVentas registroVentas) {
		this.registroVentas = registroVentas;
	}

	public ArrayList<String> getIva() {
		return iva;
	}

	public void setIva(ArrayList<String> iva) {
		this.iva = iva;
	}

}
