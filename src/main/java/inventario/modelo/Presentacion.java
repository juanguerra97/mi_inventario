package inventario.modelo;

import java.io.Serializable;

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
	 * @param idProducto
	 * @param nombre
	 */
	public Presentacion(long idProducto, String nombre) {
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
	 * @param id numero de identificacion del producto, debe ser mayor a cero
	 */
	public void setIdProducto(long idProducto) {
		assert idProducto > 0 : "El ID del producto debe ser mayor a cero";
		this.idProducto = idProducto;
	}
	
	/**
	 * Devuelve el nombre de la presentacion
	 * @return nombre de la presentacion
	 */
	public String getNombre() {return nombre;}
	
	/**
	 * Establece el nombre de la presentacion
	 * @param nombre nombre de la presentacion, no puede ser null o estar vacio
	 */
	public void setNombre(String nombre) {
		assert nombre != null : "El nombre de la presentacion no puede ser null";
		assert !nombre.isEmpty() : "El nombre de la presentacion no puede estar vacio";
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
