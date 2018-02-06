package inventario.dao;

import java.util.List;

/**
 * Interfaz DAO para las marcas de producto
 * @author juang
 *
 */
public interface DAOMarca {
	
	/**
	 * Metodo para obtener todas las marcas
	 * @param index posicion desde la que se quieren recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @return lista con todas las marcas, si no hay marcas o si index o size son 
	 * invalidos se devolvera una lista vacia
	 */
	List<String> getAll(int index, int size);
	
	/**
	 * Metodo para obtener todas las marcas que hagan match con una expresion regular
	 * @param index posicion desde la que se quieren recuperar los registros
	 * @param size numero maximo de registros a devolver
	 * @param regex expresion regular con la que se deben comparar las marcas
	 * @return lista con todas las marcas que hagan match con regex, si no hay marcas 
	 * o si index o size son invalidos se devolvera una lista vacia
	 */
	List<String> getAll(int index, int size, String regex);

}
