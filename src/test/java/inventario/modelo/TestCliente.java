package inventario.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.zip.DataFormatException;

import org.junit.Test;

import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class TestCliente {

	@Test
	public void testConstructors() throws DataFormatException, 
	EmptyArgumentException, ModelConstraintViolationException {
		new Cliente("1234589762345","Jose","Perez","12345678","user@mail.com", 
				new Direccion("Guatemala","Guatemala",(byte)12));
		new Cliente("1234589762345","Jose","Perez","12345678",null, // cliente si email
				new Direccion("Guatemala","Guatemala",(byte)12));
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testBadNombre() throws DataFormatException, 
	EmptyArgumentException, ModelConstraintViolationException {
		new Cliente("1234589762345","","Perez","12345678","user@mail.com", 
				new Direccion("Guatemala","Guatemala",(byte)12));
	}

	@Test(expected = EmptyArgumentException.class)
	public void testBadApellido() throws DataFormatException, 
	EmptyArgumentException, ModelConstraintViolationException {
		new Cliente("1234589762345","Jose","","12345678","user@mail.com", 
				new Direccion("Guatemala","Guatemala",(byte)12));
	}
	
	@Test
	public void testEquals() throws DataFormatException, 
	EmptyArgumentException, ModelConstraintViolationException {
		Cliente c1 = new Cliente("1234589762345","Jose","Perez","12345678","user@mail.com", 
				new Direccion("Guatemala","Guatemala",(byte)12));
		Cliente c2 = new Cliente("1234589762345","Pedro","Sanchez","12344668","pedro@mail.com", 
				new Direccion("Quetzaltenango","Guatemala",(byte)10));
		assertEquals(c1, c2);
		Cliente c3 = new Cliente("2234589762345","Jose","Perez","12345678","user@mail.com", 
				new Direccion("Guatemala","Guatemala",(byte)12));
		assertNotEquals(c1, c3);
	}
	
}
