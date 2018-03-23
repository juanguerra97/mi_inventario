package inventario.files;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import inventario.gui.Configuracion;

/**
 * Clase con metodos para manejar los archivos del programa
 * @author juang
 *
 */
public class ProgramFiles {
	
	/**
	 * Ruta del directorio del usuario
	 */
	public static final String USER_HOME = System.getProperty("user.home");
	
	/**
	 * Ruta al directorio de los archivos del programa
	 */
	public static final Path PROGRAM_DIR = Paths.get(USER_HOME, "mi_inventario");
	
	/**
	 * Ruta al archivo de configuracion
	 */
	public static final Path SETTINGS_FILE = Paths.get(PROGRAM_DIR.toString(), "settings.set");
	
	/**
	 * Ruta al objeto locale con la configuracion de idioma
	 */
	public static final Path LOCALE_FILE = Paths.get(PROGRAM_DIR.toString(),"locale.set");
	
	// bloque para crear el directorio del programa
	static {
		if(!Files.exists(PROGRAM_DIR))
			try {
				Files.createDirectories(PROGRAM_DIR);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Metodo para obtener la configuracion guardada del usuario
	 * @return objeto con la configuracion del usuario
	 */
	public static Configuracion getSettings() {
		if(!Files.exists(SETTINGS_FILE))
			return new Configuracion();
		Configuracion config = null;
		try(ObjectInputStream input = 
				new ObjectInputStream(Files.newInputStream(SETTINGS_FILE))){
			config = (Configuracion) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	/**
	 * Metodo para guardar la configuracion del usuario
	 * @param config objeto con la configuracion, no debe ser null
	 */
	public static void saveSettings(Configuracion config) {
		assert config != null;
		try(ObjectOutputStream output = 
				new ObjectOutputStream(Files.newOutputStream(SETTINGS_FILE))){
			output.writeObject(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para obtener la configuracion de idioma
	 * @return configuracion de idioma
	 */
	public static Locale getLocale() {
		if(!Files.exists(LOCALE_FILE))
			return Locale.getDefault();
		Locale locale = null;
		try(ObjectInputStream input = 
				new ObjectInputStream(Files.newInputStream(LOCALE_FILE))){
			locale = (Locale) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return locale;
	}
	
	/**
	 * Metodo para guardar locale
	 * @param locale locale, no debe ser null
	 */
	public static void saveLocale(Locale locale) {
		assert locale != null;
		try(ObjectOutputStream output = 
				new ObjectOutputStream(Files.newOutputStream(LOCALE_FILE))){
			output.writeObject(locale);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
