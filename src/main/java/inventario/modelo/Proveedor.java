package inventario.modelo;

import java.io.Serializable;

import inventario.modelo.error.EmptyArgumentException;

/**
 * Clase para crear objetos que representen a un proveedor
 * @author juang
 *
 */
public class Proveedor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	
	/**
	 * Constructor
	 * @param nombre nombre del proveedor, no puede ser null ni estar vacio
	 * @throws EmptyArgumentException si el nombre esta vacio
	 */
	public Proveedor(String nombre) throws EmptyArgumentException {
		setNombre(nombre);
	}

	/**
	 * Devuelve el nombre del proveedor
	 * @return nombre del proveedor
	 */
	public String getNombre() {return nombre;}

	/**
	 * Establece el nombre del proveedor	
	 * @param nombre nombre del proveedor, no puede ser null ni estar vacio
	 * @throws EmptyArgumentException si el nombre esta vacio
	 */
	public void setNombre(String nombre) throws EmptyArgumentException {
		assert nombre != null : "El nombre del proveedor no puede ser null";
		if(nombre.isEmpty())
			throw new EmptyArgumentException("El nombre del proveedor no puede quedar vacio");
		this.nombre = nombre;
	}
	
	@Override
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
	 * Compara el proveedor con otro objeto que tambien debe ser un proveedor.
	 * Dos proveedores son iguales si tienen el mismo nombre.
	 * En cuanto al nombre no se distingue entre mayusculas y minusculas
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proveedor other = (Proveedor) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equalsIgnoreCase(other.nombre))
			return false;
		return true;
	}
	
	

}
