package inventario.modelo;

import java.io.Serializable;
import java.util.zip.DataFormatException;
import static inventario.modelo.Resources.STRINGS_MODELO;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.validacion.Validacion;

/**
 * Clase para representar a los clientes
 * @author juang
 *
 */ 
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String dpi;
	private String nombre;
	private String apellido;
	private String telefono;
	private String email;
	private Direccion direccion;
	
	/**
	 * Constructor
	 * @param dpi dpi del cliente, no debe ser null
	 * @param nombre nombre del cliente, no debe ser null
	 * @param apellido apellido del cliente, no debe ser null
	 * @param telefono telefono del cliente, no debe ser null
	 * @param email email del cliente, si no tiene, debe ser null
	 * @param direccion direccion del cliente, no debe ser null
	 * @throws DataFormatException si el DPI, telefono o email no tienen un formato valido
	 * @throws EmptyArgumentException si el nombre o apellido estan vacio
	 */
	public Cliente(String dpi, String nombre, String apellido, String telefono, String email, Direccion direccion) 
			throws DataFormatException, EmptyArgumentException {
		setDpi(dpi);
		setNombre(nombre);
		setApellido(apellido);
		setTelefono(telefono);
		setEmail(email);
		setDireccion(direccion);
	}
	
	/**
	 * Constructor
	 * @param dpi dpi del cliente, no debe ser null
	 * @param nombre nombre del cliente, no debe ser null
	 * @param apellido apellido del cliente, no debe ser null
	 * @param telefono telefono del cliente, no debe ser null
	 * @param direccion direccion del cliente, no debe ser null
	 * @throws DataFormatException si el DPI, telefono o email no tienen un formato valido
	 * @throws EmptyArgumentException si el nombre o apellido estan vacio
	 */
	public Cliente(String dpi, String nombre, String apellido, String telefono, Direccion direccion) 
			throws DataFormatException, EmptyArgumentException {
		this(dpi, nombre, apellido, telefono, null, direccion);
	}

	/**
	 * Devuelve el DPI del cliente
	 * @return DPI del cliente
	 */
	public String getDpi() {return dpi;}
	
	/**
	 * Establece el DPI del cliente
	 * @param dpi dpi del cliente, no debe ser null
	 * @throws DataFormatException si el DPI esta vacio o con un formato invalido
	 */
	public void setDpi(String dpi) throws DataFormatException {
		assert dpi != null : STRINGS_MODELO.getString("error.dpi_cliente.null");
		if(!dpi.matches(Validacion.REGEX_DPI))
			throw new DataFormatException(STRINGS_MODELO.getString("error.dpi_cliente.format"));
		this.dpi = dpi;
	}
	
	/**
	 * Devuelve el nombre del cliente4
	 * @return nombre del cliente
	 */
	public String getNombre() {return nombre;}
	
	/**
	 * Establece el nombre del cliente
	 * @param nombre nombre del cliente, no debe ser null
	 * @throws EmptyArgumentException si el nombre esta vacio
	 */
	public void setNombre(String nombre) throws EmptyArgumentException {
		assert nombre != null : STRINGS_MODELO.getString("error.nombre_cliente.null");
		if(nombre.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.nombre_cliente.empty"));
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve el apellido del cliente
	 * @return apellido del cliente
	 */
	public String getApellido() {return apellido;}
	
	/**
	 * Establece el apellido del cliente
	 * @param apellido apellido del cliente, no debe ser null
	 * @throws EmptyArgumentException si el apellido esta vacio
	 */
	public void setApellido(String apellido) throws EmptyArgumentException {
		assert apellido != null : STRINGS_MODELO.getString("error.apellido_cliente.null");
		if(apellido.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.apellido_cliente.empty"));
		this.apellido = apellido;
	}
	
	/**
	 * Devuelve el telefono del cliente
	 * @return telefono del cliente
	 */
	public String getTelefono() {return telefono;}
	
	/**
	 * Establece el numero de telefono del cliente
	 * @param telefono numero de telefono del cliente, no debe ser null
	 * @throws DataFormatException si el numero esta vacio o no es valido
	 */
	public void setTelefono(String telefono) throws DataFormatException {
		assert telefono != null : STRINGS_MODELO.getString("error.telefono.null");
		if(!telefono.matches(Validacion.REGEX_TELEFONO))
			throw new DataFormatException(STRINGS_MODELO.getString("error.telefono.format"));
		this.telefono = telefono;
	}
	
	/**
	 * Devuelve el email del cliente
	 * @return email del cliente, null si no tiene
	 */
	public String getEmail() {return email;}
	
	/**
	 * Establece el email del cliente
	 * @param email email del cliente, si no tiene debe ser null
	 * @throws DataFormatException si el formato del email no es valido
	 */
	public void setEmail(String email) throws DataFormatException {
		if(email != null && !email.matches(Validacion.REGEX_EMAIL))
			throw new DataFormatException(STRINGS_MODELO.getString("error.email.format"));
		this.email = email;
	}
	
	/**
	 * Devuelve la direccion del cliente
	 * @return direccion del cliente
	 */
	public Direccion getDireccion() {return direccion;}
	
	/**
	 * Establece la direccion del cliente
	 * @param direccion direccion del cliente, no debe ser null
	 */
	public void setDireccion(Direccion direccion) {
		assert direccion != null : STRINGS_MODELO.getString("error.direccion.null");
		this.direccion = direccion;
	}
	
	@Override
	public String toString() {
		return  String.join(" ", dpi,nombre,apellido,telefono,direccion.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dpi == null) ? 0 : dpi.hashCode());
		return result;
	}

	@Override
	/**
	 * Compara dos clientes por su DPI
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (dpi == null) {
			if (other.dpi != null)
				return false;
		} else if (!dpi.equals(other.dpi))
			return false;
		return true;
	}
	
}
