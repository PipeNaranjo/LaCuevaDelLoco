package test.java;

import org.junit.Assert;
import org.junit.Test;

import main.java.software.cafeteria.entidades.Empresa;

public class EmpresaTest {

	private static final String NOMBRE = "coca-cola";

	@Test
	public void testEmpresa() {
		Empresa a = new Empresa(NOMBRE);
		Assert.assertEquals(NOMBRE, a.getNombre());
	}

}
