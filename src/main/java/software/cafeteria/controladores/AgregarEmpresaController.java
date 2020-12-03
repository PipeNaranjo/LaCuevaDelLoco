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

public class AgregarEmpresaController {

	private Stage stage;
	private ManejadorEscenarios manejador;

	@FXML
	private TextField nombreEmpresa;

	@FXML
	private Button btnAgregar;

	@FXML
	private Button btnCancelar;

	@FXML
	public void initialize() {
		btnAgregar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/agregarEmpresa.png"));
		btnCancelar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/cancelar.png"));
	}

	@FXML
	void agregarEmpresa() {

		if (!nombreEmpresa.getText().equals("")) {
			boolean res = manejador.agregarEmpresa(nombreEmpresa.getText());

			if (res) {

				Alert alert = new Alert(AlertType.INFORMATION, "La empresa se agreg� con exito", ButtonType.OK);
				alert.showAndWait();
				stage.close();

			} else {
				Alert alert = new Alert(AlertType.ERROR,
						"La empresa con el nombre " + nombreEmpresa.getText() + " ya se encuentra registrada.",
						ButtonType.OK);
				alert.showAndWait();
			}
		}

	}

	public void verificar(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			agregarEmpresa();

		}
	}

	@FXML
	void cancelar() {
		stage.close();
		if (manejador.isValidar()) {
			manejador.getAgregarProductoControlador().getEmpresaDistri().getSelectionModel().clearSelection();
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
