package software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FacturaController {

	private ManejadorEscenarios manejador;
	private Stage stage;

	@FXML
	private Button btn_factura;

	@FXML
	private Button btn_cancelar;

	@FXML
	private Label valorTotal;

	@FXML
	private Label cambio;

	@FXML
	private TextField efectivo;

	@FXML
	private void initialize() {
		btn_factura.setGraphic(new ImageView("file:src/software/cafeteria/images/factura.png"));
		btn_cancelar.setGraphic(new ImageView("file:src/software/cafeteria/images/cancelar.png"));
	}

	@FXML
	void calcularCambio() {
		try {
			int valorEfectivo = Integer.parseInt(efectivo.getText());
			cambio.setText((valorEfectivo - Integer.parseInt(valorTotal.getText())) + "");
		} catch (NumberFormatException e) {

		}
	}

	@FXML
	void cancelar() {
		stage.close();
		manejador.abrirCrearFactura();
	}

	@FXML
	void generarFactura() {
		try {
			if (!efectivo.getText().equals("")) {
				if (Integer.parseInt(cambio.getText()) > 0) {
					manejador.getReciboTemp().setEfectivoRegistrado(Integer.parseInt(efectivo.getText()));
					manejador.adjuntarRecibo(manejador.getReciboTemp());
					Alert alert = new Alert(AlertType.INFORMATION, "Venta Realizada", ButtonType.OK);
					alert.showAndWait();
					stage.close();
					manejador.abrirCrearFactura();
				} else {
					Alert alert = new Alert(AlertType.WARNING, "Cambio Negativo", ButtonType.OK);
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR, "Efectivo vacío", ButtonType.OK);
				alert.showAndWait();

			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR, "Dato no numérico", ButtonType.OK);
			alert.showAndWait();

		}
	}

	public ManejadorEscenarios getManejador() {
		return manejador;

	}

	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
		valorTotal.setText(manejador.getReciboTemp().getPrecioTotal() + "");
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
