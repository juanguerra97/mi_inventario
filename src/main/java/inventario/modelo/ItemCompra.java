package inventario.modelo;

import java.math.BigDecimal;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;
import static inventario.modelo.Resources.STRINGS_MODELO;

/**
 * Clase para representar los datos de un elemento de una compra
 * @author juang 
 * 
 */
public class ItemCompra extends ItemTransaccion {

	private static final long serialVersionUID = 1L;
	
	private String proveedor;

	/**
	 * Constructor
	 * @param numeroTransaccion numero de la transaccion a la que pertenece el item
	 * @param idProducto id del producto
	 * @param marcaProducto marca del producto, no debe ser null
	 * @param presentacionProducto presentacion del producto, no debe ser null
	 * @param loteProducto lote del producto
	 * @param valorUnitario valor unitario del producto, no puede ser null
	 * @param cantidad cantidad del producto
	 * @param proveedor nombre del proveedor del producto, no debe ser null
	 * @throws ModelConstraintViolationException si el numero de transaccion, id del producto, lote, cantidad o valor unitario 
	 * son invalidos
	 * @throws EmptyArgumentException si la marca, presentacion o el proveedor estan vacios
	 */
	public ItemCompra(long numeroTransaccion, long idProducto, String marcaProducto, 
			String presentacionProducto, long loteProducto, BigDecimal valorUnitario, 
			int cantidad, String proveedor)
			throws ModelConstraintViolationException, EmptyArgumentException {
		super(numeroTransaccion, idProducto, marcaProducto, presentacionProducto, 
				loteProducto, valorUnitario, cantidad);
		setProveedor(proveedor);
	}

	/**
	 * Devuelve el nombre del proveedor del producto
	 * @return
	 */
	public String getProveedor() {return proveedor;}

	/**
	 * Establece el nombre del proveedor del producto
	 * @param proveedor nombre del proveedor del producto, no puede ser null
	 * @throws EmptyArgumentException si el nombre del proveedor esta vacio
	 */
	public void setProveedor(String proveedor) throws EmptyArgumentException {
		assert proveedor != null : STRINGS_MODELO.getString("error.nom_prov.null");
		if(proveedor.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.nom_prov.empty"));
		this.proveedor = proveedor;
	}
	
}
