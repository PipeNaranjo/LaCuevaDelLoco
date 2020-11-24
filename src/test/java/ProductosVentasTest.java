package test.java;

import org.junit.Assert;
import org.junit.Test;

import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.ProductosInventario;
import main.java.software.cafeteria.entidades.ProductosVentas;

public class ProductosVentasTest {

	private static final String NOMBRE = "coca-cola";
	private static final String NOMBREPRODUCTO = NOMBRE + " personal";
	private static final Empresa EMPRESA = new Empresa(NOMBRE);
	private static final String TIPO = "Bebida";

	@Test
	public void agregarCantidadProductoV() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		ProductosVentas b = new ProductosVentas(a, 5);
		b.restarCantidad(1);
		Assert.assertEquals(4, b.getCantidad());
	}

	@Test
	public void restarCantidadProductoV() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		ProductosVentas b = new ProductosVentas(a, 5);
		b.agregarCantidad(1);
		Assert.assertEquals(6, b.getCantidad());
	}

	@Test
	public void compararProductoV1() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		ProductosVentas b = new ProductosVentas(a, 5);
		Assert.assertEquals(0, b.CompareTo(NOMBREPRODUCTO));
	}

	@Test
	public void compararProductoV2() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		ProductosVentas b = new ProductosVentas(a, 5);
		Assert.assertEquals(0, b.CompareTo(b));
	}

}
