package main.java.software.cafeteria.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import main.java.software.cafeteria.logica.Inventario;

/**
 * clase que guarda todos los datos del recibo de una venta
 * 
 * @author Sara Arias Quiroga
 * @author Andres Felipe Naranjo
 * @author Daniel Vargas Pelaez
 */
public class RegistroDeVentas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// // es el valor que se asignara en el siguiente recibo que se adjunte
	private int codigoRecibo;
	// es el valor que se le asignara en el siguiente informe fiscal que se genere
	private int codigoInformeFiscal;
	// es la lista de recibos guardad en las pociciones que corresponde a a�o, mes y dia del recibo
	private ArrayList<ArrayList<ArrayList<ArrayList<Recibo>>>> recibos;
	// lista de informes fiscales generados con anterioridad
	private ArrayList<InformeFiscal> informesFiscales;
	// lista de recibos que estan pendientes por incluir en el siguiente informe fiscal
	private ArrayList<Recibo> recibosPendientes;

	/**
	 * metodo contructor del registro de ventas
	 * @param codigoRecibo el codigo de barras con el cual empezaran a contar los recibos
	 * @param codigoInformeFiscal codigo con el cual empezaran a generarse los informes fiscales
	 */
	public RegistroDeVentas(int codigoRecibo, int codigoInformeFiscal) {
		this.codigoRecibo = codigoRecibo;
		this.codigoInformeFiscal = codigoInformeFiscal;
		recibos = new ArrayList<ArrayList<ArrayList<ArrayList<Recibo>>>>();
		informesFiscales = new ArrayList<InformeFiscal>();
		recibosPendientes = new ArrayList<Recibo>();
	}

	/**
	 * metodo que a�ade un recibo a la lista de recibos del dia
	 * @param recibo recibo el cual se quiere adjuntar
	 * @param inventario inventario de los productos en donde restar los productos vendidos
	 * @return regresa un boolena que indica que se ha a�adido el recibo con exito
	 */
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

	/**
	 * obtiene la lista de recibos de una fecha especifica
	 * @param fecha la fecha de la que se quiere obtenr los recibos en formato GregorianCalendar
	 * @return regresa la lista de fechas
	 */
	public ArrayList<Recibo> obtenerListaDeRecibos(GregorianCalendar fecha) {
		return recibos.get(fecha.get(Calendar.YEAR) - 2020).get(fecha.get(Calendar.MONTH))
				.get(fecha.get(Calendar.DAY_OF_MONTH) - 1);
	}

	/**
	 * obtener un Recibo en especifico ubicado en alguna fecha
	 * @param fecha la fecha en la cual se busca el recibo
	 * @param codigoDeBarras codigo de barras del recibo a buscar
	 * @return Recibo que contiene el codigo de barras especificado null 
	 * 		   si no se encuentra el recibo en esa fecha
	 */
	public Recibo obtenerRecibo(GregorianCalendar fecha, String id) {
		ArrayList<Recibo> lista = obtenerListaDeRecibos(fecha);
		for (Recibo i : lista) {
			if (i.getId().equals(id)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * remover un archivo de la lista en una fecha especifica
	 * @param fecha la fecha en la cual se busca el recibo
	 * @param codigoDeBarras codigo de barras del recibo a buscar
	 * @return regresa un booleano indicando si se encuentra y borra el archivo
	 */
	public boolean removerRecibo(GregorianCalendar fecha, String id) {
		Recibo r = obtenerRecibo(fecha, id);
		if (r != null) {
			recibos.remove(r);
			return true;
		}
		return false;
	}

	/**
	 * metodo que cierra el informe fiscal con los productos que aun estan pendientes
	 * para ser agregados a un informe
	 * @return regresa el informe fiscal calculado con los datos pendientes
	 */
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

	/**
	 * metodo que sirve para verificar si el a�o ha sido creado, y esta lista para el uso
	 * de lo contrario creara los a�os necesarios para llegar hasta este.
	 * @param fecha la fecha que se quiere verificar 
	 */
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

	/**
	 * metodo get del codigo actual para ponerle al siguiente recibo
	 * @return codigo del siguiente recibo
	 */
	public int getCodigoRecibo() {
		return codigoRecibo;
	}

	/**
	 * metodo set del codigo actual para ponerle al siguiente recibo
	 * @param codigoRecibo nuevo codigo de barras para poner en el siguiente recibo
	 */
	public void setCodigoRecibo(int codigoRecibo) {
		this.codigoRecibo = codigoRecibo;
	}

	/**
	 * metodo get del codigo actual para ponerle al siguiente informe fiscal
	 * @return codigo del siguiente informe fiscal
	 */
	public int getCodigoInformeFiscal() {
		return codigoInformeFiscal;
	}

	/**
	 * metodo set del codigo actual para ponerle al siguiente informe fiscal
	 * @param codigoRecibo nuevo codigo de barras para poner en el siguiente fiscal
	 */
	public void setCodigoInformeFiscal(int codigoInformeFiscal) {
		this.codigoInformeFiscal = codigoInformeFiscal;
	}

	/**
	 * metodo para obtener la lista de recibos y separados por 
	 * @return la lista completa de recibos
	 */
	public ArrayList<ArrayList<ArrayList<ArrayList<Recibo>>>> getRecibos() {
		return recibos;
	}

	/**
	 * metodo que sirve para cambiar la lista de los archivos
	 * @param recibos la nueva lista de recibos
	 */
	public void setRecibos(ArrayList<ArrayList<ArrayList<ArrayList<Recibo>>>> recibos) {
		this.recibos = recibos;
	}

	/**
	 * metodo get para la lista de Informes Fiscales
	 * @return regresa la lista de Informes Fiscales
	 */
	public ArrayList<InformeFiscal> getInformesFiscales() {
		return informesFiscales;
	}

	/**
	 * metodo set para la lista de Informes Fiscales
	 * @param informesFiscales nueva lista de Informes Fiscales
	 */
	public void setInformesFiscales(ArrayList<InformeFiscal> informesFiscales) {
		this.informesFiscales = informesFiscales;
	}

	/**
	 * metodo get de la lista de recibos pendientes por informe fiscal
	 * @return regresa lista de recibos pendientes por informe fiscal
	 */
	public ArrayList<Recibo> getRecibosPendientes() {
		return recibosPendientes;
	}

	/**
	 * metodo set de la lista de recibos pendientes por informe fiscal
	 * @param recibosPendientes nueva lista de recibos pendientes por informe fiscal
	 */
	public void setRecibosPendientes(ArrayList<Recibo> recibosPendientes) {
		this.recibosPendientes = recibosPendientes;
	}

}
