package inventario.dao;

import java.util.List;
import java.util.Optional;
import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Cliente;

/**
 * Interfaz de acceso a los datos de los clientes
 * @author juang
 *
 */
public interface DAOCliente {
	
	/**
	 * Metodo para obtener los datos de un cliente con cierto DPI
	 * @param dpi DPI del cliente, no debe ser null
	 * @return objeto Optional con los datos del cliente(si existe)
	 */
	Optional<Cliente> get(String dpi);
	
	/**
	 * Metodo para obtener los datos de todos los clientes
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con los datos de los clientes
	 */
	List<Cliente> getAll(int index, int size);	
	
	/**
	 * Metodo paara obtener los datos de todos los clientes activos
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con los datos de los clientes activos
	 */
	List<Cliente> getAllActive(int index, int size);
	
	/**
	 * Metodo paara obtener los datos de todos los clientes que han sido dados de baja
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con los datos de los clientes dados de baja
	 */
	List<Cliente> getAllNotActive(int index, int size);
	
	/**
	 * Metodo para registrar un nuevo cliente
	 * @param nuevo datos del nuevo cliente, no debe ser null
	 * @return true si el registro se realiza exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede realizar la insercion, por ejemplo 
	 * si se estar repitiendo la llave primaria
	 */
	boolean insert(Cliente nuevo) throws DBConnectionException, CannotInsertException;
	
	/**
	 * Metodo para eliminar un cliente
	 * @param cliente cliente a eliminar
	 * @return true si la operacion se realiza correctamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean delete(Cliente cliente) throws DBConnectionException;
	
	/**
	 * Metodo para actualizar los datos de un cliente(excepto el DPI)
	 * @param viejo datos actuales del cliente, no debe ser null
	 * @param nuevo nuevos datos del cliente, no debe ser null
	 * @return true si la operacion se realiza exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean update(Cliente viejo, Cliente nuevo) throws DBConnectionException;
	
	/**
	 * Metodo para dar de baja a un cliente
	 * @param dpiCliente DPI del cliente a dar de baja, no debe ser null
	 * @return true si la operacion se realiza con exito
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean darDeBaja(String dpiCliente) throws DBConnectionException;
	
	/**
	 * Metodo para dar de alta a un cliente
	 * @param dpiCliente DPI del cliente cliente a dar de alta
	 * @return true si la operacion se realiza exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean darDeAlta(String dpiCliente) throws DBConnectionException;

}
