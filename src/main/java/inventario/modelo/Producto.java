package inventario.modelo;

import java.io.Serializable;

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
	 * @param id numero de identificacion del producto, debe ser mayor a cero
	 * @param nombre nombre del producto, no puede ser null o estar vacio
	 * @param marca marca del producto, no puede ser null o estar vacia
	 */
	public Producto(long id, String nombre, String marca) {
		setId(id);
		setNombre(nombre);
		setMarca(marca);
	}
	
	/**
	 * Constructor
	 * @param nombre nombre del producto, no puede ser null o estar vacio
	 * @param marca marca del producto, no puede ser null o estar vacia
	 */
	public Producto(String nombre,String marca) {
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
	 */
	public void setId(long id) {
		assert id > 0 : "El ID debe ser mayor a cero";
		this.id = id;
	}
	
	/**
	 * Devuelve el nombre del producto
	 * @return el nombre del producto
	 */
	public String getNombre() {return nombre;}
	
	/**
	 * Establece el nombre del producto
	 * @param nombre nombre del producto, no puede ser null o estar vacio
	 */
	public void setNombre(String nombre) {
		assert nombre != null : "El nombre no puede ser null";
		assert !nombre.isEmpty() : "El nombre no puede estar vacio";
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve la marca del producto
	 * @return marca del producto
	 */
	public String getMarca() {return marca;}
	
	/**
	 * Establece la marca del producto
	 * @param marca marca del producto, no puede ser null o estar vacia
	 */
	public void setMarca(String marca) {
		assert marca != null : "La marca no puede ser null";
		assert !marca.isEmpty() : "La marca no puede estar vacia";
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
