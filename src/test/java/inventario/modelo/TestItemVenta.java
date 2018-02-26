package inventario.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.util.zip.DataFormatException;

import org.junit.Test;

import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

public class TestItemVenta {

	@Test
	public void testConstructorI() throws ModelConstraintViolationException, 
	EmptyArgumentException, DataFormatException {
		new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "1234567852345");
	}
	
	@Test
	public void testConstructorII() throws ModelConstraintViolationException, 
	EmptyArgumentException, DataFormatException {
		new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				"Marcos", new BigDecimal("8.00"), 12);
	}
	
	@Test
	public void testConstructorIII() throws ModelConstraintViolationException, 
	EmptyArgumentException, DataFormatException {
		new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				 new BigDecimal("8.00"), 12, "1234567852345", "Marcos");
	}
	
	@Test(expected = ModelConstraintViolationException.class)
	public void testConstructorIV() throws ModelConstraintViolationException, 
	EmptyArgumentException, DataFormatException {
		new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				 new BigDecimal("8.00"), 12, null, null);
	}
	
	@Test(expected = DataFormatException.class)
	public void testBadDPI() throws ModelConstraintViolationException, 
	EmptyArgumentException, DataFormatException {
		new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "1234567850345");
	}

	@Test(expected = EmptyArgumentException.class)
	public void testBadDPI_II() throws ModelConstraintViolationException, 
	EmptyArgumentException, DataFormatException {
		new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "");
	}
	
	@Test(expected = EmptyArgumentException.class)
	public void testBadNombre() throws ModelConstraintViolationException, 
	EmptyArgumentException, DataFormatException {
		new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				"", new BigDecimal("8.00"), 12);
	}
	
	@Test
	public void testEquals() throws ModelConstraintViolationException, 
	EmptyArgumentException, DataFormatException {
		ItemVenta iv1 = new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("8.00"), 12, "1234567852345");
		ItemVenta iv2 = new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				"Marcos", new BigDecimal("8.00"), 12);
		assertEquals(iv1, iv2);
		ItemVenta iv3 = new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("20.00"), 10, "1234567852345");
		assertEquals(iv1, iv3);
		ItemVenta iv4 = new ItemVenta(2, "PRODUCTO", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("20.00"), 10, "1234567852345");
		assertNotEquals(iv4, iv1);
		ItemVenta iv5 = new ItemVenta(1, "PRODUCTO2", "MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("20.00"), 10, "1234567852345");
		assertNotEquals(iv5, iv1);
		ItemVenta iv6 = new ItemVenta(1, "PRODUCTO", "OTRA MARCA", "BOTELLA 500ML", 1, 
				new BigDecimal("20.00"), 10, "1234567852345");
		assertNotEquals(iv6, iv1);
		ItemVenta iv7 = new ItemVenta(1, "PRODUCTO", "MARCA", "OTRA BOTELLA 500ML", 1, 
				new BigDecimal("20.00"), 10, "1234567852345");
		assertNotEquals(iv7, iv1);
		ItemVenta iv8 = new ItemVenta(1, "PRODUCTO", "MARCA", "BOTELLA 500ML", 5, 
				new BigDecimal("20.00"), 10, "1234567852345");
		assertNotEquals(iv8, iv1);
	}
	
}
