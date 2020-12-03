package test.java;

import org.junit.Assert;
import org.junit.Test;
import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.Producto;
import main.java.software.cafeteria.entidades.ProductosInventario;
import main.java.software.cafeteria.entidades.Recibo;
import main.java.software.cafeteria.entidades.RegistroDeVentas;
import main.java.software.cafeteria.logica.Inventario;

public class ProductoInventarioTest {
	private static final String NOMBRE = "coca-cola";
	private static final String NOMBREPRODUCTO1 = NOMBRE + " personal";
	private static final String NOMBREPRODUCTO2 = NOMBRE + " 2L";
	private static final String NOMBREPRODUCTO3 = "Gusti Papa";
	private static final String NOMBREPRODUCTO4 = "caracol coma rico";
	private static final String NOMBREPRODUCTO5 = "AGUILA ZERO BOTELLA RETORNABLE";
	private static final Empresa EMPRESA = new Empresa(NOMBRE);
	private static final Empresa EMPRESA2 = new Empresa("fried");
	private static final Empresa EMPRESA3 = new Empresa("quinvalle");
	private static final Empresa EMPRESA4 = new Empresa("Bavaria");
	private static final String CODIGOBARRAS = "7702004002419";
	private static final String TIPO = "Bebida";
	private static final String TIPO2 = "Snacks";
	private static final String TIPO3 = "Licor";

	Inventario inventario = new Inventario();

	@Test
	public void agregarCantidadProductoI() {
		ProductosInventario a = new ProductosInventario(
				new Producto("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 1000), 40, TIPO);
		a.agregarAlInventario(10);
		Assert.assertEquals(50, a.getCantidad());
	}

	@Test
	public void restarCantidadProductoI() {
		ProductosInventario a = new ProductosInventario(
				new Producto("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 1000), 40, TIPO);
		a.restarAlInventario(10);
		Assert.assertEquals(30, a.getCantidad());
	}

	@Test
	public void compararProductoI1() {
		ProductosInventario a = new ProductosInventario(
				new Producto("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 1000), 40, TIPO);
		Assert.assertEquals(0, a.compareTo("coca-cola personal"));
	}

	public void compararProductoI2() {
		Inventario e = new Inventario();
		RegistroDeVentas f = new RegistroDeVentas(0, 0);
		Recibo c = new Recibo(false);
		ProductosInventario a = new ProductosInventario(
				new Producto("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 1000), 40, TIPO);
		e.agregarAlInventario(a, 2);
		c.agregarProductos(a, 5);
		a = new ProductosInventario(new Producto("346", NOMBREPRODUCTO4, EMPRESA3, 20, 5, 800, 1100), 40, TIPO2);
		e.agregarAlInventario(a, 2);
		c.agregarProductos(a, 5);
		f.adjuntarUnRecibo(c, e);
		c = new Recibo(false);
		a = new ProductosInventario(new Producto("1234", NOMBREPRODUCTO2, EMPRESA, 20, 19, 2300, 2500), 40, TIPO);
		e.agregarAlInventario(a, 2);
		c.agregarProductos(a, 2);
		a = new ProductosInventario(new Producto("234", NOMBREPRODUCTO3, EMPRESA2, 20, 0, 1000, 1500), 40, TIPO2);
		e.agregarAlInventario(a, 2);
		c.agregarProductos(a, 5);

		f.adjuntarUnRecibo(c, e);

		Assert.assertEquals(2, f.getRecibos().size());
	}

	@Test
	public void agregarProductoTest() {

		ProductosInventario producto = new ProductosInventario(
				new Producto("123", NOMBREPRODUCTO1, EMPRESA, 20, 19, 800, 1000), 40, TIPO);

		Assert.assertTrue((inventario.agregarProducto(producto)));

	}

	@Test
	public void agregarProductoTest2() {
		int presentacion = 30;
		int iva = 19;
		int costo = 48800;
		int cantidad = 10;
		String tipo = TIPO3;
		int precio = 2500;
		ProductosInventario producto = new ProductosInventario(
				new Producto(CODIGOBARRAS, NOMBREPRODUCTO5, EMPRESA4, presentacion, iva, costo, precio), cantidad,
				tipo);
		inventario.agregarProducto(producto);

		Assert.assertFalse(inventario.agregarProducto(new ProductosInventario(
				new Producto(CODIGOBARRAS, NOMBREPRODUCTO5, EMPRESA4, presentacion, iva, costo, precio), cantidad,
				tipo)));
	}
}
