package inventario.dao;

import java.util.List;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Proveedor;

/**
 * Interfaz de acceso a los datos de proveedores
 * @author juang
 *
 */
public interface DAOProveedor {

	/**
	 * Metodo para obtener todos los proveedores
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con los proveedores
	 */
	List<Proveedor> getAll(int index, int size);
	
	/**
	 * Metodo para registrar un nuevo proveedor
	 * @param proveedor datos del nuevo proveedor, no debe ser null
	 * @return true si la operacion se realiza con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede insertar el nuevo registro, por ejemplo 
	 * si se estar repitiendo la llave primaria
	 */
	boolean insert(Proveedor proveedor) throws DBConnectionException, CannotInsertException;
	
	/**
	 * Metodo para eliminar a un proveedor
	 * @param proveedor datos del proveedor a eliminar, no debe ser null
	 * @return true si se elimina con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean delete(Proveedor proveedor) throws DBConnectionException;
	
	/**
	 * Metodo para actualizar los datos de un proveedor
	 * @param viejo datos actuales del proveedor, no debe ser null
	 * @param nuevo nuevos datos del proveedor, no debe ser null
	 * @return true si se elimina exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotUpdateException
	 */
	boolean update(Proveedor viejo, Proveedor nuevo) throws DBConnectionException, CannotUpdateException;
	
}
