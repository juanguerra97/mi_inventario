package inventario.modelo;

import java.io.Serializable;

import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

/**
 * Clase para crear objectos que representen una presentacion de un producto
 * @author juang
 *
 */
public class Presentacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idProducto;
	private String nombre;
	
	/**
	 * Constructor
	 * @param idProducto numero de identificacion del producto al que pertenece la presentacion
	 * @param nombre nombre de la presentacion, no puede ser null
	 * @throws ModelConstraintViolationException si el ID del producto es menor o igual a cero
	 * @throws EmptyArgumentException si el nombre de la presentacion esta vacio
	 */
	public Presentacion(long idProducto, String nombre) throws ModelConstraintViolationException, EmptyArgumentException {
		setIdProducto(idProducto);
		setNombre(nombre);
	}

	/**
	 * Devuelve el id del producto al que pertenece esta presentacion
	 * @return id del producto al que pertenece esta presentacion
	 */
	public long getIdProducto() {return idProducto;}
	
	/**
	 * Establece el id de identificacion del producto al que pertenece la presentacion
	 * @param id numero de identificacion del producto al que pertenece la presentacion
	 * @throws ModelConstraintViolationException si el ID del producto es menor o igual a cero
	 */
	public void setIdProducto(long idProducto) throws ModelConstraintViolationException {
		if(idProducto <= 0)
			throw new ModelConstraintViolationException("El ID del producto debe ser mayor a cero");
		this.idProducto = idProducto;
	}
	
	/**
	 * Devuelve el nombre de la presentacion
	 * @return nombre de la presentacion
	 */
	public String getNombre() {return nombre;}
	
	/**
	 * Establece el nombre de la presentacion
	 * @param nombre nombre de la presentacion, no puede ser null
	 * @throws EmptyArgumentException si el nombre de la presentacion esta vacio
	 */
	public void setNombre(String nombre) throws EmptyArgumentException {
		assert nombre != null : "El nombre de la presentacion no puede ser null";
		if(nombre.isEmpty())
			throw new EmptyArgumentException("El nombre de la presentacion no puede estar vacio");
		this.nombre = nombre;
	}
	
	@Override
	/**
	 * Devuelve representacion en string de la presentacion
	 */
	public String toString() {
		return idProducto + " " + nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProducto ^ (idProducto >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	/**
	 * Compara esta presentacion con otro objecto que tambien debe ser una presentacion.
	 * Dos Presentaciones son iguales si tanto el id del producto como el nombre de la
	 * presentacion son iguales.
	 * Para la comparacion del nombre de la presentacion no se diferencias letras mayusculas
	 * y minusculas
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Presentacion other = (Presentacion) obj;
		if (idProducto != other.idProducto)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equalsIgnoreCase(other.nombre))
			return false;
		return true;
	}

}
