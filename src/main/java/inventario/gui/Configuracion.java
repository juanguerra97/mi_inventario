package inventario.gui;

import static inventario.gui.Resources.STRINGS_SETTINGS;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase para organizar la configuracion del programa
 * @author juang
 *
 */
public class Configuracion implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Lista de idiomas soportados
	 */
	public static final List<Idioma> IDIOMAS = Arrays.stream(
			STRINGS_SETTINGS.getString("opciones.idioma")
			.split("%")).map( s -> {
				String parts[] = s.split("&");
				return new Idioma(parts[0],parts[1]);
			}).collect(Collectors.toList());
	
	/**
	 * Lista con las escalas de tiempo soportadas
	 */
	public static final List<String> ESCALAS_TIEMPO = Arrays.stream(
			STRINGS_SETTINGS.getString("opciones.tiempo").split("%"))
			.collect(Collectors.toList());
	
	private boolean capitalizarNombresProductos;
	private boolean capitalizarMarcasProductos;
	private boolean capitalizarNombresPresentaciones;
	private boolean capitalizarNombresCategorias;
	
	private Idioma idioma;
	
	private boolean mostrarProductosPorVencer;
	private int tiempo;
	private String escalaTiempo;
	
	/**
	 * Constructor que recibe todas las opciones	
	 * @param capitalizarNombresProductos valor de la propiedad de capitalizar nombres de productos
	 * @param capitalizarMarcasProductos valor de la propiedad de capitalizar marcas de productos
	 * @param capitalizarNombresPresentaciones valor de la propiedad de capitalizar nombres de presentaciones
	 * @param capitalizarNombresCategorias valor de la propiedad de capitalizar nombres de categorias
	 * @param idioma idioma, no debe ser null
	 * @param mostrarProductosPorVencer valor de la propiedad de mostrar productos por vencer
	 * @param tiempo periodo de tiempo antes de vencer de los productos en el cual se debe mostrar una alerta, 
	 * debe ser negativo
	 * @param escalaTiempo escala de tiempo, no puede ser null
	 */
	public Configuracion(boolean capitalizarNombresProductos, boolean capitalizarMarcasProductos,
			boolean capitalizarNombresPresentaciones, boolean capitalizarNombresCategorias, Idioma idioma,
			boolean mostrarProductosPorVencer, int tiempo, String escalaTiempo) {
		super();
		this.capitalizarNombresProductos = capitalizarNombresProductos;
		this.capitalizarMarcasProductos = capitalizarMarcasProductos;
		this.capitalizarNombresPresentaciones = capitalizarNombresPresentaciones;
		this.capitalizarNombresCategorias = capitalizarNombresCategorias;
		this.setIdioma(idioma);
		this.mostrarProductosPorVencer = mostrarProductosPorVencer;
		this.setTiempo(tiempo);
		this.setEscalaTiempo(escalaTiempo);
	}

	/**
	 * Constructor que da valores predeterminados a cada opcion de configuracion
	 */
	public Configuracion() {
		reset();
	}
	
	/**
	 * Metodo para restablecer la configuracion a los valores predeterminados
	 */
	public void reset() {
		this.capitalizarNombresProductos = true;
		this.capitalizarMarcasProductos = true;
		this.capitalizarNombresPresentaciones = true;
		this.capitalizarNombresCategorias = true;
		this.idioma = IDIOMAS.get(0);
		this.mostrarProductosPorVencer = false;
		this.tiempo = 0;
		this.escalaTiempo = ESCALAS_TIEMPO.get(0);
	}
	
	/**
	 * Devuelve el valor de la propiedad de capitalizar nombres de productos
	 * @return valor de la propiedad de capitalizar nombres de productos
	 */
	public boolean getCapitalizarNombresProductos() {return capitalizarNombresProductos;}
	
	/**
	 * Establece el valor de la propiedad de capitalizar nombres de productos
	 * @param capitalizarNombresProductos valor de la propiedad de capitalizar nombres de productos
	 */
	public void setCapitalizarNombresProductos(boolean capitalizarNombresProductos) {
		this.capitalizarNombresProductos = capitalizarNombresProductos;
	}
	
	/**
	 * Devueve el valor de la propiedad de capitalizar marcas de productos
	 * @return valor de la propiedad de capitalizar marcas de productos
	 */
	public boolean getCapitalizarMarcasProductos() {return capitalizarMarcasProductos;}
	
	/**
	 * Establece el valor de la propiedad de capitalizar marcas de productos
	 * @param capitalizarMarcasProductos valor de la propiedad de capitalizar marcas de productos
	 */
	public void setCapitalizarMarcasProductos(boolean capitalizarMarcasProductos) {
		this.capitalizarMarcasProductos = capitalizarMarcasProductos;
	}
	
	/**
	 * Devuelve el valor de la propiedad de capitalizar nombres de presentaciones
	 * @return valor de la propiedad de capitalizar nombres de presentaciones
	 */
	public boolean getCapitalizarNombresPresentaciones() {return capitalizarNombresPresentaciones;}
	
	/**
	 * Establece si los nombres de presentaciones se deben capitalizar
	 * @param capitalizarNombresPresentaciones
	 */
	public void setCapitalizarNombresPresentaciones(boolean capitalizarNombresPresentaciones) {
		this.capitalizarNombresPresentaciones = capitalizarNombresPresentaciones;
	}
	
	/**
	 * Devuelve el valor de la propiedad de capitalizar nombres de categorias
	 * @return valor de la propiedad de capitalizar nombres de categorias
	 */
	public boolean getCapitalizarNombresCategorias() {return capitalizarNombresCategorias;}
	
	/**
	 * Metodo para establecer si los nombres de categorias se deben capitalizar
	 * @param capitalizarNombresCategorias true si los nombres se deben capitalizar,false en caso contrario
	 */
	public void setCapitalizarNombresCategorias(boolean capitalizarNombresCategorias) {
		this.capitalizarNombresCategorias = capitalizarNombresCategorias;
	}
	
	/**
	 * Devuelve el idioma establecido
	 * @return idioma establecido
	 */
	public Idioma getIdioma() {return idioma;}
	
	/**
	 * Establece el idioma
	 * @param idioma idioma, no debe ser null
	 */
	public void setIdioma(Idioma idioma) {
		assert idioma != null;
		if(IDIOMAS.stream().noneMatch(i -> i.equals(idioma)))
			throw new IllegalArgumentException(
					STRINGS_SETTINGS.getString("error.idioma.nosoportado"));
		this.idioma = idioma;
	}
	
	/**
	 * Devuelve el valor de la opcion de mostrar productos por vencer
	 * @return valor de la opcion de mostrar productos por vencer
	 */
	public boolean getMostrarProductosPorVencer() {return mostrarProductosPorVencer;}
	
	/**
	 * Establece el valor de la opcion de mostrar productos por vencer
	 * @param mostrarProductosPorVencer valor de la opcion de mostrar productos por vencer
	 */
	public void setMostrarProductosPorVencer(boolean mostrarProductosPorVencer) {
		this.mostrarProductosPorVencer = mostrarProductosPorVencer;
	}
	
	public int getTiempo() {return tiempo;}
	
	/**
	 * Establece el periodo de tiempo antes de vencer de los productos en el cual se debe mostrar una alerta
	 * @param tiempo periodo de tiempo antes de vencer de los productos en el cual se debe mostrar una alerta, 
	 * debe ser negativo
	 */
	public void setTiempo(int tiempo) {
		if(tiempo < 0)
			throw new IllegalArgumentException(
					STRINGS_SETTINGS.getString("error.tiempo.negativo"));
		this.tiempo = tiempo;
	}
	
	/**
	 * Devuelve la escala del tiempo
	 * @return escala de tiempo utilizada
	 */
	public String getEscalaTiempo() {return escalaTiempo;}
	
	/**
	 * Establece la escala de tiempo a utilizar
	 * @param escalaTiempo escala de tiempo, no puede ser null
	 */
	public void setEscalaTiempo(String escalaTiempo) {
		assert escalaTiempo != null;
		this.escalaTiempo = escalaTiempo;
	}

}
