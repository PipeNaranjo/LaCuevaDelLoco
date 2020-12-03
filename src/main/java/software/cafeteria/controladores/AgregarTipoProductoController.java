package main.java.software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AgregarTipoProductoController {

	private Stage stage;
	private ManejadorEscenarios manejador;

	@FXML
	private TextField agregarTipo;

	@FXML
	private Button btnAgregar;

	@FXML
	private Button btnCancelar;

	@FXML
	public void initialize() {
		btnAgregar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/agregarTipo.png"));
		btnCancelar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/cancelar.png"));
	}

	@FXML
	void agregarTipo() {
		if (!agregarTipo.getText().equalsIgnoreCase("")) {
			boolean res = manejador.agregarTipoProducto(agregarTipo.getText());

			if (res) {
				Alert alert = new Alert(AlertType.INFORMATION, "El tipo de producto se agrego con exito",
						ButtonType.OK);
				alert.showAndWait();
				stage.close();
			} else {
				Alert alert = new Alert(AlertType.ERROR,
						"El tipo de producto con el nombre " + agregarTipo.getText() + " ya se encuentra registrado.",
						ButtonType.OK);
				alert.showAndWait();
			}
		}
	}

	public void verificar(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			agregarTipo();

		}
	}

	@FXML
	void cancelar() {

		stage.close();
		if (manejador.isValidar()) {
			manejador.getAgregarProductoControlador().getTipoProducto().getSelectionModel().clearSelection();
		} else {
			manejador.getModificarProductoControlador().actualiarCampos();
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public ManejadorEscenarios getManejador() {
		return manejador;
	}

	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
	}

}