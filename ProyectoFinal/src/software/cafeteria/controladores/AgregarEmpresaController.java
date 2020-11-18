package software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AgregarEmpresaController {

	private Stage stage;
	private ManejadorEscenarios manejador;

	@FXML
	private TextField nombreEmpresa;

	@FXML
	private Button btn_agregar;

	@FXML
	private Button btn_cancelar;

	@FXML
	public void initialize() {
		btn_agregar.setGraphic(new ImageView("file:src/software/cafeteria/images/agregarEmpresa.png"));
		btn_cancelar.setGraphic(new ImageView("file:src/software/cafeteria/images/cancelar.png"));
	}

	@FXML
	void agregarEmpresa() {

		boolean res = manejador.agregarEmpresa(nombreEmpresa.getText());

		if (res) {

			Alert alert = new Alert(AlertType.INFORMATION, "La empresa se agregó con exito", ButtonType.OK);
			alert.showAndWait();
			stage.close();

		} else {
			Alert alert = new Alert(AlertType.ERROR,
					"La empresa con el nombre " + nombreEmpresa.getText() + " ya se encuentra registrada.",
					ButtonType.OK);
			alert.showAndWait();
		}

	}

	@FXML
	void cancelar() {
		stage.close();
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
