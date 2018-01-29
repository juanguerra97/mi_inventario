package inventario.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

import inventario.modelo.error.ModelConstraintViolationException;

public class TestTransaccion {

	@Test
	public void testConstructor() throws ModelConstraintViolationException {
		new Transaccion(1, LocalDate.of(1997, 4, 17), new BigDecimal("50.00"));
		new Transaccion(1, new BigDecimal("50.00"));
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testBadNumero() throws ModelConstraintViolationException {
		new Transaccion(0, LocalDate.of(1997, 4, 17), new BigDecimal("50.00"));// numero invalido
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testBadValor() throws ModelConstraintViolationException {
		new Transaccion(1, LocalDate.of(1997, 4, 17), new BigDecimal("-50.00"));
	}
	
	@Test
	public void testEquals() throws ModelConstraintViolationException {
		Transaccion t1 = new Transaccion(1, LocalDate.of(1997, 4, 17), new BigDecimal("50.00"));
		Transaccion t2 = new Transaccion(1, new BigDecimal("100.00"));
		assertEquals(t1, t2);
		Transaccion t3 = new Transaccion(2, LocalDate.of(1997, 4, 17), new BigDecimal("50.00"));
		assertNotEquals(t1, t3);
	}

}
