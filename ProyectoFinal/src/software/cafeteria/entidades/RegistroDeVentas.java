package software.cafeteria.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import software.cafeteria.logica.Inventario;

public class RegistroDeVentas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codigoRecibo;
	private int codigoInformeFiscal;
	private ArrayList<ArrayList<ArrayList<ArrayList<Recibo>>>> recibos;
	private ArrayList<InformeFiscal> informesFiscales;
	private ArrayList<Recibo> recibosPendientes;

	public RegistroDeVentas(int codigoRecibo, int codigoInformeFiscal) {
		this.codigoRecibo = codigoRecibo;
		this.codigoInformeFiscal = codigoInformeFiscal;
		recibos = new ArrayList<ArrayList<ArrayList<ArrayList<Recibo>>>>();
		informesFiscales = new ArrayList<InformeFiscal>();
		recibosPendientes = new ArrayList<Recibo>();
	}

	public boolean adjuntarUnRecibo(Recibo recibo, Inventario inventario) {
		GregorianCalendar fr = recibo.getFecha();
		verificarAnio(fr);
		recibo.setId(codigoRecibo);
		codigoRecibo++;
		recibos.get(fr.get(Calendar.YEAR) - 2020).get(fr.get(Calendar.MONTH)).get(fr.get(Calendar.DAY_OF_MONTH) - 1)
				.add(recibo);
		recibosPendientes.add(recibo);
		for (ProductosVentas a : recibo.getProductosV()) {
			inventario.obtenerproductoI(a.getProducto().getCodigoDeBarras()).restarAlInventario(a.getCantidad());
		}

		return true;
	}

	public ArrayList<Recibo> obtenerListaDeRecibos(GregorianCalendar fecha) {
		return recibos.get(fecha.get(Calendar.YEAR) - 2020).get(fecha.get(Calendar.MONTH))
				.get(fecha.get(Calendar.DAY_OF_MONTH) - 1);
	}

	public Recibo obtenerRecibo(GregorianCalendar fecha, String id) {
		ArrayList<Recibo> lista = obtenerListaDeRecibos(fecha);
		for (Recibo i : lista) {
			if (i.getId().equals(id)) {
				return i;
			}
		}
		return null;
	}

	public boolean removerRecibo(GregorianCalendar fecha, String id) {
		Recibo r = obtenerRecibo(fecha, id);
		if (r != null) {
			recibos.remove(r);
			return true;
		}
		return false;
	}

	public InformeFiscal generarInformeFiscal() {
		if (recibosPendientes.size() > 0) {
			InformeFiscal a = new InformeFiscal(codigoInformeFiscal, recibosPendientes);
			informesFiscales.add(a);
			codigoInformeFiscal++;
			recibosPendientes = new ArrayList<Recibo>();
			return a;
		}
		return null;
	}

	// -------------------------------------------------------------------------------------------------------------------
	// //

	private void verificarAnio(GregorianCalendar fecha) {
		// GregorianCalendar gc=new GregorianCalendar();
		while (recibos.size() < fecha.get(Calendar.YEAR) - 2019) {
			recibos.add(new ArrayList<ArrayList<ArrayList<Recibo>>>());
			for (int i = 0; i < 12; i++) {
				recibos.get(recibos.size() - 1).add(new ArrayList<ArrayList<Recibo>>());
				int diasMes = new GregorianCalendar(2019 + recibos.size(), i, 1).getActualMaximum(Calendar.DATE);
				for (int j = 0; j < diasMes; j++) {
					recibos.get(recibos.size() - 1).get(i).add(new ArrayList<Recibo>());
				}
			}
		}

	}

	// -------------------------------------------------------------------------------------------------------------------
	// //

	public int getCodigoRecibo() {
		return codigoRecibo;
	}

	public void setCodigoRecibo(int codigoRecibo) {
		this.codigoRecibo = codigoRecibo;
	}

	public int getCodigoInformeFiscal() {
		return codigoInformeFiscal;
	}

	public void setCodigoInformeFiscal(int codigoInformeFiscal) {
		this.codigoInformeFiscal = codigoInformeFiscal;
	}

	public ArrayList<ArrayList<ArrayList<ArrayList<Recibo>>>> getRecibos() {
		return recibos;
	}

	public void setRecibos(ArrayList<ArrayList<ArrayList<ArrayList<Recibo>>>> recibos) {
		this.recibos = recibos;
	}

	public ArrayList<InformeFiscal> getInformesFiscales() {
		return informesFiscales;
	}

	public void setInformesFiscales(ArrayList<InformeFiscal> informesFiscales) {
		this.informesFiscales = informesFiscales;
	}

	public ArrayList<Recibo> getRecibosPendientes() {
		return recibosPendientes;
	}

	public void setRecibosPendientes(ArrayList<Recibo> recibosPendientes) {
		this.recibosPendientes = recibosPendientes;
	}

}
