package software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import software.cafeteria.delegado.ProductoObservable;

public class ModificarProductoController {

	private ManejadorEscenarios manejador;

	private Stage stage;

	private int index;

	@FXML
	private TextField codigoBarras;

	@FXML
	private TextField nombreProducto;

	@FXML
	private TextField costoProducto;

	@FXML
	private TextField precioProducto;

	@FXML
	private TextField presentacionProducto;

	@FXML
	private ComboBox<String> iva;

	@FXML
	private TextField cantidad;

	@FXML
	private ComboBox<String> tipoProducto;

	@FXML
	private ComboBox<String> empresaDistri;

	@FXML
	private Button btn_cancelar;

	@FXML
	private Button btn_modificar;

	@FXML
	public void initialize() {
		btn_cancelar.setGraphic(new ImageView("file:src/software/cafeteria/images/cancelar.png"));
		btn_modificar.setGraphic(new ImageView("file:src/software/cafeteria/images/modificarProducto.png"));
	}

	@FXML
	void verificarTipo() {

		String tipo = tipoProducto.getSelectionModel().getSelectedItem();
		if (tipo != null) {
			if (tipo.equals("Otro tipo")) {
				manejador.ventanaAgregarTipo();
				tipoProducto.setItems(manejador.listarTipos());

			}
		}
	}

	@FXML
	void cancelar() {
		stage.close();
	}

	@FXML
	void modificarProducto() {

		try {
			String codigo_Barras = codigoBarras.getText();
			String nombre_Producto = nombreProducto.getText();
			int costo_Producto = Integer.parseInt(costoProducto.getText());
			int presentacion_Producto = Integer.parseInt(presentacionProducto.getText());
			int precio_Producto = Integer.parseInt(precioProducto.getText());
			int iva_ = Integer.parseInt(iva.getSelectionModel().getSelectedItem());
			String tipo_Producto = tipoProducto.getSelectionModel().getSelectedItem();
			int cantidad_ = Integer.parseInt(cantidad.getText());
			String empresa = empresaDistri.getSelectionModel().getSelectedItem();
			ProductoObservable productoAnterior = manejador.listarProductos().get(index);
			manejador.modificarProducto(codigo_Barras, nombre_Producto, costo_Producto, presentacion_Producto,
					precio_Producto, iva_, tipo_Producto, cantidad_, productoAnterior, empresa);

			Alert alert = new Alert(AlertType.CONFIRMATION, "Modificación exitosa", ButtonType.OK);
			alert.showAndWait();
			stage.close();
		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.ERROR, "Seleccione valores válidos", ButtonType.OK);
			alert.showAndWait();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR, "Valores no númericos, ingrese valores válidos", ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void verificarEmpresa() {
		String empresa = empresaDistri.getSelectionModel().getSelectedItem();
		if (empresa != null) {
			if (empresa.equals("Otra empresa")) {
				manejador.ventanaAgregarEmpresa();
				empresaDistri.setItems(manejador.listarEmpresas());

			}
		}
	}

	@FXML
	public void verificarIva() {
		String iva_ = iva.getSelectionModel().getSelectedItem();
		if (iva_ != null) {
			if (iva_.equals("Otro Iva")) {
				manejador.abrirAgregarIva();

				iva.setItems(manejador.listarIva());
			}
		}
	}

	public void actualiarCampos() {

		codigoBarras.setText(manejador.listarProductos().get(index).getCodigoBarras().getValue());
		nombreProducto.setText(manejador.listarProductos().get(index).getNombre().getValue());
		costoProducto.setText(manejador.listarProductos().get(index).getCosto().getValue());
		presentacionProducto.setText(manejador.listarProductos().get(index).getPresentacion().getValue());
		precioProducto.setText(manejador.listarProductos().get(index).getPrecio().getValue());
		iva.getSelectionModel().select((manejador.listarProductos().get(index).getIva().getValue()));
		cantidad.setText(manejador.listarProductos().get(index).getCantidad().getValue());
		tipoProducto.getSelectionModel().select(manejador.listarProductos().get(index).getTipoProducto().getValue());
		empresaDistri.getSelectionModel().select(manejador.listarProductos().get(index).getEmpresa().getValue());

	}

	public ManejadorEscenarios getManejador() {
		return manejador;
	}

	public void setManejador(ManejadorEscenarios manejador, int index) {
		this.manejador = manejador;
		this.index = index;
		tipoProducto.setItems(manejador.listarTipos());
		empresaDistri.setItems(manejador.listarEmpresas());
		iva.setItems(manejador.listarIva());
		actualiarCampos();
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
