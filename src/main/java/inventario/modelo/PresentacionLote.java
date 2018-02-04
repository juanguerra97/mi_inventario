package inventario.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import static inventario.modelo.Resources.STRINGS_MODELO;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

/**
 * Clase para relacionar las presentaciones y los lotes de un producto.
 * La relacion es de varios a varios entre lotes y presentaciones.
 * @author juang
 *
 */
public class PresentacionLote implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idProducto;
	private String nombrePresentacion;
	private long numeroLote;
	private int cantidad;
	private BigDecimal costo = null;
	private BigDecimal precio = null;
	
	/**
	 * Constructor
	 * @param idProducto id del producto
	 * @param nombrePresentacion nombre de la presentacion, no debe ser null
	 * @param numeroLote numero de lote
	 * @param cantidad cantidad en existencia
	 * @param costo costo unitario, no debe ser null
	 * @param precio precio unitario, no debe ser null
	 * @throws ModelConstraintViolationException si el id del producto o el numero de lote es negativo o cero,
	 * si la cantidad es negativa, si el costo o el precio son negativos y si el costo es mayor al precio
	 * @throws EmptyArgumentException si el nombre de la presentacion esta vacio
	 */
	public PresentacionLote(long idProducto, String nombrePresentacion, long numeroLote, 
			int cantidad, BigDecimal costo, BigDecimal precio) 
					throws ModelConstraintViolationException, EmptyArgumentException {
		setIdProducto(idProducto);
		setNombrePresentacion(nombrePresentacion);
		setNumeroLote(numeroLote);
		setCantidad(cantidad);
		setCosto(costo);
		setPrecio(precio);
	}

	/**
	 * Devuelve el id del producto
	 * @return id del producto
	 */
	public long getIdProducto() {return idProducto;}
	
	/**
	 * Establece el id del producto
	 * @param id numero de identificacion del producto, debe ser mayor a cero
	 * @throws ModelConstraintViolationException si el id es negativo o cero
	 */	
	public void setIdProducto(long idProducto) throws ModelConstraintViolationException {
		if(idProducto <= 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.id_producto"));
		this.idProducto = idProducto;
	}
	
	/**
	 * Devuelve el nombre de la presentacion
	 * @return nombre de la presentacion
	 */
	public String getNombrePresentacion() {
		return nombrePresentacion;
	}
	
	/**
	 * Establece el nombre de la presentacion
	 * @param nombrePresentacion nombre de la presentacion, no puede ser null o estar vacio
	 * @throws EmptyArgumentException si el nombre esta vacio
	 */
	public void setNombrePresentacion(String nombrePresentacion) throws EmptyArgumentException {
		assert nombrePresentacion != null : STRINGS_MODELO.getString("error.nom_pres.null");
		if(nombrePresentacion.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.nom_pres.empty"));
		this.nombrePresentacion = nombrePresentacion;
	}
	
	/**
	 * Devuelve el numero de lote
	 * @return numero de lote
	 */
	public long getNumeroLote() {return numeroLote;}
	
	/**
	 * Establece el numero de lote
	 * @param numeroLote numero de lote
	 * @throws ModelConstraintViolationException si el numero de lote es negativo o cero
	 */
	public void setNumeroLote(long numeroLote) throws ModelConstraintViolationException {
		if(numeroLote <= 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.num_lote"));
		this.numeroLote = numeroLote;
	}
	
	/**
	 * Devuelve la cantidad del producto en el lote y presentacion
	 * @return cantidad del producto en el lote y presentacion
	 */
	public int getCantidad() {return cantidad;}
	
	/**
	 * Establece la cantidad del producto en el lote y presentacion
	 * @param cantidad cantidad del producto en el lote y presentacion, no 
	 * debe ser negativa
	 * @throws ModelConstraintViolationException si la cantidad es negativa
	 */
	public void setCantidad(int cantidad) throws ModelConstraintViolationException {
		if(cantidad < 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.cantidad.neg"));
		this.cantidad = cantidad;
	}
	
	/**
	 * Devuelve el costo unitario en la presentacion del producto
	 * @return costo por unidad del producto en la presentacion
	 */
	public BigDecimal getCosto() {return costo;}
	
	/**
	 * Establece el costo unitario en la presentacion del producto
	 * @param costo costo unitario en la presentacion del producto, no debe ser null y 
	 * debe ser mayor o igual a cero y menor o igual al precio
	 * @throws ModelConstraintViolationException si el costo es negativo o si el costo
	 * es mayor al precio
	 */
	public void setCosto(BigDecimal costo) throws ModelConstraintViolationException {
		assert costo != null : STRINGS_MODELO.getString("error.costo.null");
		if(costo.compareTo(new BigDecimal("0.00")) < 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.cantidad.neg"));
		if(precio != null && (costo.compareTo(precio) > 0))
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.costo_gt_precio"));
		this.costo = costo;
	}
	
	/**
	 * Devuelve el precio unitario en la presentacion del producto
	 * @return precio por unidad del producto en la presentacion
	 */
	public BigDecimal getPrecio() {return precio;}
	
	/**
	 * Establece el precio unitario en la presentacion del producto
	 * @param precio precio unitario en la presentacion del producto, no debe ser null y 
	 * debe ser mayor o igual a cero y mayor o igual al costo
	 * @throws ModelConstraintViolationException si el precio es negativo o si el precio 
	 * es menor al costo
	 */
	public void setPrecio(BigDecimal precio) throws ModelConstraintViolationException {
		assert precio != null : STRINGS_MODELO.getString("error.precio.null");
		if(precio.compareTo(new BigDecimal("0.00")) < 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.precio.neg"));
		if(costo != null && (precio.compareTo(costo) < 0))
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.precio_lt_costo"));
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return idProducto + " #" + numeroLote + " " + nombrePresentacion 
				+ " Cantidad: " + cantidad + " Costo: " + costo + " Precio: " + precio ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProducto ^ (idProducto >>> 32));
		result = prime * result + ((nombrePresentacion == null) ? 0 : nombrePresentacion.hashCode());
		result = prime * result + (int) (numeroLote ^ (numeroLote >>> 32));
		return result;
	}

	@Override
	/**
	 * Compara la relacion de presentacion y lote con otro objeto del mismo tipo,
	 * dos objetos de esta clase se consideran iguales si el id de producto, numero
	 * de lote y nombre de presentacion son iguales.
	 * Para el nombre de presentacion no se distingue entre mayusculas y minusculas.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PresentacionLote other = (PresentacionLote) obj;
		if (idProducto != other.idProducto)
			return false;
		if (nombrePresentacion == null) {
			if (other.nombrePresentacion != null)
				return false;
		} else if (!nombrePresentacion.equalsIgnoreCase(other.nombrePresentacion))
			return false;
		if (numeroLote != other.numeroLote)
			return false;
		return true;
	}

}
