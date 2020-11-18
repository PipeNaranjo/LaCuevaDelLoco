package software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AgregarTipoProductoController {

	private Stage stage;
	private ManejadorEscenarios manejador;

	@FXML
	private TextField agregarTipo;

	@FXML
	private Button btn_agregar;

	@FXML
	private Button btn_cancelar;
	
	
	@FXML
	public void initialize(){
		btn_agregar.setGraphic(new ImageView("file:src/software/cafeteria/images/agregarTipo.png"));
		btn_cancelar.setGraphic(new ImageView("file:src/software/cafeteria/images/cancelar.png"));
	}

	@FXML
	void agregarTipo() {
		boolean res = manejador.agregarTipoProducto(agregarTipo.getText());

		if (res) {
			Alert alert = new Alert(AlertType.INFORMATION, "El tipo de producto se agrego con exito", ButtonType.OK);
			alert.showAndWait();
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR,
					"El tipo de producto con el nombre " + agregarTipo.getText() + " ya se encuentra registrado.",
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