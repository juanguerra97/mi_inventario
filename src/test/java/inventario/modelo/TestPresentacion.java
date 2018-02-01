package inventario.modelo;

import static org.junit.Assert.*;

import org.junit.Test;

import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class TestPresentacion {

	@Test
	public void testConstructor() throws ModelConstraintViolationException, 
										EmptyArgumentException {
		new Presentacion(1,"LIBRA"); // argumentos validos
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testBadIDProducto() throws ModelConstraintViolationException, 
										EmptyArgumentException {
		new Presentacion(0,"NOMBRE");// id de producto invalido
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testPresentacion4() throws ModelConstraintViolationException, 
										EmptyArgumentException {
		new Presentacion(1,"");// nombre de presentacion invalido
	}

	@Test
	public void testEquals() throws ModelConstraintViolationException, 
										EmptyArgumentException {
		Presentacion p1 = new Presentacion(1,"LATA");
		Presentacion p2 = new Presentacion(2,"LATA");
		Presentacion p3 = new Presentacion(1,"LITRO");
		Presentacion p4 = new Presentacion(1,"LATA");
		assertEquals(p1, p4);
		assertNotEquals(p2, p1);
		assertNotEquals(p3, p1);
	}

}
