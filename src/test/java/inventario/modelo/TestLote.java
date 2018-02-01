package inventario.modelo;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import inventario.modelo.error.ModelConstraintViolationException;

public class TestLote {

	@Test
	public void testConstructor() throws ModelConstraintViolationException {
		// llamada a constructores con argumentos validos
		new Lote(1,1,LocalDate.now());
		new Lote(2,3,null);
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testBadID() throws ModelConstraintViolationException {
		new Lote(0,1,null);// id de producto invalido
	}

	@Test(expected = ModelConstraintViolationException.class)
	public void testBadNumeroLote() throws ModelConstraintViolationException {
		new Lote(1,0,null);// numero de lote invalido
	}
	
	@Test
	public void testEqualsObject() throws ModelConstraintViolationException {
		Lote l1 = new Lote(1,1,null);
		Lote l2 = new Lote(1,1,LocalDate.now());
		Lote l3 = new Lote(1,2,l2.getFechaVencimiento());
		assertEquals(l1, l2);
		assertNotEquals(l2, l3);
	}

}
