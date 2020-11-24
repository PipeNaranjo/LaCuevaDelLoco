package main.java.software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Factura1Controller {

	private ManejadorEscenarios manejador;
	private Stage stage;

	@FXML
	private Button btn_factura;

	@FXML
	private Button btn_cancelar;

	@FXML
	private Label valorTotal;

	@FXML
	public void initialize() {
		btn_factura.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/factura.png"));
		btn_cancelar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/cancelar.png"));
	}

	@FXML
	void cancelar() {
		stage.close();
		manejador.abrirCrearFactura();
	}

	@FXML
	void generarFactura() {
		manejador.adjuntarRecibo(manejador.getReciboTemp());
		stage.close();
		manejador.abrirCrearFactura();
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