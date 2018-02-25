package inventario.modelo;

import java.math.BigDecimal;
import java.util.zip.DataFormatException;
import static inventario.modelo.Resources.STRINGS_MODELO;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;
import inventario.modelo.validacion.Validacion;

/**
 * Clase para representar los datos de un elemento de una venta
 * @author juang
 *
 */
public class ItemVenta extends ItemTransaccion {

	private static final long serialVersionUID = 1L;

	private String dpiCliente;
	private String nombreCliente;
	
	/**
	 * Constructor
	 * @param numeroTransaccion numero de la transaccion a la que pertenece el item
	 * @param nomProducto nombre del producto, no debe ser null
	 * @param marcaProducto marca del producto, no debe ser null
	 * @param presentacionProducto presentacion del producto, no debe ser null
	 * @param loteProducto lote del producto
	 * @param valorUnitario valor unitario del producto, no puede ser null
	 * @param cantidad cantidad del producto
	 * @param dpiCliente DPI del cliente, si no se conoce debe ser null(siempre y cuando se provea un nombre)
	 * @param nombreCliente nombre del cliente, debe ser null si no se conoce(siempre y cuando se provea el DPI)
	 * @throws ModelConstraintViolationException si el numero de transaccion, lote, cantidad, valor unitario 
	 * son invalidos; o si tanto el DPI como el nombre del cliente son null
	 * @throws EmptyArgumentException si la marca, nombre del producto, presentacion, DPI o nombre del cliente estan vacios
	 * @throws DataFormatException si el formato del DPI no es valido
	 */
	public ItemVenta(long numeroTransaccion, String nomProducto, String marcaProducto, 
			String presentacionProducto, long loteProducto, BigDecimal valorUnitario, 
			int cantidad, String dpiCliente, String nombreCliente)
			throws ModelConstraintViolationException, EmptyArgumentException, DataFormatException {
		super(numeroTransaccion, nomProducto, marcaProducto, presentacionProducto, 
				loteProducto, valorUnitario, cantidad);
		if(dpiCliente == null && nombreCliente == null)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.dpi_nom_cliente.null"));
		setDpiCliente(dpiCliente);
		setNombreCliente(nombreCliente);
	}

	/**
	 * Constructor
	 * @param numeroTransaccion numero de la transaccion a la que pertenece el item
	 * @param nomProducto nombre del producto, no debe ser null
	 * @param marcaProducto marca del producto, no debe ser null
	 * @param presentacionProducto presentacion del producto, no debe ser null
	 * @param loteProducto lote del producto
	 * @param valorUnitario valor unitario del producto, no puede ser null
	 * @param cantidad cantidad del producto
	 * @param dpiCliente DPI del cliente, no debe ser null
	 * @throws ModelConstraintViolationException si el numero de transaccion, lote, cantidad, valor unitario 
	 * son invalidos; o si tanto el DPI como el nombre del cliente son null
	 * @throws EmptyArgumentException si la marca, nombre del producto, presentacion o DPI estan vacios
	 * @throws DataFormatException si el formato del DPI no es valido
	 */
	public ItemVenta(long numeroTransaccion, String nomProducto, String marcaProducto, 
			String presentacionProducto, long loteProducto, BigDecimal valorUnitario, 
			int cantidad, String dpiCliente)
			throws ModelConstraintViolationException, EmptyArgumentException, DataFormatException {
		super(numeroTransaccion, nomProducto, marcaProducto, presentacionProducto, 
				loteProducto, valorUnitario, cantidad);
		assert dpiCliente != null : STRINGS_MODELO.getString("error.dpi_cliente.null");
		setDpiCliente(dpiCliente);
		this.nombreCliente = null;
	}
	
	/**
	 * Constructor
	 * @param numeroTransaccion numero de la transaccion a la que pertenece el item
	 * @param nomProducto nombre del producto, no debe ser null
	 * @param marcaProducto marca del producto, no debe ser null
	 * @param presentacionProducto presentacion del producto, no debe ser null
	 * @param loteProducto lote del producto
	 * @param valorUnitario valor unitario del producto, no puede ser null
	 * @param cantidad cantidad del producto
	 * @param nombreCliente nombre del cliente, no debe ser null
	 * @throws ModelConstraintViolationException si el numero de transaccion, lote, cantidad, valor unitario 
	 * son invalidos
	 * @throws EmptyArgumentException si la marca, presentacion, nombre del producto o nombre del cliente estan vacios
	 */
	public ItemVenta(long numeroTransaccion, String nomProducto, String marcaProducto, 
			String presentacionProducto, long loteProducto, String nombreCliente, 
			BigDecimal valorUnitario, int cantidad)
			throws ModelConstraintViolationException, EmptyArgumentException {
		super(numeroTransaccion, nomProducto, marcaProducto, presentacionProducto, 
				loteProducto, valorUnitario, cantidad);
		assert nombreCliente != null : STRINGS_MODELO.getString("error.nombre_cliente.null");
		setNombreCliente(nombreCliente);
		this.dpiCliente = null;
	}
	
	/**
	 * Devuelve el DPI del cliente o null si no se conoce
	 * @return DPI del cliente o null si no se conoce
	 */
	public String getDpiCliente() {return dpiCliente;}

	/**
	 * Establece el DPI del cliente
	 * @param dpiCliente DPI del cliente, si no se conoce debe ser null
	 * @throws EmptyArgumentException si el DPI que se provee esta vacio
	 * @throws DataFormatException si el formato del DPI es invalido
	 */
	private void setDpiCliente(String dpiCliente) 
			throws EmptyArgumentException, DataFormatException {
		if(dpiCliente != null)
			if(dpiCliente.isEmpty())
				throw new EmptyArgumentException(STRINGS_MODELO.getString("error.dpi_cliente.empty"));
			else if(!dpiCliente.matches(Validacion.REGEX_DPI))
				throw new DataFormatException(STRINGS_MODELO.getString("error.dpi_cliente.format"));
		this.dpiCliente = dpiCliente;
	}

	/**
	 * Devuelve el nombre del cliente o null si no se conoce
	 * @return nombre del cliente o null si no se conoce
	 */
	public String getNombreCliente() {return nombreCliente;}

	/**
	 * Establece el nombre del cliente
	 * @param nombreCliente nombre del cliente, debe ser null si no se conoce
	 * @throws EmptyArgumentException si el nombre que se provee esta vacio
	 */
	private void setNombreCliente(String nombreCliente) throws EmptyArgumentException {
		if(nombreCliente != null && nombreCliente.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.nombre_cliente.empty"));
		this.nombreCliente = nombreCliente;
	}
	
}
