package inventario.dao;

import java.util.List;

import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Producto;
import inventario.modelo.Proveedor;

/**
 * Interfaz para acceder a los datos de la relacion entre productos y proveedores
 * @author juang
 *
 */
public interface DAOProveedorProducto {
	
	/**
	 * Metodo que devuelve una lista con todos los productos que provee un proveedor
	 * @param proveedor proveedor que provee los productos, no debe ser null
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con los productos proveidos por el proveedor
	 */
	List<Producto> getAll(Proveedor proveedor, int index, int size);
	
	/**
	 * Metodo para obtener todos los proveedores de un producto
	 * @param idProducto ID del producto que deben proveer los proveedores
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con los proveedores del producto con ID idProducto
	 */
	List<Proveedor> getAll(long idProducto, int index, int sizze);
	
	/**
	 * Metodo para ingresar una nueva relacion entre un proveedor y un producto
	 * @param idProducto ID del producto
	 * @param proveedor datos del proveedor, no debe ser null
	 * @return true si la operacion se realiza exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede insertar el registro, por ejemplo 
	 * si se esta repitiendo la llave primaria
	 */
	boolean insert(long idProducto, Proveedor proveedor) throws DBConnectionException, 
																CannotInsertException;
	
	/**
	 * Metodo para eliminar un relacion de un producto y un proveedor
	 * @param idProducto ID del producto
	 * @param proveedor datos del proveedor, no debe ser null
	 * @return true si la operacion se realiza exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean delete(long idProducto, Proveedor proveedor) throws DBConnectionException;

}
