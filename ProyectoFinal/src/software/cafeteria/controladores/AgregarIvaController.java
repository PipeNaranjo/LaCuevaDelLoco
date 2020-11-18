package software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AgregarIvaController {

	private ManejadorEscenarios manejador;
	private Stage stage;

	@FXML
	private Button btn_agregar;

	@FXML
	private Button btn_cancelar;

	@FXML
	private TextField iva;

	@FXML
	private void initialize() {
		btn_agregar.setGraphic(new ImageView("file:src/software/cafeteria/images/agregarTipo.png"));
		btn_cancelar.setGraphic(new ImageView("file:src/software/cafeteria/images/cancelar.png"));
	}

	@FXML
	void agregarIva() {
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
			alert.show();
		}
	}

	@FXML
	void cancelar() {
		stage.close();
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
