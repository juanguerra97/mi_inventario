package inventario.dao;

import java.util.List;

import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Presentacion;

/**
 * Interfaz para acceder a los datos de las presentaciones
 * @author juang
 *
 */
public interface DAOPresentacion {
	
	/**
	 * Metodo para almacenar una nueva presentacion
	 * @param presentacion presentacion nueva, no debe ser null
	 * @return true si se inserta correctamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si la presentacion no se puede insertar, por ejemplo 
	 * si la llave primaria esta repetida
	 */
	boolean insert(Presentacion presentacion) throws DBConnectionException, 
												CannotInsertException;

	/**
	 * Metodo para actualizar el nombre de una presentacion
	 * @param vieja datos antiguos de la presentacion, no debe ser null
	 * @param newNombre nuevo nombre de la presentacion, no debe ser null
	 * @return true si se actualiza correctamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotUpdateException si la presentacion no se puede actualizar, por ejemplo 
	 * si la llave primaria de la presentacion actualizada se repite
	 */
	boolean update(Presentacion vieja, String newNombre) throws DBConnectionException, 
															CannotUpdateException;
	
	/**
	 * Metodo para eliminar una presentacion
	 * @param presentacion presentacion a eliminar, no puede ser null
	 * @return true si la presentacion se elimina con exito o si la presentacion no existia
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotDeleteException si la presentacion no se puede eliminar, por ejemplo 
	 * si aun hay existencias de un producto con esta presentacion
	 */
	boolean delete(Presentacion presentacion) throws DBConnectionException, 
													CannotDeleteException;
	
	/**
	 * Metodo para obtener todas las presentaciones(diferentes) que hagan match con una expresion regular
	 * @param index posicion desde la que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @param regex expresion regular con la que se deben comparar las presentaciones
	 * @return lista con las presentaciones que hagan match con regex
	 */
	List<Presentacion> getAll(int index, int size, String regex);
	
}
