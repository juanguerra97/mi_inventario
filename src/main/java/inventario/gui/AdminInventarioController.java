package inventario.gui;

import static inventario.gui.Resources.STRINGS_GUI;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.controlsfx.control.NotificationPane;

import inventario.modelo.Lote;
import inventario.modelo.Presentacion;
import inventario.modelo.PresentacionLote;
import inventario.modelo.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AdminInventarioController {
	
	@FXML private ListView<String> listMarcas;
	@FXML private MenuItem menuItemDesMarca;
	
	@FXML private ListView<String> listCategorias;
	@FXML private MenuItem menuItemDesCategoria;
	
	@FXML private TableView<Producto> tblProds;
	@FXML private TableColumn<Producto,Long> colIdProd;
	@FXML private TableColumn<Producto,String> colNomProd;
	@FXML private TableColumn<Producto,String> colMarcaProd;
	
	@FXML private MenuItem menuItemDesProd;
	@FXML private MenuItem menuItemElProd;
	@FXML private CheckMenuItem checkFiltrarProdsMarca;
	@FXML private CheckMenuItem checkFiltrarProdsCategoria;
	
	@FXML private TableView<Presentacion> tblPres;
	@FXML private TableColumn<Presentacion,String> colNomPres;
	@FXML private MenuItem menuItemDesPresentacion;
	@FXML private MenuItem menuItemElPresentacion;
	
	@FXML private TableView<Lote> tblLotes;
	@FXML private TableColumn<Lote,Long> colNumLote;
	@FXML private TableColumn<Lote,LocalDate> colFechaLote;
	@FXML private MenuItem menuItemDesLote;
	@FXML private MenuItem menuItemElLote;
	
	@FXML private TableView<PresentacionLote> tblInv;
	@FXML private TableColumn<PresentacionLote,Long> colLotInv;
	@FXML private TableColumn<PresentacionLote,String> colPresInv;
	@FXML private TableColumn<PresentacionLote,BigDecimal> colPrecInv;
	@FXML private TableColumn<PresentacionLote,BigDecimal> colCostInv;
	@FXML private TableColumn<PresentacionLote,Integer> colCantInv;
	@FXML private MenuItem menuItemDesInv;
	@FXML private MenuItem menuItemElInv;
	
	private Stage vtnNewProd;
	private NotificationPane paneNewProd;
	private NewProductoController newProdCtrl;
	
	@FXML
	private void initialize() {
		
		colIdProd.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNomProd.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colMarcaProd.setCellValueFactory(new PropertyValueFactory<>("marca"));
		
		colNomPres.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		
		colNumLote.setCellValueFactory(new PropertyValueFactory<>("numero"));
		colFechaLote.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
		
		colLotInv.setCellValueFactory(new PropertyValueFactory<>("numeroLote"));
		colPresInv.setCellValueFactory(new PropertyValueFactory<>("nombrePresentacion"));
		colPrecInv.setCellValueFactory(new PropertyValueFactory<>("precio"));
		colCostInv.setCellValueFactory(new PropertyValueFactory<>("costo"));
		colCantInv.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		
		vtnNewProd = new Stage();
		vtnNewProd.setMinWidth(300);
		vtnNewProd.setMinHeight(300);
		vtnNewProd.initModality(Modality.APPLICATION_MODAL);
		vtnNewProd.setTitle(STRINGS_GUI.getString("title.new.producto"));
		paneNewProd = new NotificationPane();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventario/gui/NewProducto.fxml"),STRINGS_GUI);
		try {
			paneNewProd.setContent(loader.load());
			newProdCtrl = loader.getController();
			vtnNewProd.setScene(new Scene(paneNewProd,300,300));
			newProdCtrl.setStage(vtnNewProd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void onNuevoProducto(ActionEvent event) {
		newProdCtrl.reset();
		vtnNewProd.centerOnScreen();
		vtnNewProd.showAndWait();
	}
	
	@FXML
	private void onProductosContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblProds.getSelectionModel().getSelectedItem() != null;
		menuItemDesProd.setDisable(!haySeleccion);
		menuItemElProd.setDisable(!haySeleccion);
		checkFiltrarProdsMarca.setDisable(listMarcas.getItems().isEmpty());
		checkFiltrarProdsCategoria.setDisable(listCategorias.getItems().isEmpty());
	}
	
	@FXML
	private void onMarcasContextMenuShown(WindowEvent event) {
		menuItemDesMarca.setDisable(listMarcas.getSelectionModel().getSelectedItem() == null);
	}
	
	@FXML
	private void onCategoriasContextMenuShown(WindowEvent event) {
		menuItemDesCategoria.setDisable(listCategorias.getSelectionModel().getSelectedItem() == null);
	}
	
	@FXML
	private void onPresentacionesContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblPres.getSelectionModel().getSelectedItem() != null;
		menuItemDesPresentacion.setDisable(!haySeleccion);
		menuItemElPresentacion.setDisable(!haySeleccion);
	}
	
	@FXML
	private void onLotesContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblLotes.getSelectionModel().getSelectedItem() != null;
		menuItemDesLote.setDisable(!haySeleccion);
		menuItemElLote.setDisable(!haySeleccion);
	}
	
	@FXML
	private void onInventarioContextMenuShown(WindowEvent event) {
		boolean haySeleccion = tblInv.getSelectionModel().getSelectedItem() != null;
		menuItemDesInv.setDisable(!haySeleccion);
		menuItemElInv.setDisable(!haySeleccion);
	}

}
