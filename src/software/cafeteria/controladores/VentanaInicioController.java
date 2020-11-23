package software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class VentanaInicioController {

	public ManejadorEscenarios manejador;
	public Stage stage;

	@FXML
	private Button informeFiscal;

	@FXML
	private Button realizarVenta;

	@FXML
	private Button gestionarInventario;

	@FXML
	public void initialize() {
		ImageView imageView1 = new ImageView("file:src/software/cafeteria/images/realizarVenta.png");
		realizarVenta.setGraphic(imageView1);

		ImageView imageView2 = new ImageView("file:src/software/cafeteria/images/realizarInformeFiscal.png");
		informeFiscal.setGraphic(imageView2);

		ImageView imageView3 = new ImageView("file:src/software/cafeteria/images/gestionarInventario.png");
		gestionarInventario.setGraphic(imageView3);
	}

	@FXML
	public void realizarInformeFiscal() {
		manejador.generarInformeFiscal();
	}

	@FXML
	public void abrirInventario() {
		manejador.abrirInventario();
		stage.close();
	}

	@FXML
	public void abrirVentanaRealizarVenta() {
		manejador.abrirCrearFactura();
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