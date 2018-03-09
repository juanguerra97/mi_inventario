package inventario.gui;

import java.util.List;
import java.util.function.Supplier;
import static inventario.gui.Resources.STRINGS_GUI;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;

public class LoadData {
	
	/**
	 * Numero maximo de elementos que puede tener una tabla o lista
	 */
	public static final int DEFAULT_MAX = 500;
	
	/**
	 * Metodo para agregar evento a una tabla para que se cargen mas datos al hacer scroll
	 * @param table tabla a la que se agregara el evento, no puede ser null
	 * @param supplier metodo que proporciona los datos, no debe ser null
	 * @param max numero maximo de elementos que puede tener la tabla
	 */
	public static <E> void loadOnScroll(TableView<E> table, Supplier<List<E>> supplier, int max) {
		assert table != null;
		assert supplier != null;
		if(max <= 0)
			max = DEFAULT_MAX;
		final int MAX = max;
		ScrollBar scrollBar = Scrolls.getTableViewVerticalScrollBar(table);
		assert scrollBar != null : STRINGS_GUI.getString("error.scrollbar.null");
		scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
	        double position = newValue.doubleValue();
	        if (position >= scrollBar.getMax() - 10) {
	        	int numItems = table.getItems().size();
	        	List<E> newItems = supplier.get();
	            if (numItems + newItems.size() <= MAX)
	            	table.getItems().addAll(newItems);
	        }
	    });
	}
	
	/**
	 * Metodo para agregar evento a una lista para que se cargen mas datos al hacer scroll 
	 * @param list lista a la que se agregara el evento, no puede ser null
	 * @param supplier metodo que proporciona los datos, no debe ser null
	 * @param max numero maximo de elementos que puede tener la lista
	 */
	public static <E> void loadOnScroll(ListView<E> list, Supplier<List<E>> supplier, int max) {
		assert list != null;
		assert supplier != null;
		if(max <= 0)
			max = DEFAULT_MAX;
		final int MAX = max;
		ScrollBar scrollBar = Scrolls.getListViewVerticalScrollBar(list);
		assert scrollBar != null : STRINGS_GUI.getString("error.scrollbar.null");
		scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
	        double position = newValue.doubleValue();
	        if (position >= scrollBar.getMax() - 10) {
	        	int numItems = list.getItems().size();
	        	List<E> newItems = supplier.get();
	            if (numItems + newItems.size() <= MAX)
	            	list.getItems().addAll(newItems);
	        }
	    });
	}
	
}
