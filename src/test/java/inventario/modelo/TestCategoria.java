package inventario.modelo;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCategoria {

	@Test
	public void testEquals() {
		Categoria c1 = new Categoria("COMESTIBLES");
		Categoria c2 = new Categoria("Comestibles");
		Categoria c3 = new Categoria("LACTEOS");
		assertEquals(c1, c2);
		assertNotEquals(c1, c3);
	}
	
	@Test(expected = AssertionError.class)
	public void assertNull() {
		new Categoria(null);
	}

	@Test(expected = AssertionError.class)
	public void assertNomEmpty() {
		new Categoria("");
	}
	
}
