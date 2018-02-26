package inventario.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.math.BigDecimal;
import org.junit.Test;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class TestItemCompra {

	@Test
	public void testConstructor()
throws ModelConstraintViolationException, EmptyArgumentException {
		new ItemCompra(1, "BEBIDA", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testBadNumTransaccion() 
			throws ModelConstraintViolationException, EmptyArgumentException  {
		new ItemCompra(0, "BEBIDA", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testBadIdProducto() 
			throws ModelConstraintViolationException, EmptyArgumentException  {
		new ItemCompra(1, "", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testBadMarca() 
			throws ModelConstraintViolationException, EmptyArgumentException  {
		new ItemCompra(1, "BEBIDA", "", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testBadPresentacion() 
			throws ModelConstraintViolationException, EmptyArgumentException  {
		new ItemCompra(1, "PRODUCTO", "MARCA", "", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testBadLote() 
			throws ModelConstraintViolationException, EmptyArgumentException  {
		new ItemCompra(1, "PRODUCTO", "MARCA", "PRESENTACION", 0, 
				new BigDecimal("8.00"), 12, "MIGUEL");
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testBadCosto() 
			throws ModelConstraintViolationException, EmptyArgumentException  {
		new ItemCompra(1, "PRODUCTO", "MARCA", "PRESENTACION", 1, 
				new BigDecimal("-8.00"), 12, "MIGUEL");
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testBadCantidad() 
			throws ModelConstraintViolationException, EmptyArgumentException  {
		new ItemCompra(1, "PRODUCTO", "MARCA", "PRESENTACION", 1, 
				new BigDecimal("8.00"), 0, "MIGUEL");
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testBadProveedor() 
			throws ModelConstraintViolationException, EmptyArgumentException  {
		new ItemCompra(1, "PRODUCTO", "MARCA", "PRESENTACION", 1, 
				new BigDecimal("8.00"), 1, "");
	}
	
	@Test
	public void testEquals() 
			throws ModelConstraintViolationException, EmptyArgumentException {
		ItemCompra ic1 = new ItemCompra(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
		ItemCompra ic2 = new ItemCompra(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("10.00"), 10, "MIGUEL");
		assertEquals(ic1, ic2);
		ItemCompra ic3 = new ItemCompra(2, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
		assertNotEquals(ic3, ic1);
		ItemCompra ic4 = new ItemCompra(1, "PRODUCTO2", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
		assertNotEquals(ic4, ic1);
		ItemCompra ic5 = new ItemCompra(1, "PRODUCTO", "MARCA II", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
		assertNotEquals(ic5, ic1);
		ItemCompra ic6 = new ItemCompra(1, "PRODUCTO", "MARCA", "LATA 500ML", 1, 
				new BigDecimal("8.00"), 12, "MIGUEL");
		assertNotEquals(ic6, ic1);
		ItemCompra ic7 = new ItemCompra(1, "PRODUCTO", "MARCA II", "BOTELLA 500ML", 2, 
				new BigDecimal("8.00"), 12, "MIGUEL");
		assertNotEquals(ic7, ic1);
	}

}
