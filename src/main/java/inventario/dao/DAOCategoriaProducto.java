package inventario.dao;

import java.util.List;

import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Categoria;
import inventario.modelo.Producto;

public interface DAOCategoriaProducto {

	/**
	 * Metodo para obtener todos los productos de una categoria
	 * @param categoria categoria a la que deben pertenecer los productos, no debe ser null
	 * @param index posicion desde la que se deben obtener los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con todos los productos de la categoria
	 */
	List<Producto> getAll(String categoria, int index, int size);
	
	/**
	 * Metodo para obtener todas las categorias de un producto
	 * @param idProducto ID del producto que debe estar asociado a las categorias
	 * @param index posicion desde la que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con las categorias del producto
	 */
	List<Categoria> getAll(long idProducto, int index, int size);
	
	/**
	 * Metodo para insertar una nueva relacion de producto y categoria
	 * @param idProducto ID del producto
	 * @param nomCategoria nombre de la categoria
	 * @return true si la insercion se realiza con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede insertar la relacion, por ejemplo 
	 * si ya existe la relacion
	 */
	boolean insert(long idProducto, String nomCategoria) throws DBConnectionException, 
													CannotInsertException;
	
	/**
	 * Metodo para eliminar una relacion de producto y categoria
	 * @param idProducto ID del producto
	 * @param nomCategoria nombre de la categoria
	 * @return true si la relacion se elimina exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean delete(long idProducto, String nomCategoria) throws DBConnectionException;
	
}
