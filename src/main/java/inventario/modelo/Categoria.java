package inventario.modelo;

import java.io.Serializable;
import static inventario.modelo.Resources.STRINGS_MODELO;
import inventario.modelo.error.EmptyArgumentException;

/**
 * Clase para crear categorias de productos
 * @author juang
 *
 */
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	
	/**
	 * Constructor
	 * @param nombre nombre de la categoria, no debe ser null
	 * @throws EmptyArgumentException si el nombre esta vacio
	 */
	public Categoria(String nombre) throws EmptyArgumentException {
		setNombre(nombre);
	}

	/**
	 * Devuelve el nombre de la categoria
	 * @return nombre de la categoria
	 */
	public String getNombre() {return nombre;}

	/**
	 * Establece el nombre de la categoria
	 * @param nombre nombre de la categoria, no debe ser null
	 * @throws EmptyArgumentException si el nombre esta vacio
	 */
	public void setNombre(String nombre) throws EmptyArgumentException {
		assert nombre != null : STRINGS_MODELO.getString("error.nombre_categoria.null");
		if(nombre.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.nombre_categoria.empty"));
		this.nombre = nombre;
	}
	
	@Override
	/**
	 * Devuelve representacion en forma de string de la categoria
	 */
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	/**
	 * Compara la categoria con otro objeto que tambien debe ser una categoria, dos
	 * categorias se consideran iguales si tienen el mismo nombre sin hacer diferencia
	 * entre mayusculas y minusculas
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equalsIgnoreCase(other.nombre))
			return false;
		return true;
	}

}
