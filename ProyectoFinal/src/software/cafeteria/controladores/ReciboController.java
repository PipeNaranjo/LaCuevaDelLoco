package software.cafeteria.controladores;

import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import software.cafeteria.delegado.ProductoObservable;
import software.cafeteria.entidades.ProductosVentas;
import software.cafeteria.entidades.Recibo;

public class ReciboController {

	private ManejadorEscenarios manejador;

	private Stage stage;

	@FXML
	private Label imagenCarrito;

	@FXML
	private TableView<ProductoObservable> carrito;

	@FXML
	private TableColumn<ProductoObservable, String> nombre2;

	@FXML
	private TableColumn<ProductoObservable, String> precio2;

	private TableColumn<ProductoObservable, Void> agregarAlCarro;

	@FXML
	private Label valorTotal;

	@FXML
	private RadioButton tarjeta;

	@FXML
	private TableView<ProductoObservable> productosInventarios;

	@FXML
	private TableColumn<ProductoObservable, String> nombre1;

	@FXML
	private TableColumn<ProductoObservable, String> precio1;

	private TableColumn<ProductoObservable, Void> menos;

	private TableColumn<ProductoObservable, String> cantidad;

	private TableColumn<ProductoObservable, Void> mas;

	private TableColumn<ProductoObservable, Void> eliminarDelCarrito;

	@FXML
	private Button btn_factura;

	@FXML
	private Button btn_carrito;

	@FXML
	private TextField busquedaProducto;

	@FXML
	private Button btn_regresar;

	@FXML
	private TextField cant_bolsas;

	@FXML
	public void initialize() {

		nombre1.setCellValueFactory(nombre -> nombre.getValue().getNombre());
		precio1.setCellValueFactory(precio -> precio.getValue().getPrecio());
		nombre2.setCellValueFactory(nombre -> nombre.getValue().getNombre());
		precio2.setCellValueFactory(precio -> precio.getValue().getPrecio());

		valorTotal.setText("0");

		mas = new TableColumn<ProductoObservable, Void>();
		mas.setMaxWidth(40);
		menos = new TableColumn<ProductoObservable, Void>();
		menos.setMaxWidth(40);
		agregarAlCarro = new TableColumn<ProductoObservable, Void>();

		eliminarDelCarrito = new TableColumn<ProductoObservable, Void>();

		generarBotones();

		ImageView imageView = new ImageView("file:src/software/cafeteria/images/carritoMercado.png");
		imagenCarrito.setGraphic(imageView);

		imageView = new ImageView("file:src/software/cafeteria/images/regresar.png");
		btn_regresar.setGraphic(imageView);

		imageView = new ImageView("file:src/software/cafeteria/images/vaciarCarrito.png");
		btn_carrito.setGraphic(imageView);

		imageView = new ImageView("file:src/software/cafeteria/images/confirmar.png");
		btn_factura.setGraphic(imageView);

	}

	private void generarBotones() {
		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory1 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {
			@Override
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView("file:src/software/cafeteria/images/agregarCarrito.png");
					private final Button btn = new Button("Agregar carrito", imageView);

					{
						btn.setOnAction((ActionEvent event) -> {

							int indice = this.getIndex();
							ProductoObservable producto = productosInventarios.getItems().get(indice);
							ObservableList<ProductoObservable> productos = FXCollections.observableArrayList();

							boolean ward = true;

							for (int i = 0; i < carrito.getItems().size() && ward; i++) {
								if (carrito.getItems().get(i).getCodigoBarras().getValue()
										.equals(producto.getCodigoBarras().getValue())) {
									ward = false;
									int cantidad = Integer.parseInt(carrito.getItems().get(i).getCantidad().getValue());

									if (cantidad < carrito.getItems().get(i).getProductoInventario().getCantidad()) {

										cantidad += 1;

										carrito.getItems().get(i).setCantidad(new SimpleStringProperty(cantidad + ""));

										if (cantidad == carrito.getItems().get(i).getProductoInventario()
												.getCantidad()) {

											Alert alert = new Alert(AlertType.WARNING, "Las existencias se agotaron",
													ButtonType.OK);
											alert.show();

										}
									} else {
										Alert alert = new Alert(AlertType.WARNING, "Las existencias se agotaron",
												ButtonType.OK);
										alert.show();
									}

								}
							}

							if (ward) {

								if (producto.getProductoInventario().getCantidad() != 0) {
									carrito.getItems().add(new ProductoObservable(producto.getNombre().getValue(),
											producto.getCosto().getValue(), producto.getIva().getValue(),
											producto.getTipoProducto().getValue(), "1", producto.getPrecio().getValue(),
											producto.getCodigoBarras().getValue(),
											producto.getPresentacion().getValue(), producto.getEmpresa().getValue(),
											producto.getProductoInventario()));
								} else {
									Alert alert = new Alert(AlertType.WARNING, "Las existencias se agotaron",
											ButtonType.OK);
									alert.show();
								}
							}
							double valor = 0;
							for (int j = 0; j < carrito.getItems().size(); j++) {
								productos.add(carrito.getItems().get(j));
								valor += Integer.parseInt(carrito.getItems().get(j).getPrecio().getValue())
										* Integer.parseInt(carrito.getItems().get(j).getCantidad().getValue());
							}

							carrito.getItems().removeAll(carrito.getItems());
							carrito.getItems().addAll(productos);

							valorTotal.setText(valor + "");

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}

				};
				return cell;
			}
		};

		agregarAlCarro.setCellFactory(cellFactory1);

		productosInventarios.getColumns().add(agregarAlCarro);

		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory2 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {
			@Override
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView(
							new Image("file:src/software/cafeteria/images/disminuirCantidad.png"));
					private final Button btn = new Button("", imageView);

					{
						btn.setOnAction((ActionEvent event) -> {

							int indice = this.getIndex();

							ObservableList<ProductoObservable> productos = FXCollections.observableArrayList();
							if (!carrito.getItems().get(indice).getCantidad().getValue().equals("1")) {

								int cantidad = Integer.parseInt(carrito.getItems().get(indice).getCantidad().getValue())
										- 1;
								ProductoObservable producto = carrito.getItems().get(indice);
								producto.setCantidad(new SimpleStringProperty(cantidad + ""));

								double valor = 0;
								for (int j = 0; j < carrito.getItems().size(); j++) {
									productos.add(carrito.getItems().get(j));
									valor += Integer.parseInt(carrito.getItems().get(j).getPrecio().getValue())
											* Integer.parseInt(carrito.getItems().get(j).getCantidad().getValue());
								}

								carrito.getItems().removeAll(carrito.getItems());
								carrito.getItems().addAll(productos);

								valorTotal.setText(valor + "");

							}

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		menos.setCellFactory(cellFactory2);

		carrito.getColumns().add(menos);

		cantidad = new TableColumn<ProductoObservable, String>("Cant");
		cantidad.setCellValueFactory(cantidad -> cantidad.getValue().getCantidad());
		cantidad.setMaxWidth(42);

		carrito.getColumns().add(cantidad);

		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory3 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {
			@Override
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView(
							new Image("file:src/software/cafeteria/images/aumentarCantidad.png"));
					private final Button btn = new Button("", imageView);

					{
						btn.setOnAction((ActionEvent event) -> {

							ObservableList<ProductoObservable> productos = FXCollections.observableArrayList();
							ProductoObservable productoCarrito = carrito.getItems().get(this.getIndex());

							ProductoObservable producto = null;

							for (int i = 0; i < productosInventarios.getItems().size(); i++) {
								if (productoCarrito.getCodigoBarras().getValue()
										.equals(productosInventarios.getItems().get(i).getCodigoBarras().getValue())) {

									producto = productosInventarios.getItems().get(i);
								}
							}

							if (producto != null) {
								int cantidadCarrito = Integer.parseInt(productoCarrito.getCantidad().getValue());

								int cantidad = producto.getProductoInventario().getCantidad();

								if (cantidadCarrito < cantidad) {
									cantidadCarrito += 1;

									productoCarrito.setCantidad(new SimpleStringProperty(cantidadCarrito + ""));

									double valor = 0;
									for (int j = 0; j < carrito.getItems().size(); j++) {
										productos.add(carrito.getItems().get(j));
										valor += Integer.parseInt(carrito.getItems().get(j).getPrecio().getValue())
												* Integer.parseInt(carrito.getItems().get(j).getCantidad().getValue());
									}

									carrito.getItems().removeAll(carrito.getItems());
									carrito.getItems().addAll(productos);

									valorTotal.setText(valor + "");

									if (cantidadCarrito == cantidad) {
										Alert alert = new Alert(AlertType.WARNING, "Las existencias se agotaron",
												ButtonType.OK);
										alert.show();
									}

								} else {
									Alert alert = new Alert(AlertType.WARNING, "Las existencias se agotaron",
											ButtonType.OK);
									alert.show();
								}
							}

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		mas.setCellFactory(cellFactory3);

		carrito.getColumns().add(mas);

		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory4 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {
			@Override
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView(
							new Image("file:src/software/cafeteria/images/eliminarCarrito.png"));
					private final Button btn = new Button("", imageView);

					{
						btn.setOnAction((ActionEvent event) -> {

							int indice = this.getIndex();

							carrito.getItems().remove(indice);

							double valor = 0;
							for (int j = 0; j < carrito.getItems().size(); j++) {

								valor += Integer.parseInt(carrito.getItems().get(j).getPrecio().getValue())
										* Integer.parseInt(carrito.getItems().get(j).getCantidad().getValue());
							}
							valorTotal.setText(valor + "");

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		eliminarDelCarrito.setCellFactory(cellFactory4);
		eliminarDelCarrito.setMaxWidth(40);

		carrito.getColumns().add(eliminarDelCarrito);
	}

	@FXML
	void buscarProducto() {

		productosInventarios.setItems(manejador.buscarProductoNombre(busquedaProducto.getText()));

	}

	@FXML
	void generarFactura() {
		if (carrito.getItems().size() > 0) {
			boolean seleccion = false;
			if (tarjeta.isSelected()) {
				seleccion = true;
			}
			int cantidad_bolsas = 0;
			if (!cant_bolsas.getText().equals("")) {
				try {
					cantidad_bolsas = Integer.parseInt(cant_bolsas.getText());
				} catch (NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR, "Número de bolsas inválido", ButtonType.OK);
					alert.show();
				}
			}
			Recibo recibo = new Recibo(seleccion);
			for (int i = 0; i < carrito.getItems().size(); i++) {
				recibo.agregarProductos(carrito.getItems().get(i).getProductoInventario(),
						Integer.parseInt(carrito.getItems().get(i).getCantidad().getValue()));
			}
			recibo.setValorBolsas(cantidad_bolsas * 50);
			if (seleccion) {
				manejador.adjuntarRecibo(recibo);
				manejador.abrirCrearFactura();
				stage.close();
			} else {
				manejador.ingresarEfectivo(recibo);

			}
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "El carrito esta vacío", ButtonType.OK);
			alert.show();
		}

	}

	@FXML
	public void regresar() {

		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro quieres salir?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> action = alert.showAndWait();
		if (action.get() == ButtonType.YES) {
			manejador.setReciboTemp(null);
			stage.close();
			manejador.ventanaPrincipal();

		} else if (action.get() == ButtonType.NO) {

		}
	}

	@FXML
	void vaciarCarrito() {

		ObservableList<ProductoObservable> productos = FXCollections.observableArrayList();
		carrito.getItems().removeAll(carrito.getItems());
		carrito.getItems().addAll(productos);
		valorTotal.setText("0");
		manejador.setReciboTemp(null);
		cant_bolsas.setText("");

	}

	public ManejadorEscenarios getManejador() {
		return manejador;
	}

	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
		productosInventarios.setItems(manejador.listarProductos());
		if (manejador.getReciboTemp() != null) {
			Recibo recibo = manejador.getReciboTemp();

			for (int i = 0; i < recibo.getProductosV().size(); i++) {
				ProductosVentas producto = recibo.getProductosV().get(i);
				ProductoObservable productoO = new ProductoObservable(manejador.getTienda().getInventario()
						.obtenerproductoI(producto.getProducto().getCodigoDeBarras()));
				productoO.setCantidad(new SimpleStringProperty(producto.getCantidad() + ""));
				carrito.getItems().add(productoO);

			}
			cant_bolsas.setText(recibo.getValorBolsas() / 50 + "");
			valorTotal.setText(recibo.getPrecioTotal() + "");
		}

	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public TableView<ProductoObservable> getCarrito() {
		return carrito;
	}

	public void setCarrito(TableView<ProductoObservable> carrito) {
		this.carrito = carrito;
	}

}
