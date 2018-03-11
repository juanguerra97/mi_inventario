package inventario.modelo.validacion;

public class Validacion {
	
	/**
	 * Expresion regular para validar un email
	 */
	public static final String REGEX_EMAIL = "^[a-z0-9._%+-]+@([a-z]+\\.)+[a-z]{2,6}$";
	
	/**
	 * Expresion regular para validar el documento de identificacion personal (Guatemala)
	 */
	public static final String REGEX_DPI = "^[1-9]\\d{8}[1-9]\\d{3}$";
	
	/**
	 * Regex para validar un numero de telefono de ocho digitos
	 */
	public static final String REGEX_TELEFONO = "^[1-9]\\d{7}$";
	
	/**
	 * Regex para validar el nombre de un producto
	 */
	public static final String REGEX_NOM_PRODUCTO = "^[\\d.\\s\\p{L}\\p{Punct}]+$";
	
	/**
	 * Regex para validar el nombre de una marca
	 */
	public static final String REGEX_NOM_MARCA = "^[\\d.\\s\\p{L}\\p{Punct}]+$";
	
	// constructor privado
	private Validacion() {}

}
