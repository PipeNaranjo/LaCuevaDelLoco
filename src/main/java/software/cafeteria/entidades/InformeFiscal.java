package main.java.software.cafeteria.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Clase que guarda todos los datos de productos en general
 * 
 * @author Sara Arias Quiroga
 * @author Andres Felipe Naranjo
 * @author Daniel Vargas Pelaez
 */
public class InformeFiscal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// numero de identicacion del informe fiscal
	private String numeroInforme;
	// fecha en la que se genera el informe fiscal
	private GregorianCalendar fecha;
	// suma del total de dinero recogido en efectivo
	private int efectivo;
	// suma del total de dinero recogido por tarjeta
	private int tarjeta;
	// valor a pagar de iva separado el porcentaje de iva
	private Integer[][] iva;
	// valor monetario de las ganacias recaudadas en los productos separados por
	// el iva
	private Integer[][] ganancia;
	// lista de recibos con los cuales se saca el informe fiscal
	private ArrayList<Recibo> lista;
	// valor de las bolsas vendidas en efectivo
	private int valorBolsasEfectivo;
	// valor de las bolsas vendidas por tarjeta
	private int valorBolsasTarjeta;

	/**
	 * metodo contructo del informe fiscal
	 * 
	 * @param numeroInforme
	 *            numero de identicacion del informe fiscal
	 * @param lista
	 *            lista de recibos con los cuales se saca el informe fiscal
	 */
	public InformeFiscal(int numeroInforme, ArrayList<Recibo> lista) {
		setNumeroInforme(numeroInforme);
		this.fecha = new GregorianCalendar();
		this.lista = lista;
		calcularValores(lista);
	}

	/**
	 * metodo que se encarga de hacer todos los calculos necesarios para sacar
	 * el informe fiscal
	 * 
	 * @param lista
	 *            lista de recibos con los cuales se saca el informe fiscal
	 */
	private void calcularValores(ArrayList<Recibo> lista) {
		Integer[] i = new Integer[100];
		Integer[] g = new Integer[100];
		for (Recibo r : lista) {
			int suma = 0;
			for (ProductosVentas p : r.getProductosV()) {
				int ivaP = p.getProducto().getIva();
				int $p = p.getProducto().getPrecio();
				int $piva = ($p * ivaP) / 100;
				if (i[ivaP] == null) {
					i[ivaP] = 0;
					g[ivaP] = 0;
				}
				i[ivaP] += $piva * p.getCantidad();
				g[ivaP] += ($p - $piva) * p.getCantidad();
				suma += $p * p.getCantidad();
			}
			if (r.getTarjeta()) {
				this.tarjeta += suma;
			} else {
				this.efectivo += suma;
			}
		}
		this.iva = new Recibo(true).organizarArreglo(i);
		this.ganancia = new Recibo(true).organizarArreglo(g);

	}

	/**
	 * metodo que calcula el total del dinero recaudado del balor de las bolsas
	 * pagadas en efectivo
	 * 
	 * @return valor del dinero recaudado
	 */
	public int calcularValorBolsasEfectivo() {

		int valor = 0;
		for (int i = 0; i < lista.size(); i++) {
			if (!lista.get(i).getTarjeta()) {

				valor += lista.get(i).getValorBolsas();
			}
		}

		return valor;
	}

	/**
	 * metodo que calcula el total del dinero recaudado del balor de las bolsas
	 * pagadas por tarjeta
	 * 
	 * @return valor del dinero recaudado
	 */
	public int calcularValorBolsasTarjeta() {
		int valor = 0;
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getTarjeta()) {
				valor += lista.get(i).getValorBolsas();
			}
		}

		return valor;
	}

	/**
	 * metodo que calcula total de dinero recaudado sin restar el iva tanto en
	 * tajeto como en efectivo
	 * 
	 * @return regresa el total de dinero recaudado + el valor de las bolsas en
	 *         efectivo
	 */
	public int getTotalEnCaja() {
		return this.efectivo + this.tarjeta + calcularValorBolsasEfectivo();
	}

	/**
	 * metodo que permite saber cual es la dentificacion del primer recibo de
	 * este informe fiscal
	 * 
	 * @return regresa el numero de identificacion del primer recibo
	 */
	public String numeroPrimerRecibo() {
		return lista.get(0).getId();
	}

	/**
	 * metodo que permite saber cual es la identificacion del ultimo recibo de
	 * este informe fiscal
	 * 
	 * @return regresa el numero de identicacion
	 */
	public String numeroUltimoRecibo() {
		return lista.get(lista.size() - 1).getId();
	}

	/**
	 * metodo get del numero de Informe fiscal
	 * 
	 * @return numero del informe fiscal
	 */
	public String getNumeroInforme() {
		return numeroInforme;
	}

	/**
	 * metodo set del numero de informe fiscal
	 * 
	 * @param numeroInforme
	 *            neuvo numero de informe fiscal
	 */
	public void setNumeroInforme(int numeroInforme) {
		this.numeroInforme = numeroInforme + "";
	}

	/**
	 * metodo get de la fecha en el que se genera el informe fiscal
	 * 
	 * @return regresa la fecha del informe fiscal
	 */
	public GregorianCalendar getFecha() {
		return fecha;
	}

	/**
	 * metodo get para saber el valor de dinero que se recogio en efectivo
	 * 
	 * @return regresa el valor de dinero en efectivo
	 */
	public int getEfectivo() {
		return efectivo;
	}

	/**
	 * metodo get para saber el valor de dinero que se recogio por tarjeta
	 * 
	 * @return regresa el valor de dinero en la tarjeta
	 */
	public int getTarjeta() {
		return tarjeta;
	}

	/**
	 * metodo para obtener la suma del iva separados por el porcentaje de iva de
	 * cada producto
	 * 
	 * @return regresa los suma del iva
	 */
	public Integer[][] getIva() {
		return iva;
	}

	/**
	 * metodo para obtener la suma de las ganancas separadas por el porcentaje
	 * de iva de cada producto
	 * 
	 * @return regresa el porcentaje del iva y la suma de las gancias
	 */
	public Integer[][] getGanancia() {
		return ganancia;
	}

	/**
	 * metodo que permite obtener la lista de productos pertenecientes a este
	 * informe fiscal
	 * 
	 * @return regresa la lista de productos
	 */
	public ArrayList<Recibo> getLista() {
		return lista;
	}

	/**
	 * metodo que calcula la ganancia total en este informe fiscal
	 * 
	 * @return regresa la suma de las ganancias
	 */
	public int getTotalGanancia() {
		int suma = 0;
		for (int i = 0; i < ganancia.length; i++) {
			suma += ganancia[i][1];
		}
		return suma;
	}

	/**
	 * metodo que calcula el valor del iva total a pagar por este informe fiscal
	 * 
	 * @return regresa la suma del valor del iva
	 */
	public int getTotalIva() {
		int suma = 0;
		for (int i = 0; i < iva.length; i++) {
			suma += iva[i][1];
		}
		return suma;
	}

	/**
	 * metodo que obtiene el valor de las bolsas pagadas en efectivo
	 * 
	 * @return valor del dinero recaudado en efectivo
	 */
	public int getValorBolsasEfectivo() {
		return valorBolsasEfectivo;
	}

	/**
	 * metodo set del valor de las bolsas en efectivo
	 * 
	 * @param valorBolsasEfectivo
	 */
	public void setValorBolsasEfectivo(int valorBolsasEfectivo) {
		this.valorBolsasEfectivo = valorBolsasEfectivo;
	}

	/**
	 * metodo que obtiene el valor de las bolsas pagadas con tarjeta
	 * 
	 * @return valor del dinero recaudado con tarjeta
	 */
	public int getValorBolsasTarjeta() {
		return valorBolsasTarjeta;
	}

	/**
	 * metodo set del valor total de las bolsas por tarjeta
	 * 
	 * @param valorBolsasTarjeta
	 */
	public void setValorBolsasTarjeta(int valorBolsasTarjeta) {
		this.valorBolsasTarjeta = valorBolsasTarjeta;
	}

}
