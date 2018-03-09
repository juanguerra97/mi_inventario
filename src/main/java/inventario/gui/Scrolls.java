package inventario.gui;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;

public class Scrolls {

	/**
	 * Metodo para obtener el scroll bar vertical de un listview
	 * @param listView lista de la que se obtendra el scrollbar, no debe ser null
	 * @return scroll bar si la lista lo tiene, null si no se puede obtener
	 */
	public static ScrollBar getListViewVerticalScrollBar(ListView<?> listView) {
		assert listView != null;
		ScrollBar scrollbar = null;
		for (Node node : listView.lookupAll(".scroll-bar")) {
			if (node instanceof ScrollBar) {
				ScrollBar bar = (ScrollBar) node;
				if (bar.getOrientation().equals(Orientation.VERTICAL)) {
					scrollbar = bar;
					break;
				}
			}
		}
		return scrollbar;
	}
	
	/**
	 * Metodo para obtener el scroll bar vertical de un tableview
	 * @param tableView tabla de la que se obtendra el scrollbar, no debe ser null
	 * @return scroll bar si la tabla lo tiene, null si no se puede obtener
	 */
	public static ScrollBar getTableViewVerticalScrollBar(TableView<?> tableView) {
		assert tableView != null;
		ScrollBar scrollbar = null;
		for (Node node : tableView.lookupAll(".scroll-bar")) {
			if (node instanceof ScrollBar) {
				ScrollBar bar = (ScrollBar) node;
				if (bar.getOrientation().equals(Orientation.VERTICAL)) {
					scrollbar = bar;
					break;
				}
			}
		}
		return scrollbar;
	}
	
}
