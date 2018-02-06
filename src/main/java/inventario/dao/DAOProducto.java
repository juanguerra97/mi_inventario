package inventario.dao;

import java.util.List;
import java.util.Optional;

import inventario.dao.error.CannotDeleteException;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.CannotUpdateException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Producto;

/**
 * Interfaz para acceder a los datos de productos
 * @author juang
 *
 */
public interface DAOProducto {
	
	/**
	 * Metodo que devuelve los datos de un producto con cierto ID
	 * @param id ID del producto que se quiere recuperar
	 * @return objeto Optional con el producto, si el producto no existe o el ID
	 * proporcionado es invalido ( <= 0) el objeto Optional contendra null
	 */
	Optional<Producto> get(long id);
	
	/**
	 * Metodo que devuelve los datos de un producto con cierto nombre y cierta marca
	 * @param nombre nombre del producto, no debe ser null
	 * @param marca marca del producto, no debe ser null
	 * @return objeto Optional con el producto, si el producto no existe o si el nombre
	 *  o la marca son invalidos(vacios) el objeto optional contendra null
	 */
	Optional<Producto> get(String nombre, String marca);
	
	/**
	 * Metodo para obtener todos los productos
	 * @param start indice desde el que se deben recuperar los registros
	 * @param size cantidad de producto maxima a devolver
	 * @return lista con productos, la lista estara vacia si no hay producto o si 
	 * start o size son invalidos
	 */
	List<Producto> getAll(int start, int size);
	
	/**
	 * Metodo para obtener los productos con cierta marca
	 * @param marca marca a la que deben pertenecer los productos, 
	 * no debe ser null
	 * @param start indice desde el cual se deben recuperar los registros
	 * @param size numero maximo de productos que puede contener la lista
	 * @return lista con los productos de la marca, si no hay productos con esa marca 
	 * o la marca, el indice start o size son invalidos, la lista devuelta estara vacia
	 */
	List<Producto> getAll(String marca, int start, int size);
	
	/**
	 * Metodo que permite recuperar todos los productos cuyo nombre hace match con 
	 * una expresion regular
	 * @param start indice desde el cual se deben recuperar los registros
	 * @param size numero maximo de productos que puede contener la lista
	 * @param regex expresion regular con la cual se compararan los nombres, no debe ser null
	 * @return lista con productos cuyo nombre concuerda con la expresion regular, la lista 
	 * estara vacia si no hay productos que hagan match o si start, size o regex son argumentos  
	 * invalidos
	 */
	List<Producto> getAll(int start, int size, String regex);
	
	/**
	 * Metodo para insertar un nuevo producto a la base de datos
	 * @param producto objeto con los datos del nuevo producto, no debe ser null
	 * @return true si la insercion se realiza con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede insertar el producto, por ejemplo si la 
	 * llave primaria esta duplicada
	 */
	boolean insert(Producto producto) throws DBConnectionException, CannotInsertException;
	
	/**
	 * Metodo para insertar un nuevo producto a la base de datos
	 * @param producto objeto con los datos del nuevo producto, el ID no se tomara en cuenta 
	 * y el objeto no debe ser null
	 * @return devuelve el ID generado del producto si se tuvo exito, -1 si ocurrio un error
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede insertar el producto, por ejemplo si la 
	 * llave primaria esta duplicada
	 */
	long insertAndGet(Producto producto) throws DBConnectionException, CannotInsertException;
	
	/**
	 * Metodo que elimina todos los productos que tengan el ID que se pase como argumento
	 * @param idProducto ID del producto a eliminar
	 * @return true si el producto se elimino con exito o no existia, false si ocurre 
	 * un error
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotDeleteException si no se puede eliminar el producto, por ejemplo si 
	 * aun hay existencias del producto
	 */
	boolean delete(long idProducto) throws DBConnectionException, CannotDeleteException;
	
	/**
	 * Metodo para actualizar todos los datos de un producto
	 * @param viejo datos viejos del producto, no debe ser null
	 * @param nuevo datos nuevos, no debe ser null
	 * @return true si se actualiza con exito o el si producto viejo no existia,
	 *  false si ocurre un error
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotUpdateException si no se puede realizar la actualizacion, por ejemplo 
	 * si en los nuevos datos se esta repitiendo una llave primaria
	 */
	boolean update(Producto viejo, Producto nuevo) throws DBConnectionException, CannotUpdateException;
	
	/**
	 * Metodo para actualizar el nombre y marca de un producto
	 * @param idProducto ID del producto a actualizar
	 * @param nuevo objeto con el nuevo nombre y nueva marca
	 * @return true si se actualiza con exito o el si producto viejo no existia,
	 *  false si ocurre un error
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotUpdateException si no se puede realizar la actualizacion, por ejemplo 
	 * si tanto el nuevo nombre como marca ya estan registrados
	 */
	boolean update(long idProducto, Producto nuevo) throws DBConnectionException, CannotUpdateException;
	
}
