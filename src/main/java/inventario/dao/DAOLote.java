package inventario.dao;

import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Lote;

/**
 * DAO de acceso a los datos de los lotes
 * @author juang
 *
 */
public interface DAOLote {
	
	/**
	 * Metodo para insertar un nuevo lote
	 * @param lote lote nuevo, no debe ser null
	 * @return true si se inserta correctamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede insertar el lote, por ejemplo si se 
	 * esta repitiendo la llave primaria
	 */
	boolean insert(Lote lote) throws DBConnectionException, CannotInsertException;
	
	/**
	 * Metodo para actualizar numero y fecha de vencimiento de un lote
	 * @param viejo datos viejos del lote, no debe ser null
	 * @param nuevo datos nuevos del lote, no debe ser null
	 * @return true si se realiza la actualizacion
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean update(Lote viejo, Lote nuevo) throws DBConnectionException; 

	/**
	 * Metodo para eliminar un lote
	 * @param lote lote a eliminar, no debe ser null
	 * @return true si se realiza la eliminacion
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotDeleteException si el lote no se puede eliminar, por ejemplo si aun 
	 * hay existencias de un producto para este lote
	 */
	boolean delete(Lote lote) throws DBConnectionException, CannotDeleteException;
	
}
