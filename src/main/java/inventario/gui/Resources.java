package inventario.gui;

import java.util.ResourceBundle;

import inventario.files.ProgramFiles;

public class Resources {
	
	/**
	 * ResourceBundle con los strings utilizados en la interfaz grafica
	 */
	public static final ResourceBundle STRINGS_GUI = 
			ResourceBundle.getBundle("strings/strings_gui", ProgramFiles.getLocale());
	
	public static final ResourceBundle STRINGS_SETTINGS = 
			ResourceBundle.getBundle("strings/strings_settings", ProgramFiles.getLocale());
	
	private Resources() {}

}
