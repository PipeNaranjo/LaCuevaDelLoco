package test.java;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.InformeFiscal;
import main.java.software.cafeteria.entidades.ProductosInventario;
import main.java.software.cafeteria.entidades.Recibo;
import main.java.software.cafeteria.entidades.RegistroDeVentas;
import main.java.software.cafeteria.logica.Inventario;

public class RegistroDeVentasTest {

	private static final String NOMBREPRODUCTO = "AGUILA ZERO BOTELLA RETORNABLE";
	private static final Empresa EMPRESA = new Empresa("Bavaria");
	private static final String CODIGOBARRAS = "7702004002419";
	private static final String TIPO = "Licor";

	Inventario inventario = new Inventario();

	@Test
	public void adjuntarUnReciboTest() {
		Recibo recibo = new Recibo(false);
		int cantidad = 3;
		ProductosInventario producto = new ProductosInventario(CODIGOBARRAS, NOMBREPRODUCTO, EMPRESA, 30, 19, 48800, 20,
				TIPO, 2500);
		inventario.agregarProducto(producto);

		recibo.agregarProductos(producto, cantidad);

		RegistroDeVentas registro = new RegistroDeVentas(0, 0);
		boolean resul = registro.adjuntarUnRecibo(recibo, inventario);

		Assert.assertTrue(resul);

	}

	@Test
	public void obtenerListaRecibosTest() {
		Recibo recibo = new Recibo(false);
		int cantidad = 3;
		ProductosInventario producto = new ProductosInventario(CODIGOBARRAS, NOMBREPRODUCTO, EMPRESA, 30, 19, 48800, 20,
				TIPO, 2500);
		inventario.agregarProducto(producto);

		recibo.agregarProductos(producto, cantidad);

		RegistroDeVentas registro = new RegistroDeVentas(0, 0);
		registro.adjuntarUnRecibo(recibo, inventario);

		ArrayList<Recibo> lista = registro.obtenerListaDeRecibos(new GregorianCalendar());

		Assert.assertTrue(lista.size() == 1);
	}

	@Test
	public void obtenerReciboTest() {
		Recibo recibo = new Recibo(false);
		int cantidad = 3;
		ProductosInventario producto = new ProductosInventario(CODIGOBARRAS, NOMBREPRODUCTO, EMPRESA, 30, 19, 48800, 20,
				TIPO, 2500);
		inventario.agregarProducto(producto);

		recibo.agregarProductos(producto, cantidad);

		RegistroDeVentas registro = new RegistroDeVentas(0, 0);
		registro.adjuntarUnRecibo(recibo, inventario);

		Recibo nuevoRecibo = registro.obtenerRecibo(new GregorianCalendar(), "0");

		Assert.assertNotNull(nuevoRecibo);
	}

	@Test
	public void removerRecibo() {
		Recibo recibo = new Recibo(false);
		int cantidad = 3;
		ProductosInventario producto = new ProductosInventario(CODIGOBARRAS, NOMBREPRODUCTO, EMPRESA, 30, 19, 48800, 20,
				TIPO, 2500);
		inventario.agregarProducto(producto);

		recibo.agregarProductos(producto, cantidad);

		RegistroDeVentas registro = new RegistroDeVentas(0, 0);
		registro.adjuntarUnRecibo(recibo, inventario);

		registro.removerRecibo(new GregorianCalendar(), "0");

		Recibo nuevo = registro.obtenerRecibo(new GregorianCalendar(), "0");

		Assert.assertNotNull(nuevo);
	}

	@Test
	public void generarInformeFiscalTest() {
		Recibo recibo = new Recibo(false);
		int cantidad = 3;
		ProductosInventario producto = new ProductosInventario(CODIGOBARRAS, NOMBREPRODUCTO, EMPRESA, 30, 19, 48800, 20,
				TIPO, 2500);
		inventario.agregarProducto(producto);

		recibo.agregarProductos(producto, cantidad);

		RegistroDeVentas registro = new RegistroDeVentas(0, 0);
		registro.adjuntarUnRecibo(recibo, inventario);

		InformeFiscal informeFiscal = registro.generarInformeFiscal();
		Assert.assertNotNull(informeFiscal);
	}

}
