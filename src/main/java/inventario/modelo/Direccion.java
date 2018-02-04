package inventario.modelo;

import java.io.Serializable;
import static inventario.modelo.Resources.STRINGS_MODELO;
import inventario.modelo.error.EmptyArgumentException;
import inventario.modelo.error.ModelConstraintViolationException;

/**
 * Clase para crear direcciones
 * @author juang
 * 
 */
public class Direccion implements Serializable {

	private static final long serialVersionUID = 1L;

	private String departamento; // valido para Guatemala
	private String ciudad;
	private byte zona;
	
	/**
	 * Constructor
	 * @param ciudad ciudad de la direccion, no debe ser null o estar vacio
	 * @param departamento departamento de la direccion, no debe ser null o estar vacio
	 * @param zona zona de la direccion, debe estar entre 1 y 50
	 * @throws ModelConstraintViolationException si la zona no esta en el rango entre 1 y 50
	 * @throws EmptyArgumentException si la ciudad o el departamento estan vacios
	 */
	public Direccion(String departamento,String ciudad, byte zona) 
			throws ModelConstraintViolationException, EmptyArgumentException {
		assert ciudad != null : STRINGS_MODELO.getString("error.ciudad_dir.null");
		assert departamento != null : STRINGS_MODELO.getString("error.dept_dir.null");
		if(ciudad.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.ciudad_dir.empty"));
		if(departamento.isEmpty())
			throw new EmptyArgumentException(STRINGS_MODELO.getString("error.dept_dir.emtpy"));
		if(zona < 1 || zona > 50)
			throw new ModelConstraintViolationException(STRINGS_MODELO.getString("error.zona_dir.range"));
		this.ciudad = ciudad;
		this.departamento = departamento;
		this.zona = zona;
	}
	
	/**
	 * Devuelve el departamento de la ciudad
	 * @return departamento de la ciudad
	 */
	public String getDepartamento() {return departamento;}
	
	/**
	 * Devuelve la ciudad de la direccion
	 * @return ciudad de la direccion
	 */
	public String getCiudad() {return ciudad;}

	/**
	 * Devuelve la zona
	 * @return zona
	 */
	public byte getZona() {return zona;}
	
	@Override
	public String toString() {
		return "Zona " + zona + ", " + ciudad + ", " + departamento + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + zona;
		return result;
	}

	@Override
	/**
	 * Compara esta direccion con otro objeto que debe ser una direccion.
	 * Dos direcciones se consideran iguales si tienen la misma ciudad, departamento
	 * y zona sin tomar en cuenta mayusculas y minusculas para la ciudad y el
	 * departamento.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direccion other = (Direccion) obj;
		if (ciudad == null) {
			if (other.ciudad != null)
				return false;
		} else if (!ciudad.equalsIgnoreCase(other.ciudad))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equalsIgnoreCase(other.departamento))
			return false;
		if (zona != other.zona)
			return false;
		return true;
	}
	
}
