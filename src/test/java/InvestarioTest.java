package test.java;


import org.junit.Assert;
import org.junit.Test;

import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.ProductosInventario;
import main.java.software.cafeteria.logica.Inventario;

public class InvestarioTest {

	private static final String NOMBRE = "coca-cola";
	private static final String NOMBRE2 = "Postobon";
	private static final String NOMBREPRODUCTO = NOMBRE + " personal";
	private static final Empresa EMPRESA = new Empresa(NOMBRE);
	private static final String TIPO = "Bebida";
	private static final Empresa EMPRESA2 = new Empresa(NOMBRE2);

	Inventario b = new Inventario();

	@Test
	public void verificarExistenciaProductoI() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		b.agregarProducto(a);
		Assert.assertTrue(b.verficarExistenciaProducto("123"));
	}

	@Test
	public void obtenerProductoI() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		b.agregarProducto(a);
		Assert.assertEquals(a, b.obtenerproductoI("123"));
	}

	@Test
	public void modificarProductoI() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		b.agregarProducto(a);
		b.modificarProducto(a, "123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1200);
		Assert.assertEquals(1200, b.obtenerproductoI("123").getPrecio());
	}

	@Test
	public void borrarProductoI() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		b.agregarProducto(a);
		Assert.assertTrue(b.verficarExistenciaProducto("123"));
		b.borrarProductoI("123");
		Assert.assertFalse(b.verficarExistenciaProducto("123"));
	}

	@Test
	public void agregarAlProductoI() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		b.agregarProducto(a);
		b.agregarAlInventario(b.obtenerproductoI("123"), 10);
		Assert.assertEquals(50, b.obtenerproductoI("123").getCantidad());
	}

	@Test
	public void restarAlProductoI() {
		ProductosInventario a = new ProductosInventario("123", NOMBREPRODUCTO, EMPRESA, 20, 19, 800, 40, TIPO, 1000);
		b.agregarProducto(a);
		b.restarAlInventario(b.obtenerproductoI("123"), 10);
		Assert.assertEquals(30, b.obtenerproductoI("123").getCantidad());
	}

	@Test
	public void agregarEmpresaI() {
		Empresa a = EMPRESA2;
		b.agregarEmpresa(a);
		Assert.assertEquals(1, b.getEmpresas().size());
	}

	@Test
	public void agregarEmpresaI2() {
		b.agregarEmpresa(NOMBRE2);
		Assert.assertEquals(1, b.getEmpresas().size());
	}

	@Test
	public void verificarExistenciaEmpresaI() {
		b.agregarEmpresa(NOMBRE2);
		Assert.assertTrue(b.verficarExistenciaEmpresa(NOMBRE2));
	}

	@Test
	public void obtenerEmpresaI() {
		Empresa a = EMPRESA2;
		b.agregarEmpresa(a);
		Assert.assertEquals(a, b.obtenerEmpresa(NOMBRE2));
	}

	@Test
	public void borrarEmpresaI() {
		b.agregarEmpresa(NOMBRE2);
		Assert.assertTrue(b.verficarExistenciaEmpresa(NOMBRE2));
		b.borrarEmpresas(NOMBRE2);
		Assert.assertFalse(b.verficarExistenciaEmpresa(NOMBRE2));
	}

}
