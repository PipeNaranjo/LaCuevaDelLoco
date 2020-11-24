package main.java.software.cafeteria.controladores;

import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.software.cafeteria.delegado.ProductoObservable;
import main.java.software.cafeteria.entidades.ProductosVentas;
import main.java.software.cafeteria.entidades.Recibo;

@SuppressWarnings("restriction")
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

	private TableColumn<ProductoObservable, Void> mas;

	private TableColumn<ProductoObservable, Void> eliminarDelCarrito;

	@FXML
	private Button factura;

	@FXML
	private Button botonCarrito;

	@FXML
	private TextField busquedaProducto;

	@FXML
	private Button botonRegresar;

	@FXML
	private TextField cantidadBolsas;

	private int numeroBolsas;

	@FXML
	public void initialize() {

		numeroBolsas = 0;
		nombre1.setCellValueFactory(
				new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> nombre) {
						return nombre.getValue().getNombre();
					}
				});
		precio1.setCellValueFactory(
				new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> precio) {
						return precio.getValue().getPrecio();
					}
				});
		nombre2.setCellValueFactory(
				new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> nombre) {
						return nombre.getValue().getNombre();
					}
				});
		precio2.setCellValueFactory(
				new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> precio) {
						return precio.getValue().getPrecio();
					}
				});

		valorTotal.setText("0");

		mas = new TableColumn<ProductoObservable, Void>();
		mas.setMaxWidth(40);
		menos = new TableColumn<ProductoObservable, Void>();
		menos.setMaxWidth(40);
		agregarAlCarro = new TableColumn<ProductoObservable, Void>();

		eliminarDelCarrito = new TableColumn<ProductoObservable, Void>();

		generarBotones();

		ImageView imageView = new ImageView("file:src/main/java/software/cafeteria/images/carritoMercado.png");
		imagenCarrito.setGraphic(imageView);

		imageView = new ImageView("file:src/main/java/software/cafeteria/images/regresar.png");
		botonRegresar.setGraphic(imageView);

		imageView = new ImageView("file:src/main/java/software/cafeteria/images/vaciarCarrito.png");
		botonCarrito.setGraphic(imageView);

		imageView = new ImageView("file:src/main/java/software/cafeteria/images/confirmar.png");
		factura.setGraphic(imageView);

	}

	private void generarBotones() {
		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory1 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {

			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView(
							"file:src/main/java/software/cafeteria/images/agregarCarrito.png");
					private final Button btn = new Button("Agregar carrito", imageView);

					{
						btn.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {

								int indice = getIndex();
								ProductoObservable producto = productosInventarios.getItems().get(indice);
								ObservableList<ProductoObservable> productos = FXCollections.observableArrayList();

								boolean ward = true;

								for (int i = 0; i < carrito.getItems().size() && ward; i++) {
									if (carrito.getItems().get(i).getCodigoBarras().getValue()
											.equals(producto.getCodigoBarras().getValue())) {
										ward = false;
										int cantidad = Integer
												.parseInt(carrito.getItems().get(i).getCantidad().getValue());

										if (cantidad < carrito.getItems().get(i).getProductoInventario()
												.getCantidad()) {

											cantidad += 1;

											carrito.getItems().get(i)
													.setCantidad(new SimpleStringProperty(cantidad + ""));

											if (cantidad == carrito.getItems().get(i).getProductoInventario()
													.getCantidad()) {

												Alert alert = new Alert(AlertType.WARNING,
														"Las existencias se agotaron", ButtonType.OK);
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
												producto.getTipoProducto().getValue(), "1",
												producto.getPrecio().getValue(), producto.getCodigoBarras().getValue(),
												producto.getPresentacion().getValue(), producto.getEmpresa().getValue(),
												producto.getProductoInventario()));
									} else {
										Alert alert = new Alert(AlertType.WARNING, "Las existencias se agotaron",
												ButtonType.OK);
										alert.show();
									}
								}
								int valor = numeroBolsas * 50;
								for (int j = 0; j < carrito.getItems().size(); j++) {
									productos.add(carrito.getItems().get(j));
									valor += Integer.parseInt(carrito.getItems().get(j).getPrecio().getValue())
											* Integer.parseInt(carrito.getItems().get(j).getCantidad().getValue());
								}

								carrito.getItems().removeAll(carrito.getItems());
								carrito.getItems().addAll(productos);

								valorTotal.setText(valor + "");
								cambiarTotal();

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

		agregarAlCarro.setCellFactory(cellFactory1);

		productosInventarios.getColumns().add(agregarAlCarro);

		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory2 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView(
							new Image("file:src/main/java/software/cafeteria/images/disminuirCantidad.png"));
					private final Button btn = new Button("", imageView);

					{
						btn.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {

								int indice = getIndex();

								ObservableList<ProductoObservable> productos = FXCollections.observableArrayList();
								if (!carrito.getItems().get(indice).getCantidad().getValue().equals("1")) {

									int cantidad = Integer
											.parseInt(carrito.getItems().get(indice).getCantidad().getValue()) - 1;
									ProductoObservable producto = carrito.getItems().get(indice);
									producto.setCantidad(new SimpleStringProperty(cantidad + ""));

									int valor = numeroBolsas * 50;
									for (int j = 0; j < carrito.getItems().size(); j++) {
										productos.add(carrito.getItems().get(j));
										valor += Integer.parseInt(carrito.getItems().get(j).getPrecio().getValue())
												* Integer.parseInt(carrito.getItems().get(j).getCantidad().getValue());
									}

									carrito.getItems().removeAll(carrito.getItems());
									carrito.getItems().addAll(productos);

									valorTotal.setText(valor + "");
									cambiarTotal();

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

		menos.setCellFactory(cellFactory2);

		carrito.getColumns().add(menos);
		TableColumn<ProductoObservable, String> cantidad;
		cantidad = new TableColumn<ProductoObservable, String>("Cant");
		cantidad.setCellValueFactory(
				new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> cantidad) {
						return cantidad.getValue().getCantidad();
					}
				});
		cantidad.setMaxWidth(42);

		carrito.getColumns().add(cantidad);

		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory3 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView(
							new Image("file:src/main/java/software/cafeteria/images/aumentarCantidad.png"));
					private final Button btn = new Button("", imageView);

					{
						btn.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {

								ObservableList<ProductoObservable> productos = FXCollections.observableArrayList();
								ProductoObservable productoCarrito = carrito.getItems().get(getIndex());

								ProductoObservable producto = null;

								for (int i = 0; i < productosInventarios.getItems().size(); i++) {
									if (productoCarrito.getCodigoBarras().getValue().equals(
											productosInventarios.getItems().get(i).getCodigoBarras().getValue())) {

										producto = productosInventarios.getItems().get(i);
									}
								}

								if (producto != null) {
									int cantidadCarrito = Integer.parseInt(productoCarrito.getCantidad().getValue());

									int cantidad = producto.getProductoInventario().getCantidad();

									if (cantidadCarrito < cantidad) {
										cantidadCarrito += 1;

										productoCarrito.setCantidad(new SimpleStringProperty(cantidadCarrito + ""));

										int valor = numeroBolsas * 50;
										for (int j = 0; j < carrito.getItems().size(); j++) {
											productos.add(carrito.getItems().get(j));
											valor += Integer.parseInt(carrito.getItems().get(j).getPrecio().getValue())
													* Integer.parseInt(
															carrito.getItems().get(j).getCantidad().getValue());
										}

										carrito.getItems().removeAll(carrito.getItems());
										carrito.getItems().addAll(productos);

										valorTotal.setText(valor + "");
										cambiarTotal();

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
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView(
							new Image("file:src/main/java/software/cafeteria/images/eliminarCarrito.png"));
					private final Button btn = new Button("", imageView);

					{
						btn.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {

								int indice = getIndex();

								carrito.getItems().remove(indice);

								int valor = 0;
								for (int j = 0; j < carrito.getItems().size(); j++) {

									valor += Integer.parseInt(carrito.getItems().get(j).getPrecio().getValue())
											* Integer.parseInt(carrito.getItems().get(j).getCantidad().getValue());
								}
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
			if (!cantidadBolsas.getText().equals("")) {
				try {
					cantidad_bolsas = Integer.parseInt(cantidadBolsas.getText());
				} catch (NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR, "N�mero de bolsas inv�lido", ButtonType.OK);
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
				manejador.ventanaConfirmar(recibo);
			} else {
				manejador.ingresarEfectivo(recibo);

			}
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "El carrito esta vac�o", ButtonType.OK);
			alert.show();
		}

	}

	@FXML
	public void regresar() {

		Alert alert = new Alert(AlertType.CONFIRMATION, "�Seguro quieres salir?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> action = alert.showAndWait();
		if (action.get() == ButtonType.YES) {
			manejador.setReciboTemp(null);
			stage.close();
			manejador.ventanaPrincipal();

		} else if (action.get() == ButtonType.NO) {

		}
	}

	@FXML
	void cambiarTotal() {

		try {
			if (!(valorTotal.getText().equals("0"))) {

				int valor = Integer.parseInt(valorTotal.getText()) - (numeroBolsas * 50);

				if (cantidadBolsas.getText().equals("")) {
					numeroBolsas = 0;
				} else {
					numeroBolsas = Integer.parseInt(cantidadBolsas.getText());
				}
				System.out.println(numeroBolsas);
				int bolsas = numeroBolsas * 50;
				int valortotal = (valor + bolsas);
				valorTotal.setText(valortotal + "");

			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING, "Valor de bolsa inv�lido", ButtonType.OK);
			alert.show();
		}
	}

	@FXML
	void vaciarCarrito() {

		ObservableList<ProductoObservable> productos = FXCollections.observableArrayList();
		carrito.getItems().removeAll(carrito.getItems());
		carrito.getItems().addAll(productos);
		valorTotal.setText("0");
		manejador.setReciboTemp(null);
		cantidadBolsas.setText("");

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
			if (recibo.getTarjeta()) {
				tarjeta.setSelected(true);
			}
			cantidadBolsas.setText(recibo.getValorBolsas() / 50 + "");
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
