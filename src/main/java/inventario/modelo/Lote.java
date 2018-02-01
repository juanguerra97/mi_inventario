package inventario.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import inventario.modelo.error.ModelConstraintViolationException;

/**
 * Clase para crear los lotes que pertenecen a un producto.
 * @author juang
 *
 */
public class Lote implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idProducto;
	private long numero;
	private LocalDate fechaVencimiento;
	
	/**
	 * Constructor
	 * @param idProducto numero de identificacion del producto al que pertenece el lote
	 * @param numero numero de lote
	 * @param fechaVencimiento fecha de vencimiento de los productos en este lote, si no hay fecha vencimiento se debe pasar null como argumento
	 * @throws ModelConstraintViolationException si el ID del producto o el numero de lote son menor o igual a cero
	 */
	public Lote(long idProducto, long numero, LocalDate fechaVencimiento) throws ModelConstraintViolationException {
		setIdProducto(idProducto);
		setNumero(numero);
		setFechaVencimiento(fechaVencimiento);
	}
	
	/**
	 * Constructor
	 * @param idProducto numero de identificacion del producto al que pertenece el lote
	 * @param numero numero de lote
	 * @throws ModelConstraintViolationException si el ID del producto o el numero de lote son menor o igual a cero
	 */
	public Lote(long idProducto, long numero) throws ModelConstraintViolationException {
		this(idProducto, numero, null);// llamada al constructor que recibe todos los atributos con la fecha = null
	}

	/**
	 * Devuelve el id del producto al que pertenece este lote
	 * @return id id del producto al que pertenece este lote
	 */
	public long getIdProducto() {return idProducto;}
	
	/**
	 * Establece el id de identificacion del producto al que pertenece el lote
	 * @param id numero de identificacion del producto al que pertenece el lote
	 * @throws ModelConstraintViolationException si el ID es menor o igual a cero
	 */
	public void setIdProducto(long idProducto) throws ModelConstraintViolationException {
		if(idProducto <= 0)
			throw new ModelConstraintViolationException("El ID del producto debe ser mayor a cero");
		this.idProducto = idProducto;
	}
	
	/**
	 * Devuelve el numero de lote
	 * @return numero numero de lote
	 */
	public long getNumero() {return numero;}
	
	/**
	 * Establece el numero del lote
	 * @param numero numero del lote
	 * @throws ModelConstraintViolationException si el numero de lote es menor o igual a cero
	 */
	public void setNumero(long numero) throws ModelConstraintViolationException {
		if(numero <= 0)
			throw new ModelConstraintViolationException("El numero de lote debe ser mayor a cero");
		this.numero = numero;
	}
	
	/**
	 * Devuelve la fecha de vencimiento de los productos en este lote
	 * @return fecha de vecimiento de los productos o null si no hay una fecha de vecimiento
	 */
	public LocalDate getFechaVencimiento() {return fechaVencimiento;}
	
	/**
	 * Establece la fecha de vencimiento de los productos en este lote
	 * @param fechaVencimiento fecha de vencimiento de los productos en este lote, si no hay fecha vencimiento se debe pasar null como argumento
	 */
	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	@Override
	public String toString() {
		return idProducto + " #" + numero + " " + fechaVencimiento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProducto ^ (idProducto >>> 32));
		result = prime * result + (int) (numero ^ (numero >>> 32));
		return result;
	}

	@Override
	/**
	 * Compara el lote con el objeto obj que tambien debe ser un objeto lote.
	 * Dos lotes se consideran iguales si tanto el id del producto como el numero
	 * de lote son iguales.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lote other = (Lote) obj;
		if (idProducto != other.idProducto)
			return false;
		if (numero != other.numero)
			return false;
		return true;
	}

}
