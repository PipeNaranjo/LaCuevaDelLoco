package main.java.software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AgregarIvaController {

	private ManejadorEscenarios manejador;
	private Stage stage;

	@FXML
	private Button btnAgregar;

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField iva;

	@FXML
	private void initialize() {

		btnAgregar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/agregarTipo.png"));
		btnCancelar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/cancelar.png"));
	}

	@FXML
	void agregarIva() {
		if (!iva.getText().equalsIgnoreCase("")) {
			try {
				if (Integer.parseInt(iva.getText()) > 0) {
					boolean res = manejador.agregarIva(Integer.parseInt(iva.getText()));
					if (res) {
						Alert alert = new Alert(AlertType.INFORMATION, "Se agregó el iva correctamente", ButtonType.OK);
						alert.show();
					} else {
						Alert alert = new Alert(AlertType.ERROR, "El iva ya se encuentra registrado", ButtonType.OK);
						alert.show();
					}
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR, "Valor no numérico", ButtonType.OK);
				alert.showAndWait();
			}
		}
	}

	public void verificar(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			agregarIva();

		}
	}

	@FXML
	void cancelar() {
		stage.close();
		if (manejador.isValidar()) {
			manejador.getAgregarProductoControlador().getIva().getSelectionModel().clearSelection();
		} else {
			manejador.getModificarProductoControlador().actualiarCampos();
		}
	}

	public ManejadorEscenarios getManejador() {
		return manejador;
	}

	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
