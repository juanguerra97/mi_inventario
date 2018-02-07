package inventario.dao;

import java.util.List;

import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.PresentacionLote;

/**
 * DAO de la relacion entre un producto y sus presentaciones y lotes
 * @author juang
 *
 */
public interface DAOPresentacionLote {
	
	/**
	 * Metodo para obtener todas las relaciones de presentaciones y lotes de un producto
	 * @param idProducto ID del producto al que deben pertenecer los registros
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con todas las relaciones de lotes y presentaciones del producto con ID idProducto
	 */
	List<PresentacionLote> getAll(long idProducto, int index, int size);
	
	/**
	 * Metodo para registrar una nueva relacion de lote y presentacion
	 * @param nuevo nueva relacion a registrar, no debe ser null
	 * @return true si se inserta con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede insertar el registro, por ejemplo 
	 * si se esta repitiendo la llave primaria
	 */
	boolean insert(PresentacionLote nuevo) throws DBConnectionException, CannotInsertException;
	
	/**
	 * Metodo para actualizar la cantidad, precio y costo de un objeto PresentacionLote
	 * @param viejo datos viejos, no debe ser null
	 * @param nuevo datos nuevos, no debe ser null
	 * @return true si se realiza la actualizacion
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean update(PresentacionLote viejo, PresentacionLote nuevo) throws DBConnectionException;

	/**
	 * Metodo para eliminar un objeto PresentacionLote
	 * @param registro registro a eliminar, no puede ser null
	 * @return true si se realiza la eliminacion
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotDeleteException si no se puede eliminar el registro, por ejemplo si aun 
	 * hay existencias
	 */
	boolean delete(PresentacionLote registro) throws DBConnectionException, CannotDeleteException;
	
}
