package software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AgregarProductoController {

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
	private ComboBox<String> empresaDistri;

	@FXML
	private ComboBox<String> tipoProducto;

	@FXML
	private Button btn_agregar;

	@FXML
	private Button btn_cancelar;

	@FXML
	public void initialize() {

		btn_agregar.setGraphic(new ImageView("file:src/software/cafeteria/images/agregarProducto.png"));
		btn_cancelar.setGraphic(new ImageView("file:src/software/cafeteria/images/cancelar.png"));

	}

	@FXML
	public void agregarProducto() {

		try {
			if (!(codigoBarras.getText().equals("") || nombreProducto.getText().equals("")
					|| costoProducto.getText().equals("") || presentacionProducto.getText().equals("")
					|| precioProducto.getText().equals("") || cantidad.getText().equals(""))) {
				String codigo_Barras = codigoBarras.getText();
				String nombre_Producto = nombreProducto.getText();
				int costo_Producto = Integer.parseInt(costoProducto.getText());
				int presentacion_Producto = Integer.parseInt(presentacionProducto.getText());
				int precio_Producto = Integer.parseInt(precioProducto.getText());
				int iva_ = -1;
				String seleccion = iva.getSelectionModel().getSelectedItem();
				if (seleccion.equals("5")) {
					iva_ = 5;
				} else if (seleccion.equals("19")) {
					iva_ = 19;
				} else if (seleccion.equals("Exento")) {
					iva_ = 0;
				}
				String tipo_Producto = tipoProducto.getSelectionModel().getSelectedItem();
				int cantidad_ = Integer.parseInt(cantidad.getText());
				String empresa = empresaDistri.getSelectionModel().getSelectedItem();
				boolean res = manejador.agregarProducto(codigo_Barras, nombre_Producto, costo_Producto,
						presentacion_Producto, precio_Producto, iva_, tipo_Producto, cantidad_, empresa);

				if (res) {
					Alert alert = new Alert(AlertType.INFORMATION, "Se agregó con exito el Producto", ButtonType.OK);
					alert.showAndWait();
					agregar.close();
				} else {
					Alert alert = new Alert(AlertType.ERROR,
							"El producto con el código " + codigo_Barras + " ya se encuentra registrado.",
							ButtonType.OK);
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR, "Ingrese valores en todos los campos", ButtonType.OK);
				alert.showAndWait();
			}

		} catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.ERROR, "Seleccione valores válidos", ButtonType.OK);
			alert.showAndWait();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR, "Valores no númericos, ingrese valores válidos", ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	public void cancelar() {

		agregar.close();

	}

	@FXML
	public void verificarEmpresa() {
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

	@FXML
	public void verificarTipo() {

		String tipo = tipoProducto.getSelectionModel().getSelectedItem();
		if (tipo != null) {
			if (tipo.equals("Otro tipo")) {
				manejador.ventanaAgregarTipo();
				tipoProducto.setItems(manejador.listarTipos());
			}
		}
	}

	private ManejadorEscenarios manejador;
	private Stage agregar;

	public ManejadorEscenarios getManejador() {
		return manejador;
	}

	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
		tipoProducto.setItems(manejador.listarTipos());
		empresaDistri.setItems(manejador.listarEmpresas());
		iva.setItems(manejador.listarIva());

	}

	public Stage getStage() {
		return agregar;
	}

	public void setStage(Stage agregar) {
		this.agregar = agregar;
	}

}
