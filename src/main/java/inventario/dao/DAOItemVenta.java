package inventario.dao;

import java.util.List;

import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.ItemVenta;
import inventario.modelo.Transaccion;

/**
 * Interfaz de acceso a los datos de los elementos de las ventas
 * @author juang
 *
 */
public interface DAOItemVenta {
	
	/**
	 * Metodo para obtener los items de una venta
	 * @param venta venta a la que deben pertenecer los items, no debe ser null
	 * @return lista con los items de la venta
	 */
	List<ItemVenta> getAll(Transaccion venta);
	
	/**
	 * Metodo para ingresar un nuevo elemento a una venta
	 * @param item elemento a ingresar, no debe ser null
	 * @return true si se realiza la insercion
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede realizar la operacion, por ejemplo 
	 * si se esta repitiendo la llave primaria
	 */
	boolean insert(ItemVenta item) throws DBConnectionException, CannotInsertException;
	
	/**
	 * Metodo para eliminar un elemento de una venta
	 * @param item elemento a eliminar, no puede ser null
	 * @return true si se realiza la eliminacion
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean delete(ItemVenta item) throws DBConnectionException;
	
	/**
	 * Metodo para actualizar los datos de un elemento de una venta, el unico atributo que no se puede cambiar es
	 * el numero de transaccion e ID del producto
	 * @param actual datos del item, no debe ser null
	 * @param nuevo nuevos datos el item, no debe ser null
	 * @return true si se realiza la actualizacion
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotUpdateException si no se puede realizar la operacion, por ejemplo si se esta repitiendo
	 * la llave primaria
	 */
	boolean update(ItemVenta actual, ItemVenta nuevo) throws DBConnectionException, CannotUpdateException;


}
