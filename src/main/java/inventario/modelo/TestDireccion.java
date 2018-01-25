package inventario.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class TestDireccion {

	@Test
	public void testConstructor() throws ModelConstraintViolationException, 
										EmptyArgumentException {
		new Direccion("GUATEMALA","GUATEMALA", (byte) 5);
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testConstructorWithBadDepartamento() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new Direccion("","GUATEMALA", (byte) 5);
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testConstructorWithBadCiudad() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new Direccion("GUATEMALA","", (byte) 5);
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorWithBadZona() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new Direccion("GUATEMALA","GUATEMALA", (byte) 0);
	}
	
	@Test
	public void testEquals() throws ModelConstraintViolationException, 
									EmptyArgumentException {
		Direccion d1 = new Direccion("GUATEMALA","GUATEMALA",(byte) 5);
		Direccion d2 = new Direccion("Guatemala","Guatemala",(byte) 5);
		assertEquals(d1, d2);
		Direccion d3 = new Direccion("GUATEMALA","MIXCO",(byte) 5);
		assertNotEquals(d1, d3);
		Direccion d4 = new Direccion("QUETZALTENANGO","GUATEMALA",(byte) 5);
		assertNotEquals(d1, d4);
		Direccion d5 = new Direccion("GUATEMALA","GUATEMALA",(byte) 4);
		assertNotEquals(d1, d5);
	}

}
