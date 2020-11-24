package software.cafeteria.pruebas;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import software.cafeteria.entidades.Empresa;
import software.cafeteria.entidades.Producto;

public class ProductoTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClone() {
		Producto a = new Producto("123", "coca-cola personal", new Empresa("coca-cola"), 20, 19, 800, 1000);
		Producto b = a.clone();
		Assert.assertEquals(a.getCodigoDeBarras(), b.getCodigoDeBarras());
		Assert.assertEquals(a.getNombre(), b.getNombre());
		Assert.assertEquals(a.getEmpresa(), b.getEmpresa());
		Assert.assertEquals(a.getPresentacion(), b.getPresentacion());
		Assert.assertEquals(a.getIva(), b.getIva());
		Assert.assertEquals(a.getCosto(), b.getCosto());
		Assert.assertEquals(a.getPrecio(), b.getPrecio());
		Assert.assertNotEquals(a, b);
	}

}
