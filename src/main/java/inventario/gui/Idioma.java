package inventario.gui;

import java.io.Serializable;

/**
 * Clase para guardar informacion sobre un idioma
 * @author juang
 *
 */
public class Idioma implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idioma,locale;
	
	/**
	 * Constructor
	 * @param idioma nombre completo del idioma, no debe ser null
	 * @param locale codigo de localizacion del idioma, no debe ser null
	 */
	public Idioma(String idioma, String locale) {
		setIdioma(idioma);
		setLocale(locale);
	}

	/**
	 * Devuelve el nombre del idioma
	 * @return nombre del idioma
	 */
	public String getIdioma() {return idioma;}

	/**
	 * Metodo para establecer el nombre del idioma
	 * @param idioma nombre completo del idioma, no debe ser null
	 */
	public void setIdioma(String idioma) {
		assert idioma != null;
		this.idioma = idioma;
	}

	/**
	 * Devuelve el codigo de localizacion del idioma
	 * @return codigo de localizacion del idioma
	 */
	public String getLocale() {return locale;}

	/**
	 * Establece el codigo de localizacion del idioma
	 * @param locale codigo de localizacion del idioma, no debe ser null
	 */
	public void setLocale(String locale) {
		assert locale != null;
		this.locale = locale;
	}
	
	@Override
	public String toString() {return idioma;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idioma == null) ? 0 : idioma.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Idioma other = (Idioma) obj;
		if (idioma == null) {
			if (other.idioma != null)
				return false;
		} else if (!idioma.equals(other.idioma))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}

}
