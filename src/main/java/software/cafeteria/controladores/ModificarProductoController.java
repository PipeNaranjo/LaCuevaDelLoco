package main.java.software.cafeteria.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.software.cafeteria.delegado.ProductoObservable;
import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.Producto;
import main.java.software.cafeteria.entidades.ProductosInventario;

public class ModificarProductoController {

	private ManejadorEscenarios manejador;

	private Stage stage;

	private int index;

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
	private ComboBox<String> tipoProducto;

	@FXML
	private ComboBox<String> empresaDistri;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnModificar;

	@FXML
	public void initialize() {
		btnCancelar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/cancelar.png"));
		btnModificar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/modificarProducto.png"));
	}

	@FXML
	void verificarTipo() {

		String tipo = tipoProducto.getSelectionModel().getSelectedItem();
		if (tipo != null && tipo.equals("Otro tipo")) {

			manejador.ventanaAgregarTipo();
			tipoProducto.setItems(manejador.listarTipos());

		}
	}

	@FXML
	void cancelar() {
		stage.close();
	}

	@FXML
	void modificarProducto() {

		boolean validar = true;

		if (!(codigoBarras.getText().equals("") || nombreProducto.getText().equals("")
				|| costoProducto.getText().equals("") || presentacionProducto.getText().equals("")
				|| precioProducto.getText().equals("") || cantidad.getText().equals(""))) {

			String codigoBarras1 = codigoBarras.getText();
			String nombreProducto1 = nombreProducto.getText();
			int costoProducto1 = 0;
			try {
				costoProducto1 = Integer.parseInt(costoProducto.getText());
			} catch (NumberFormatException e) {
				validar = false;
				Alert alert = new Alert(AlertType.ERROR, "Valor no numérico \nCampo costo del producto", ButtonType.OK);
				alert.showAndWait();
			}
			int presentacionProducto1 = 0;
			if (validar) {
				try {
					presentacionProducto1 = Integer.parseInt(presentacionProducto.getText());
				} catch (NumberFormatException e) {
					validar = false;
					Alert alert = new Alert(AlertType.ERROR, "Valor no numérico \nCampo presentación del producto",
							ButtonType.OK);
					alert.showAndWait();

				}
			}
			int precioProducto1 = 0;
			if (validar) {
				try {
					precioProducto1 = Integer.parseInt(precioProducto.getText());
				} catch (NumberFormatException e) {
					validar = false;
					Alert alert = new Alert(AlertType.ERROR, "Valor no numérico \nCampo precio del producto",
							ButtonType.OK);
					alert.showAndWait();

				}
			}
			int iva1 = -1;
			String seleccion = iva.getSelectionModel().getSelectedItem();
			if (seleccion != null && validar) {
				if (seleccion.equals("Exento")) {
					iva1 = 0;
				} else {
					iva1 = Integer.parseInt(seleccion);
				}
			} else {
				validar = false;
				Alert alert = new Alert(AlertType.ERROR, "Seleccione un valor en Iva", ButtonType.OK);
				alert.showAndWait();
			}
			String tipoProducto1 = tipoProducto.getSelectionModel().getSelectedItem();
			if (tipoProducto1 == null && validar) {
				validar = false;
				Alert alert = new Alert(AlertType.ERROR, "Seleccione un valor en tipo Producto", ButtonType.OK);
				alert.showAndWait();
			}
			int cantidad1 = 0;
			if (validar) {
				try {
					cantidad1 = Integer.parseInt(cantidad.getText());
				} catch (NumberFormatException e) {
					validar = false;
					Alert alert = new Alert(AlertType.ERROR, "Valor no num�rico \nCampo cantidad del producto",
							ButtonType.OK);
					alert.showAndWait();
				}
			}
			String empresa = empresaDistri.getSelectionModel().getSelectedItem();

			if (empresa == null && validar) {
				validar = false;
				Alert alert = new Alert(AlertType.ERROR, "Seleccione un valor en empresa", ButtonType.OK);
				alert.showAndWait();
			}
			ProductoObservable productoAnterior = manejador.listarProductos().get(index);

			if (validar) {
				ProductosInventario producto = new ProductosInventario(new Producto(codigoBarras1, nombreProducto1,
						new Empresa(empresa), presentacionProducto1, iva1, costoProducto1, precioProducto1), cantidad1,
						tipoProducto1);
				boolean respuesta = manejador.modificarProducto(producto, productoAnterior.getProductoInventario());
				if (respuesta) {
					Alert alert = new Alert(AlertType.INFORMATION, "Se modificó con exito el Producto", ButtonType.OK);
					alert.showAndWait();
					stage.close();
				} else {
					Alert alert = new Alert(AlertType.ERROR, "El código de barras ya existe", ButtonType.OK);
					alert.showAndWait();
				}
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR, "Ingrese valores en todos los campos", ButtonType.OK);
			alert.showAndWait();
		}

	}

	@FXML
	void verificarEmpresa() {
		String empresa = empresaDistri.getSelectionModel().getSelectedItem();
		if (empresa != null && empresa.equals("Otra empresa")) {

			manejador.ventanaAgregarEmpresa();
			empresaDistri.setItems(manejador.listarEmpresas());

		}
	}

	@FXML
	public void verificarIva() {
		String iva1 = iva.getSelectionModel().getSelectedItem();
		if (iva1 != null && iva1.equals("Otro Iva")) {

			manejador.abrirAgregarIva();

			iva.setItems(manejador.listarIva());

		}
	}

	public void actualiarCampos() {

		codigoBarras.setText(manejador.listarProductos().get(index).getCodigoBarras().getValue());
		nombreProducto.setText(manejador.listarProductos().get(index).getNombre().getValue());
		costoProducto.setText(manejador.listarProductos().get(index).getCosto().getValue());
		presentacionProducto.setText(manejador.listarProductos().get(index).getPresentacion().getValue());
		precioProducto.setText(manejador.listarProductos().get(index).getPrecio().getValue());
		iva.getSelectionModel().select((manejador.listarProductos().get(index).getIva().getValue()));
		cantidad.setText(manejador.listarProductos().get(index).getCantidad().getValue());
		tipoProducto.getSelectionModel().select(manejador.listarProductos().get(index).getTipoProducto().getValue());
		empresaDistri.getSelectionModel().select(manejador.listarProductos().get(index).getEmpresa().getValue());

	}

	public ManejadorEscenarios getManejador() {
		return manejador;
	}

	public void setManejador(ManejadorEscenarios manejador, int index) {
		this.manejador = manejador;
		this.index = index;
		tipoProducto.setItems(manejador.listarTipos());
		empresaDistri.setItems(manejador.listarEmpresas());
		iva.setItems(manejador.listarIva());
		actualiarCampos();
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ComboBox<String> getIva() {
		return iva;
	}

	public void setIva(ComboBox<String> iva) {
		this.iva = iva;
	}

	public ComboBox<String> getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(ComboBox<String> tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public ComboBox<String> getEmpresaDistri() {
		return empresaDistri;
	}

	public void setEmpresaDistri(ComboBox<String> empresaDistri) {
		this.empresaDistri = empresaDistri;
	}

}
