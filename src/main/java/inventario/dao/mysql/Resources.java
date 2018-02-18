package inventario.dao.mysql;

import java.util.Locale;
import java.util.ResourceBundle;

public class Resources {

	/**
	 * ResourceBundle con los strings utilizados en las clases que implementan 
	 * las interfaces DAO
	 */
	public static final ResourceBundle STRINGS_DAO = ResourceBundle.getBundle("strings/strings_dao", Locale.getDefault());
	
	private Resources() {}
	
}
