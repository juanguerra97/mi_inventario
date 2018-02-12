package inventario.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import inventario.dao.error.CannotInsertException;
import inventario.dao.error.DBConnectionException;
import inventario.modelo.Transaccion;

/**
 * Interfaz de acceso a los datos de las transacciones
 * @author juang
 *
 */
public interface DAOTransaccion {
	
	/**
	 * Metodo para obtener los datos de una transaccion
	 * @param tipo tipo de la transaccion, no debe ser null
	 * @param numero numero de la transaccion
	 * @return objeto Optinal con los datos de la transaccion(si existe)
	 */
	Optional<Transaccion> get(TipoTransaccion tipo, long numero);
	
	/**
	 * Metodo que devuelve todas las transacciones de cierto tipo
	 * @param tipo tipo de la transaccion, no debe ser null
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con las transacciones del tipo especificado
	 */
	List<Transaccion> getAll(TipoTransaccion tipo, int index, int size);

	/**
	 * Metodo para obtener las transacciones de cierto tipo de cierta fecha
	 * @param tipo tipo de la transaccion, no debe ser null
	 * @param fecha fecha de las transacciones, no debe ser null
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con todas las transacciones de cierta fecha
	 */
	List<Transaccion> getAll(TipoTransaccion tipo, LocalDate fecha, int index, int size);
	
	/**
	 * Metodo para registrar una transaccion de cierto tipo
	 * @param tipo tipo de la transaccion, no debe ser null
	 * @param transaccion datos de la transaccion, no debe ser null
	 * @return true si la operacion se realiza exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 * @throws CannotInsertException si no se puede realizar la insercion, por ejemplo 
	 * si se esta repitiendo la llave primaria
	 */
	boolean insert(TipoTransaccion tipo, Transaccion transaccion) throws DBConnectionException, 
																			CannotInsertException;
	/**
	 * Metodo para registrar una transaccion de cierto tipo
	 * @param tipo tipo de la transaccion, no debe ser null
	 * @param transaccion datos de la transaccion, no debe ser null
	 * @return el numero de transaccion generado
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	long insertAndGet(TipoTransaccion tipo, Transaccion transaccion) throws DBConnectionException;
	
	/**
	 * Metodo para eliminar una transaccion
	 * @param tipo tipo de la transaccion, no debe ser null
	 * @param numero numero de la transaccion a eliminar
	 * @return true si la operacion se realiza exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean delete(TipoTransaccion tipo, long numero) throws DBConnectionException;
	
	/**
	 * Metodo para actualizar la fecha y valor total de una transaccion
	 * @param tipo tipo de la transaccion, no debe ser null
	 * @param transaccion objeto con los nuevos datos de la transaccion
	 * @return true si la actualizacion se realiza exitosamente
	 * @throws DBConnectionException si ocurre un error de conexion con la base de datos
	 */
	boolean update(TipoTransaccion tipo, Transaccion transaccion) throws DBConnectionException;
	
}
