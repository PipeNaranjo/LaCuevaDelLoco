package test.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import main.java.software.cafeteria.entidades.Empresa;

public class EmpresaTest extends TestCase {

	private static final String NOMBRE = "coca-cola";

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
	public void testEmpresa() {
		Empresa a = new Empresa(NOMBRE);
		assertEquals(NOMBRE, a.getNombre());
	}

}
