package inventario.dao;

import java.util.List;

import inventario.modelo.Presentacion;

/**
 * DAO de la relacion entre productos y presentaciones
 * @author juang
 *
 */
public interface DAOPresentacionProducto {

	/**
	 * Metodo para obtener todas las presentaciones de un producto
	 * @param idProducto ID del producto al que deben pertenecer las presentaciones
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con las presentaciones del producto
	 */
	List<Presentacion> getAll(long idProducto, int index, int size);
	
}
