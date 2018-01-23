package inventario.modelo;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPresentacion {

	@Test
	public void testPresentacion() {
		new Presentacion(1,"LIBRA"); // argumentos validos
	}
	
	@Test(expected = AssertionError.class)
	public void testPresentacion2() {
		new Presentacion(0,"NOMBRE");// id de producto invalido
	}
	
	@Test(expected = AssertionError.class)
	public void testPresentacion3() {
		new Presentacion(1,null);// nombre de presentacion invalido
	}
	
	@Test(expected = AssertionError.class)
	public void testPresentacion4() {
		new Presentacion(1,"");// nombre de presentacion invalido
	}

	@Test
	public void testEquals() {
		Presentacion p1 = new Presentacion(1,"LATA");
		Presentacion p2 = new Presentacion(2,"LATA");
		Presentacion p3 = new Presentacion(1,"LITRO");
		Presentacion p4 = new Presentacion(1,"LATA");
		assertEquals(p1, p4);
		assertNotEquals(p2, p1);
		assertNotEquals(p3, p1);
	}

}
