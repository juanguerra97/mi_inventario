package inventario.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import static inventario.modelo.Resources.STRINGS_MODELO;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

/**
 * Informacion general de un elemento de una transaccion
 * @author juang
 * 
 */
public abstract class ItemTransaccion implements Serializable {

	private static final long serialVersionUID = 1L;

	private long numeroTransaccion;// numero de la transaccion a la que pertenece el item
	private String nomProducto;
	private String marcaProducto;
	private String presentacionProducto;
	private long loteProducto;
	private BigDecimal valorUnitario = null;
	private int cantidad = -1;
	private BigDecimal subtotal = null;
	
	/**
	 * Constructor
	 * @param numeroTransaccion numero de la transaccion a la que pertenece el item
	 * @param nomProducto nombre del producto, no debe ser null
	 * @param marcaProducto marca del producto, no debe ser null
	 * @param presentacionProducto presentacion del producto, no debe ser null
	 * @param loteProducto lote del producto
	 * @param valorUnitario valor unitario del producto, no puede ser null
	 * @param cantidad cantidad del producto
	 * @throws ModelConstraintViolationException si el numero de transaccion, lote, cantidad o valor unitario 
	 * son invalidos
	 * @throws EmptyArgumentException si la marca, nombre de producto o la presentacion estan vacios
	 */
	public ItemTransaccion(long numeroTransaccion, String nomProducto, String marcaProducto, String presentacionProducto,
			long loteProducto, BigDecimal valorUnitario, int cantidad) 
					throws ModelConstraintViolationException, EmptyArgumentException {
		setNumeroTransaccion(numeroTransaccion);
		setNomProducto(nomProducto);
		setMarcaProducto(marcaProducto);
		setPresentacionProducto(presentacionProducto);
		setLoteProducto(loteProducto);
		setValorUnitario(valorUnitario);
		setCantidad(cantidad);
	}

	/**
	 * Devuelve el numero de la transaccion a la que pertenece el item
	 * @return numero de la transaccion a la que pertenece el item
	 */
	public long getNumeroTransaccion() {return numeroTransaccion;}
	
	/**
	 * Establece el numero de la transaccion a la que pertenece el item
	 * @param numeroTransaccion numero de la transaccion a la que pertenece el item
	 * @throws ModelConstraintViolationException si el numero de transaccion es negativo o cero
	 */ 
	public void setNumeroTransaccion(long numeroTransaccion) throws ModelConstraintViolationException {
		if(numeroTransaccion <= 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.num_transac"));
		this.numeroTransaccion = numeroTransaccion;
	}
	
	/**
	 * Devuelve el nombre del producto
	 * @return
	 */
	public String getNomProducto() {return nomProducto;}
	
	/**
	 * Establece el nombre del producto
	 * @param nomProducto nombre del producto, no debe ser null
	 * @throws EmptyArgumentException  si el nombre del producto esta vacio
	 */
	public void setNomProducto(String nomProducto) throws EmptyArgumentException {
		assert nomProducto != null : STRINGS_MODELO.getString("error.nombre_producto.null");
		if(nomProducto.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.nombre_producto.empty"));
	}
	
	/**
	 * Devuelve la marca del producto
	 * @return marca del producto
	 */
	public String getMarcaProducto() {return marcaProducto;}
	
	/**
	 * Establece la marca
	 * @param marcaProducto marca del producto, no puede ser null
	 * @throws EmptyArgumentException si la marca esta vacia
	 */
	public void setMarcaProducto(String marcaProducto) throws EmptyArgumentException {
		assert marcaProducto != null : STRINGS_MODELO.getString("error.marca_producto.null");
		if(marcaProducto.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.marca_producto.empty"));
		this.marcaProducto = marcaProducto;
	}
	
	/**
	 * Devuelve la presentacion del producto
	 * @return presentacion del producto
	 */
	public String getPresentacionProducto() {return presentacionProducto;}
	
	/**
	 * Establece la presentacion del producto
	 * @param presentacionProducto presentacion del producto, no puede ser null
	 * @throws EmptyArgumentException si la presentacion esta vacia
	 */
	public void setPresentacionProducto(String presentacionProducto) throws EmptyArgumentException {
		assert presentacionProducto != null : STRINGS_MODELO.getString("error.nom_pres.null");
		if(presentacionProducto.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.nom_pres.empty"));
		this.presentacionProducto = presentacionProducto;
	}
	
	/**
	 * Devuelve el lote del producto
	 * @return lote del producto
	 */
	public long getLoteProducto() {return loteProducto;}
	
	/**
	 * Establece el lote del producto
	 * @param loteProducto lote del producto
	 * @throws ModelConstraintViolationException si el lote es negativo o cero
	 */
	public void setLoteProducto(long loteProducto) throws ModelConstraintViolationException {
		if(loteProducto <= 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.num_lote"));
		this.loteProducto = loteProducto;
	}
	
	/**
	 * Devuelve el valor unitario
	 * @return valor unitario 
	 */
	public BigDecimal getValorUnitario() {return valorUnitario;}

	/**
	 * Establece el valor unitario del producto
	 * @param valorUnitario valor unitario, no debe ser null
	 * @throws ModelConstraintViolationException si el valor unitario es negativo
	 */
	public void setValorUnitario(BigDecimal valorUnitario) throws ModelConstraintViolationException {
		assert valorUnitario != null : STRINGS_MODELO.getString("error.val_transac.null");
		if(valorUnitario.compareTo(new BigDecimal("0.00")) < 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.val_trasac.neg"));
		this.valorUnitario = valorUnitario;
		setSubtotal();
	}
	
	/**
	 * Devuelve la cantidad
	 * @return cantidad
	 */
	public int getCantidad() {return cantidad;}
	
	/**
	 * Establece la cantidad
	 * @param cantidad
	 * @throws ModelConstraintViolationException si la cantidad es negativa o cero
	 */
	public void setCantidad(int cantidad) throws ModelConstraintViolationException {
		if(cantidad <= 0)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.cant_trasac"));
		this.cantidad = cantidad;
		setSubtotal();
	}
	
	/**
	 * Incrementa la cantidad
	 * @param aumento incremento
	 */
	public void aumentarCantidad(int aumento) {
		if(aumento <= 0)
			return;
		cantidad += aumento;
		setSubtotal();
	}
	
	/**
	 * Reduce la cantidad
	 * @param decremento reduccion
	 */
	public void reducirCantidad(int decremento) {
		if(decremento <= 0 || decremento >= cantidad)
			return;
		cantidad -= decremento;
		setSubtotal();
	}
	
	/**
	 * Devuelve el subtotal
	 * @return subtotal
	 */
	public BigDecimal getSubtotal() {return subtotal;}
	
	/**
	 * Calcula y establece el subtotal
	 */
	private void setSubtotal() {
		if(valorUnitario != null && cantidad != -1)
			this.subtotal = valorUnitario.multiply(new BigDecimal(cantidad));
	}
	
	@Override
	public String toString() {
		return "#" + numeroTransaccion + " Producto: " + nomProducto + " Marca: " + marcaProducto 
				+ " Presentacion: " + presentacionProducto + " #Lote: " + loteProducto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (loteProducto ^ (loteProducto >>> 32));
		result = prime * result + ((marcaProducto == null) ? 0 : marcaProducto.hashCode());
		result = prime * result + ((nomProducto == null) ? 0 : nomProducto.hashCode());
		result = prime * result + (int) (numeroTransaccion ^ (numeroTransaccion >>> 32));
		result = prime * result + ((presentacionProducto == null) ? 0 : presentacionProducto.hashCode());
		return result;
	}

	@Override
	/**
	 * Dos objetos de esta clase se consideran iguales si tienen el mismo 
	 * numero de transaccion, el mismo nombre de producto, el mismo lote, la misma marca
	 * y la misma presentacion.
	 * Para la comparacion de marca y presentacion no se diferencia entre mayusculas y 
	 * minusculas
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemTransaccion other = (ItemTransaccion) obj;
		if (loteProducto != other.loteProducto)
			return false;
		if (marcaProducto == null) {
			if (other.marcaProducto != null)
				return false;
		} else if (!marcaProducto.equals(other.marcaProducto))
			return false;
		if (nomProducto == null) {
			if (other.nomProducto != null)
				return false;
		} else if (!nomProducto.equals(other.nomProducto))
			return false;
		if (numeroTransaccion != other.numeroTransaccion)
			return false;
		if (presentacionProducto == null) {
			if (other.presentacionProducto != null)
				return false;
		} else if (!presentacionProducto.equals(other.presentacionProducto))
			return false;
		return true;
	}
	
}
