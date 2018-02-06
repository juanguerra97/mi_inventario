package inventario.dao;

import java.util.List;

import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Categoria;

/**
 * Interfaz DAO para los datos de las categorias
 * @author juang
 *
 */
public interface DAOCategoria {

	/**
	 * Metodo para obtener todas las categorias
	 * @param index posicion desde la que se deben obtener los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con las categorias, la lista estara vacia si index o size 
	 * son invalidos, o si no hay categorias
	 */
	List<Categoria> getAll(int index, int size);
	
	/**
	 * Metodo para obtener todas las categorias cuyo nombre haga match con una expresion regular
	 * @param index posicion desde la que se deben obtener los registros
	 * @param size numero maximo de registros a devolver
	 * @param regex expresion regular con la que se deben comparar los registros, no debe ser null
	 * @return lista con todas las categorias que hagan match con regex, 
	 * la lista estara vacia si index o size son invalidos, o si no hay categorias
	 */
	List<Categoria> getAll(int index, int size, String regex);
	
	/**
	 * Metodo para insertar una nueva categoria
	 * @param nueva datos de la nueva categoria
	 * @return true si se inserta con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede insertar la categoria, por ejemplo, 
	 * si la llave primaria esta repetida
	 */
	public boolean insert(Categoria nueva) throws DBConnectionException, 
										CannotInsertException;
	
	/**
	 * Metodo para eliminar una categoria
	 * @param categoria categoria a eliminar, no debe ser null
	 * @return true si la eliminacion se realiza con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotDeleteException si la categoria no se puede eliminar, por ejemplo, 
	 * si aun hay productos en una categoria
	 */
	boolean delete(Categoria categoria) throws DBConnectionException, 
										CannotDeleteException;
	
	/**
	 * Metodo para actualizar los datos de una categoria
	 * @param vieja datos viejos de la categoria, no debe ser null
	 * @param nueva datos nuevos de la categoria, no debe ser null
	 * @return true si la actualizacion se realiza con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotUpdateException si no se puede realizar la actualizacion, 
	 * por ejemplo, si la llave primaria de los nuevos datos ya la tiene otra categoria
	 */
	boolean update(Categoria vieja, Categoria nueva) throws DBConnectionException,
										CannotUpdateException;
	
}
