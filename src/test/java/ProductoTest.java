package test.java;

import org.junit.Assert;
import org.junit.Test;

import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.Producto;

public class ProductoTest {

	@Test
	public void testClone() {
		Producto a = new Producto("123", "coca-cola personal", new Empresa("coca-cola"), 20, 19, 800, 1000);
		Producto b = a.clone();

		Assert.assertEquals(a.getCodigoDeBarras(), b.getCodigoDeBarras());
	}

}
