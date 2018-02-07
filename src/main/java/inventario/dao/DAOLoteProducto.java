package inventario.dao;

import java.util.List;

import inventario.modelo.Lote;

/**
 * DAO de la relacion enter productos y lotes
 * @author juang
 *
 */
public interface DAOLoteProducto {
	
	/**
	 * Metodo para obtener todos los lotes de un producto
	 * @param idProducto ID del producto al que deben pertenecer los lotes
	 * @param index indice desde el que se deben recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con todos los lotes del producto
	 */
	List<Lote> getAll(long idProducto, int index, int size);

}
