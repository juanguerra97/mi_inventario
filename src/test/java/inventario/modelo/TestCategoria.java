package inventario.modelo;

import static org.junit.Assert.*;

import org.junit.Test;

import inventario.modelo.error.EmptyArgumentException;

public class TestCategoria {

	@Test
	public void testEquals() throws EmptyArgumentException {
		Categoria c1 = new Categoria("COMESTIBLES");
		Categoria c2 = new Categoria("Comestibles");
		Categoria c3 = new Categoria("LACTEOS");
		assertEquals(c1, c2);
		assertNotEquals(c1, c3);
	}

	@Test(expected = EmptyArgumentException.class)
	public void testBadNombre() throws EmptyArgumentException {
		new Categoria("");
	}
	
}
