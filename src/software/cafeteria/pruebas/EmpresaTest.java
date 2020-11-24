package software.cafeteria.pruebas;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import software.cafeteria.entidades.Empresa;

public class EmpresaTest extends TestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testEmpresa() {
		Empresa a = new Empresa("coca-cola");
		assertEquals("coca-c", a.getNombre());
	}

}
