package inventario.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import inventario.modelo.error.EmptyArgumentException;

public class TestProveedor {

	@Test
	public void testConstructor() throws EmptyArgumentException {
		new Proveedor("GERMAN SANCHEZ");
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testConstructorWithInvalidNombre() throws EmptyArgumentException {
		new Proveedor("");
	}
	
	@Test
	public void testEquals() throws EmptyArgumentException {
		Proveedor p1 = new Proveedor("GERMAN SANCHEZ");
		Proveedor p2 = new Proveedor("German Sanchez");
		assertEquals(p1, p2);
		Proveedor p3 = new Proveedor("ORACIO FUENTES");
		assertNotEquals(p1, p3);
	}

}
