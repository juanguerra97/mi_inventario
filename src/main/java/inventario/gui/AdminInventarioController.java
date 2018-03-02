package inventario.gui;

import java.math.BigDecimal;
import java.time.LocalDate;

import inventario.modelo.Lote;
import inventario.modelo.Presentacion;
import inventario.modelo.PresentacionLote;
import inventario.modelo.Producto;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminInventarioController {
	
	@FXML private ListView<String> listMarcas;
	
	@FXML private TableView<Producto> tblProds;
	@FXML private TableColumn<Producto,Long> colIdProd;
	@FXML private TableColumn<Producto,String> colNomProd;
	@FXML private TableColumn<Producto,String> colMarcaProd;
	
	@FXML private TableView<Presentacion> tblPres;
	@FXML private TableColumn<Presentacion,String> colNomPres;
	
	@FXML private TableView<Lote> tblLotes;
	@FXML private TableColumn<Lote,Long> colNumLote;
	@FXML private TableColumn<Lote,LocalDate> colFechaLote;
	
	@FXML private TableView<PresentacionLote> tblInv;
	@FXML private TableColumn<PresentacionLote,Long> colLotInv;
	@FXML private TableColumn<PresentacionLote,String> colPresInv;
	@FXML private TableColumn<PresentacionLote,BigDecimal> colPrecInv;
	@FXML private TableColumn<PresentacionLote,BigDecimal> colCostInv;
	@FXML private TableColumn<PresentacionLote,Integer> colCantInv;
	
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
		
	}

}
