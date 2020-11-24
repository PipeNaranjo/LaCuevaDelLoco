package test.java;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.InformeFiscal;
import main.java.software.cafeteria.entidades.ProductosInventario;
import main.java.software.cafeteria.entidades.Recibo;

public class InformeFiscalTest extends TestCase {

	private static final String NOMBRE = "coca-cola";
	private static final String NOMBREPRODUCTO1 = NOMBRE + " personal";
	private static final String NOMBREPRODUCTO2 = NOMBRE + " 2L";
	private static final String NOMBREPRODUCTO3 = "Gusti Papa";
	private static final String NOMBREPRODUCTO4 = "caracol coma rico";
	private static final Empresa EMPRESA = new Empresa(NOMBRE);
	private static final Empresa EMPRESA2 = new Empresa("fried");
	private static final Empresa EMPRESA3 = new Empresa("quinvalle");
	private static final String TIPO = "Bebida";
	private static final String TIPO2 = "Snacks";

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void totalDeLaCaja() {
		ArrayList<Recibo> b = new ArrayList<Recibo>();
		Recibo c = new Recibo(false);
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		c.agregarProductos(a, 5);
		a = new ProductosInventario("346", NOMBREPRODUCTO4, EMPRESA3, 20, 5, 800, 40, TIPO2, 1100);
		c.agregarProductos(a, 5);
		b.add(c);
		c = new Recibo(false);
		a = new ProductosInventario("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 40, TIPO, 2500);
		c.agregarProductos(a, 2);
		a = new ProductosInventario("234", NOMBREPRODUCTO3, EMPRESA2, 20, 0, 1000, 40, TIPO2, 1500);
		c.agregarProductos(a, 10);
		b.add(c);
		InformeFiscal d = new InformeFiscal(123, b);
		Assert.assertEquals((5000 + 5500 + 5000 + 15000), d.getTotalEnCaja());
	}

	@Test
	public void totalDeIvaIF() {
		ArrayList<Recibo> b = new ArrayList<Recibo>();
		Recibo c = new Recibo(false);
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		c.agregarProductos(a, 5);
		a = new ProductosInventario("346", NOMBREPRODUCTO4, EMPRESA3, 20, 5, 800, 40, TIPO2, 1100);
		c.agregarProductos(a, 5);
		b.add(c);
		c = new Recibo(false);
		a = new ProductosInventario("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 40, TIPO, 2500);
		c.agregarProductos(a, 2);
		a = new ProductosInventario("234", NOMBREPRODUCTO3, EMPRESA2, 20, 0, 1000, 40, TIPO2, 1500);
		c.agregarProductos(a, 5);
		b.add(c);
		InformeFiscal d = new InformeFiscal(123, b);

		Assert.assertEquals(((5000 * 19 / 100) + (5500 * 5 / 100) + (5000 * 19 / 100) + 0), d.getTotalIva());
	}

	@Test
	public void totalDeGananciaIF() {
		ArrayList<Recibo> b = new ArrayList<Recibo>();
		Recibo c = new Recibo(false);
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		c.agregarProductos(a, 5);
		a = new ProductosInventario("346", NOMBREPRODUCTO4, EMPRESA3, 20, 5, 800, 40, TIPO2, 1100);
		c.agregarProductos(a, 5);
		b.add(c);
		c = new Recibo(false);
		a = new ProductosInventario("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 40, TIPO, 2500);
		c.agregarProductos(a, 2);
		a = new ProductosInventario("234", NOMBREPRODUCTO3, EMPRESA2, 20, 0, 1000, 40, TIPO2, 1500);
		c.agregarProductos(a, 5);
		b.add(c);
		InformeFiscal d = new InformeFiscal(123, b);
		Assert.assertEquals((d.getTotalEnCaja() - d.getTotalIva()), d.getTotalGanancia());
	}

	@Test
	public void primerRecibo() {
		ArrayList<Recibo> b = new ArrayList<Recibo>();
		Recibo c = new Recibo(false);
		c.setId(1);
		b.add(c);
		c = new Recibo(false);
		c.setId(2);
		b.add(c);
		c = new Recibo(false);
		c.setId(3);
		b.add(c);
		c = new Recibo(false);
		c.setId(4);
		b.add(c);
		c = new Recibo(false);
		c.setId(5);
		b.add(c);
		InformeFiscal d = new InformeFiscal(123, b);
		Assert.assertEquals("1", d.numeroPrimerRecibo());
	}

	@Test
	public void ultimoRecibo() {
		ArrayList<Recibo> b = new ArrayList<Recibo>();
		Recibo c = new Recibo(false);
		c.setId(1);
		b.add(c);
		c = new Recibo(false);
		c.setId(2);
		b.add(c);
		c = new Recibo(false);
		c.setId(3);
		b.add(c);
		c = new Recibo(false);
		c.setId(4);
		b.add(c);
		c = new Recibo(false);
		c.setId(5);
		b.add(c);
		InformeFiscal d = new InformeFiscal(123, b);
		Assert.assertEquals("5", d.numeroUltimoRecibo());
	}

}
