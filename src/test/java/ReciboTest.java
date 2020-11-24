package test.java;

import org.junit.Assert;
import org.junit.Test;

import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.ProductosInventario;
import main.java.software.cafeteria.entidades.Recibo;

public class ReciboTest {

	private static final String NOMBRE = "coca-cola";
	private static final String NOMBREPRODUCTO1 = NOMBRE + " personal";
	private static final String NOMBREPRODUCTO2 = NOMBRE + " 2L";
	private static final String NOMBREPRODUCTO3 = "Gusti Papa";
	private static final Empresa EMPRESA = new Empresa(NOMBRE);
	private static final Empresa EMPRESA2 = new Empresa("fried");
	private static final String TIPO = "Bebida";
	private static final String TIPO2 = "Snacks";

	@Test
	public void calcularPrecioTotalRecibo() {
		Recibo c = new Recibo(true);
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		boolean respuesta = c.agregarProductos(a, 5);
		Assert.assertTrue(respuesta);
		a = new ProductosInventario("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 40, TIPO, 2500);
		respuesta = c.agregarProductos(a, 2);
		Assert.assertTrue(respuesta);
	}

	@Test
	public void calcularCambioTotalReciboTarjeta() {
		Recibo c = new Recibo(true);
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		c.agregarProductos(a, 5);
		a = new ProductosInventario("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 40, TIPO, 2500);
		c.agregarProductos(a, 4);
		Assert.assertEquals(0, c.getCambio());
	}

	@Test
	public void calcularCambioTotalReciboEfectivo() {
		Recibo c = new Recibo(false);
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		c.agregarProductos(a, 5);
		a = new ProductosInventario("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 40, TIPO, 2500);
		c.agregarProductos(a, 4);
		c.setEfectivoRegistrado(20000);
		Assert.assertEquals(5000, c.getCambio());
	}

	@Test
	public void totalIvaRecibo() {
		Recibo c = new Recibo(false);
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		c.agregarProductos(a, 5);
		a = new ProductosInventario("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 40, TIPO, 2500);
		c.agregarProductos(a, 4);
		a = new ProductosInventario("234", NOMBREPRODUCTO3, EMPRESA2, 20, 0, 1000, 40, TIPO2, 1500);
		c.agregarProductos(a, 5);
		int valor = c.getTotalIvaRecibo()[0][1];
		Assert.assertEquals(0, valor);
		valor = c.getTotalIvaRecibo()[1][1];
		Assert.assertEquals(((190 * 5) + (475 * 4)), valor);
	}

	@Test
	public void totalIvaGanancia() {
		Recibo c = new Recibo(false);
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		c.agregarProductos(a, 5);
		a = new ProductosInventario("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 40, TIPO, 2500);
		c.agregarProductos(a, 4);
		a = new ProductosInventario("234", NOMBREPRODUCTO3, EMPRESA2, 20, 0, 1000, 40, TIPO2, 1500);
		c.agregarProductos(a, 5);
		int valor = c.getTotalGananciaRecibo()[0][1];
		Assert.assertEquals((1500 * 5), valor);
		valor = c.getTotalGananciaRecibo()[1][1];
		Assert.assertEquals((810L * 5L) + (2025L * 4L), valor);
	}

}
