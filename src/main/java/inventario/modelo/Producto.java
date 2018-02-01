package inventario.modelo;

import java.io.Serializable;

import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

/**
 * Clase para crear objetos con informacion de un producto.
 * @author juang
 *
 */
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nombre;
	private String marca;
	
	/**
	 * Constructor	
	 * @param id numero de identificacion del producto
	 * @param nombre nombre del producto, no puede ser null
	 * @param marca marca del producto, no puede ser null
	 * @throws ModelConstraintViolationException si el ID es menor o igual a cero
	 * @throws EmptyArgumentException si el nombre o la marca estan vacios
	 */
	public Producto(long id, String nombre, String marca) throws ModelConstraintViolationException, EmptyArgumentException {
		setId(id);
		setNombre(nombre);
		setMarca(marca);
	}
	
	/**
	 * Constructor
	 * @param nombre nombre del producto, no puede ser null
	 * @param marca marca del producto, no puede ser null
	 * @throws EmptyArgumentException si el nombre o la marca estan vacios
	 */
	public Producto(String nombre,String marca) throws EmptyArgumentException {
		setNombre(nombre);
		setMarca(marca);
		id = Long.MAX_VALUE;
	}

	/**
	 * Devuelve el numero de identificacion del producto
	 * @return el numero de identificacion del producto
	 */
	public long getId() {return id;}
	
	/**
	 * Establece el numero que identifica al producto
	 * @param id numero de identificacion del producto, debe ser mayor a cero
	 * @throws ModelConstraintViolationException si el ID es menor o igual a cero
	 */
	public void setId(long id) throws ModelConstraintViolationException {
		if(id <= 0)
			throw new ModelConstraintViolationException("El ID debe ser mayor a cero");
		this.id = id;
	}
	
	/**
	 * Devuelve el nombre del producto
	 * @return el nombre del producto
	 */
	public String getNombre() {return nombre;}
	
	/**
	 * Establece el nombre del producto
	 * @param nombre nombre del producto, no puede ser null
	 * @throws EmptyArgumentException si el nombre esta vacio
	 */
	public void setNombre(String nombre) throws EmptyArgumentException {
		assert nombre != null : "El nombre no puede ser null";
		if(nombre.isEmpty())
			throw new EmptyArgumentException("El nombre no puede estar vacio");
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve la marca del producto
	 * @return marca del producto
	 */
	public String getMarca() {return marca;}
	
	/**
	 * Establece la marca del producto
	 * @param marca marca del producto, no puede ser null
	 * @throws EmptyArgumentException si la marca esta vacia
	 */
	public void setMarca(String marca) throws EmptyArgumentException {
		assert marca != null : "La marca no puede ser null";
		if(marca.isEmpty())
			throw new EmptyArgumentException("La marca no puede estar vacia");
		this.marca = marca;
	}
	
	@Override
	/**
	 * Devuelve una representacion del producto en forma de texto
	 */
	public String toString() {
		return id + " " + nombre + " " + marca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	/**
	 * Compara el producto con otro, devuelve true si el id o tanto el nombre como la marca
	 * de dos productos son iguales
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (id != other.id)
			if(nombre.equalsIgnoreCase(other.nombre) 
					&& marca.equalsIgnoreCase(other.marca))
				return true;
			else
				return false;
		return true;
	}
	
}
