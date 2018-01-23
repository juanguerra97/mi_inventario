package inventario.modelo;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class TestLote {

	@Test
	public void testLote() {
		// llamada a constructores con argumentos validos
		new Lote(1,1,LocalDate.now());
		new Lote(2,3,null);
	}
	
	@Test(expected = AssertionError.class)
	public void testLote2() {
		new Lote(0,1,null);// id de producto invalido
	}

	@Test(expected = AssertionError.class)
	public void testLote3() {
		new Lote(1,0,null);// numero de lote invalido
	}
	
	@Test
	public void testEqualsObject() {
		Lote l1 = new Lote(1,1,null);
		Lote l2 = new Lote(1,1,LocalDate.now());
		Lote l3 = new Lote(1,2,l2.getFechaVencimiento());
		assertEquals(l1, l2);
		assertNotEquals(l2, l3);
	}

}
