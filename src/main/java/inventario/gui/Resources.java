package inventario.gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class Resources {
	
	/**
	 * ResourceBundle con los strings utilizados en la interfaz grafica
	 */
	public static final ResourceBundle STRINGS_GUI = ResourceBundle.getBundle("strings/strings_gui", Locale.getDefault());
	
	public static final ResourceBundle STRINGS_SETTINGS = ResourceBundle.getBundle("strings/strings_settings", Locale.getDefault());
	
	private Resources() {}

}
