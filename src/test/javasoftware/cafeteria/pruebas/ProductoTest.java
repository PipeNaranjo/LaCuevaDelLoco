package test.javasoftware.cafeteria.pruebas;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.Producto;


public class ProductoTest extends TestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testClone() {
		Producto a = new Producto("123", "coca-cola personal", new Empresa("coca-cola"), 20, 19, 800, 1000);
		Producto b = a.clone();

		Assert.assertEquals(a.getCodigoDeBarras(), b.getCodigoDeBarras());
	}

}
