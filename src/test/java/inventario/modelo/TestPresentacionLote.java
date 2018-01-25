package inventario.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Test;

import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class TestPresentacionLote {

	@Test
	public void testConstructor() throws ModelConstraintViolationException, 
										 EmptyArgumentException {
		new PresentacionLote(1,"LATA",1,12,new BigDecimal("4.50"),
				new BigDecimal("5.00"));
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorWithBadIdProducto() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new PresentacionLote(0,"LATA",1,12,new BigDecimal("4.50"), // ID de producto invalido
				new BigDecimal("5.00"));
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testConstructorWithBadPresentacion() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new PresentacionLote(1,"",1,12,new BigDecimal("4.50"), // presentacion invalida
				new BigDecimal("5.00"));
	}

	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorWithBadNumeroLote() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new PresentacionLote(1,"LATA",0,12,new BigDecimal("4.50"), // numero de lote invalido
				new BigDecimal("5.00"));
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorWithBadCantidad() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new PresentacionLote(1,"LATA",1,-1,new BigDecimal("4.50"), // cantidad invalida
				new BigDecimal("5.00"));
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorWithBadCostoI() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new PresentacionLote(1,"LATA",1,12,new BigDecimal("-1.00"), // costo invalido(negativo)
				new BigDecimal("5.00"));
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorWithBadCostoII() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new PresentacionLote(1,"LATA",1,12,new BigDecimal("5.50"), // costo invalido(mayor al precio)
				new BigDecimal("5.00"));
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorWithBadPrecioI() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new PresentacionLote(1,"LATA",1,12,new BigDecimal("4.00"), 
				new BigDecimal("-1.00"));// precio invalido(negativo)
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorWithBadPrecioII() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		new PresentacionLote(1,"LATA",1,12,new BigDecimal("4.00"), 
				new BigDecimal("3.50"));// precio invalido(menor al costo)
	}
	
	@Test
	public void testEquals() throws ModelConstraintViolationException, EmptyArgumentException {
		PresentacionLote pl1 = new PresentacionLote(1,"LATA",1,12,new BigDecimal("4.00"),
				new BigDecimal("5.00"));
		PresentacionLote pl2 = new PresentacionLote(1,"lata",1,12,new BigDecimal("4.00"),
				new BigDecimal("5.00"));
		assertEquals(pl1, pl2);
		PresentacionLote pl3 = new PresentacionLote(2,"LATA",1,12,new BigDecimal("4.00"),
				new BigDecimal("5.00"));
		assertNotEquals(pl1, pl3);
		PresentacionLote pl4 = new PresentacionLote(1,"LITRO",1,12,new BigDecimal("5.00"),
				new BigDecimal("6.00"));
		assertNotEquals(pl1, pl4);
		PresentacionLote pl5 = new PresentacionLote(1,"LATA",2,12,new BigDecimal("4.00"),
				new BigDecimal("5.00"));
		assertNotEquals(pl1, pl5);
		PresentacionLote pl6 = new PresentacionLote(1,"LATA",1,12,new BigDecimal("7.00"),
				new BigDecimal("9.00"));
		assertEquals(pl1, pl6);
	}
	
}
