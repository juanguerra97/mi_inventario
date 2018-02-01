package inventario.modelo;

import static org.junit.Assert.*;

import org.junit.Test;

import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class TestProducto {

	@Test
	public void testEquals() throws ModelConstraintViolationException, 
										EmptyArgumentException {
		Producto p1 = new Producto(1,"NARANJAS","EL NARANJERO");
		Producto p2 = new Producto(1,"IDREPETIDO","REP");
		Producto p3 = new Producto(2,"NARANJAS","EL NARANJERO");
		Producto p4 = new Producto(3,"LIMONES","EL LIMONERO");
		assertEquals(p1, p2);
		assertEquals(p1, p3);
		assertNotEquals(p4, p1);
	}

}
