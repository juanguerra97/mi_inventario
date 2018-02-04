package inventario.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import static inventario.modelo.Resources.STRINGS_MODELO;
import inventario.modelo.error.ModelConstraintViolationException;

/**
 * Clase para guardar informacion sobre una transaccion(compra o venta)
 * @author juang
 *
 */
public class Transaccion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long numero;// numero de la transaccion
	private LocalDate fecha;// fecha en que se realizo la transaccion
	private BigDecimal valor;// valor total de la transaccion
	
	/**
	 * Constructor
	 * @param numero numero de la transaccion
	 * @param fecha fecha de la transaccion, no debe ser null
	 * @param valor valor de la transaccion, no debe ser null
	 * @throws ModelConstraintViolationException si el numero o es cero o negativo, o si el valor es negativo
	 */
	public Transaccion(long numero, LocalDate fecha, BigDecimal valor) 
			throws ModelConstraintViolationException {
		setNumero(numero);
		setFecha(fecha);
		setValor(valor);
	}
	
	/**
	 * Constructor
	 * @param numero numero de la transaccion
	 * @param valor valor de la transaccion, no debe ser null
	 * @throws ModelConstraintViolationException si el numero o es cero o negativo, o si el valor es negativo
	 */
	public Transaccion(long numero, BigDecimal valor) 
			throws ModelConstraintViolationException {
		this(numero, LocalDate.now(), valor);// establece la fecha actual del sistema
	}

	/**
	 * Devuelve el numero de la transaccion
	 * @return numero de la transaccion
	 */
	public long getNumero() {return numero;}
	
	/**
	 * Establece el numero de la transaccion
	 * @param numero numero de la transaccion
	 * @throws ModelConstraintViolationException si el numero es negativo o cero
	 */
	public void setNumero(long numero) throws ModelConstraintViolationException {
		if(numero <= 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.num_transac"));
		this.numero = numero;
	}
	
	/**
	 * Devuelve la fecha en que se realizo la transaccion
	 * @return fecha en que se realizo la transaccion
	 */
	public LocalDate getFecha() {return fecha;}
	
	/**
	 * Establece la fecha de la transaccion
	 * @param fecha fecha de la transaccion, no debe ser null
	 */
	public void setFecha(LocalDate fecha) {
		assert fecha != null : STRINGS_MODELO.getString("error.date.null");
		this.fecha = fecha;
	}
	
	/**
	 * Devuelve el valor de la transaccion
	 * @return valor de la transaccion
	 */
	public BigDecimal getValor() {return valor;}
	
	/**
	 * Establece el valor de la transaccion
	 * @param valor valor de la transaccion, no debe ser null
	 * @throws ModelConstraintViolationException si el valor es negativo
	 */
	public void setValor(BigDecimal valor) throws ModelConstraintViolationException {
		assert valor != null : STRINGS_MODELO.getString("error.val_transac.null");
		if(valor.compareTo(new BigDecimal("0.00")) < 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.val_trasac.neg"));
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "#" + numero + " Fecha: " + fecha + " " + valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (numero ^ (numero >>> 32));
		return result;
	}

	@Override
	/**
	 * Compara dos transacciones.
	 * Devuelve true si ambas transacciones tienen el mismo numero.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaccion other = (Transaccion) obj;
		if (numero != other.numero)
			return false;
		return true;
	}
	
}
