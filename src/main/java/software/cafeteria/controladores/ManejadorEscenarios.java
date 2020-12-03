package main.java.software.cafeteria.controladores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.software.cafeteria.Main;
import main.java.software.cafeteria.delegado.Impressor;
import main.java.software.cafeteria.delegado.ProductoObservable;
import main.java.software.cafeteria.entidades.Empresa;
import main.java.software.cafeteria.entidades.InformeFiscal;
import main.java.software.cafeteria.entidades.ProductosInventario;
import main.java.software.cafeteria.entidades.ProductosVentas;
import main.java.software.cafeteria.entidades.Recibo;
import main.java.software.cafeteria.logica.Persistencia;
import main.java.software.cafeteria.logica.Tienda;

public class ManejadorEscenarios {

	private static final Logger LOGGER = Logger
			.getLogger(ManejadorEscenarios.class.getPackage().getName() + "." + ManejadorEscenarios.class.getName());
	private String errorDir = "Error Directorios";
	private String vacio = "|                                              |";
	private String inicio = " ---------------------------------------------- ";
	private ObservableList<ProductoObservable> productos;
	private ObservableList<String> iva;
	private ObservableList<String> tiposProductos;
	private ObservableList<String> tiposProductos1;
	private String todos = "Todos";
	private ObservableList<String> empresas;
	private Stage stage;
	private Tienda tienda;
	private String tipoSeleccionado;
	private InventarioController inventarioControlador;
	private AgregarProductoController agregarProductoControlador;
	private ModificarProductoController modificarProductoControlador;
	private String imagen = "file:src/main/java/software/cafeteria/images/icono_ventana.png";
	private Recibo reciboTemp;
	private String rutaRecibos = System.getProperty("user.home") + "/Desktop/Facturas/";
	private String rutaInformesFiscales = System.getProperty("user.home")
			+ "/Desktop/informesFiscales (Cuadre de Caja)/";
	private Impressor impressor;
	private int bolsas = 0;
	private String archivo = "src/cafeteria.dat";
	private boolean validar = false;

	public ManejadorEscenarios(Stage stage) {

		this.stage = stage;
		if (new File(archivo).exists()) {
			try {
				tienda = (Tienda) Persistencia.cargarObjeto(archivo);
			} catch (ClassNotFoundException e) {

				LOGGER.log(Level.WARNING, "Error Clase No Encontrada");
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, errorDir);
			}
		} else {
			tienda = new Tienda();
		}

		tipoSeleccionado = "Todos";

		impressor = new Impressor();
		verificarDirectorios();
		iva = listarIva();
		productos = listarProductos();
		empresas = listarEmpresas();
		tiposProductos = listarTipos();
		tiposProductos1 = listarTipos1();

		ventanaPrincipal();

	}

	public void verificarDirectorios() {
		File directorio = new File(rutaInformesFiscales);
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
		directorio = new File(rutaRecibos);

		if (!directorio.exists()) {
			directorio.mkdirs();
		}
	}

	public void ventanaPrincipal() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/inicio.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			stage = new Stage();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {

					Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro quieres salir de la aplicación?",
							ButtonType.YES, ButtonType.NO);
					Optional<ButtonType> action = alert.showAndWait();
					if (action.isPresent()) {
						if (action.get() == ButtonType.YES) {
							try {
								Persistencia.guardarObjetos(tienda, archivo);
								stage.close();
							} catch (IOException e) {
								LOGGER.log(Level.WARNING, errorDir);
							}
						} else if (action.get() == ButtonType.NO) {
							event.consume();
						}
					}

				}
			});

			stage.setResizable(false);
			stage.setTitle("Ventana Principal");
			Scene scene = new Scene(page);

			stage.setScene(scene);
			stage.getIcons().add(new Image(imagen));

			// se carga el controlador
			VentanaInicioController ventanaInicioController = loader.getController();
			ventanaInicioController.setManejador(this);
			ventanaInicioController.setStage(stage);

			// se crea el escenario
			stage.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}

	}

	public void ventanaConfirmarCompra() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/tarjeta.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setResizable(false);
			escenario.getIcons().add(new Image(imagen));
			escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {

					Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro quieres salir?", ButtonType.YES,
							ButtonType.NO);
					Optional<ButtonType> action = alert.showAndWait();
					if (action.isPresent()) {
						if (action.get() == ButtonType.YES) {
							abrirCrearFactura();
						} else if (action.get() == ButtonType.NO) {
							event.consume();
						}
					}

				}
			});
			escenario.setTitle("Ingresar Efectivo");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			Factura1Controller facturaControlador = loader.getController();
			facturaControlador.setManejador(this);
			facturaControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}

	}

	public void abrirCrearFactura() {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/venderProductos.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setTitle("Realizar Venta");
			Scene scene = new Scene(page);
			escenario.setResizable(false);
			escenario.setScene(scene);
			escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {

					Alert alert = new Alert(AlertType.CONFIRMATION, "Â¿Seguro quieres salir?\nSe perderan los datos",
							ButtonType.YES, ButtonType.NO);
					Optional<ButtonType> action = alert.showAndWait();
					if (action.isPresent()) {
						if (action.get() == ButtonType.YES) {
							reciboTemp = null;
							stage.close();
							ventanaPrincipal();

						} else if (action.get() == ButtonType.NO) {
							event.consume();
						}
					}

				}
			});
			escenario.getIcons().add(new Image(imagen));
			// se carga el controlador
			ReciboController facturaControlador = loader.getController();
			facturaControlador.setManejador(this);
			facturaControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}

	}

	public void abrirInventario() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/inventario.fxml"));
			BorderPane page = (BorderPane) loader.load();

			Stage escenario = new Stage();
			escenario.getIcons().add(new Image(imagen));
			escenario.setTitle("Inventario");
			escenario.setResizable(false);
			escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {

					ventanaPrincipal();

				}
			});
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			inventarioControlador = loader.getController();
			inventarioControlador.setManejador(this);
			inventarioControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
	}

	public void ventanaAgregarEmpresa() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/agregar_empresa.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setResizable(false);
			escenario.getIcons().add(new Image(imagen));
			escenario.setTitle("Agregar Empresa");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			AgregarEmpresaController agregarEmpresaControlador = loader.getController();
			agregarEmpresaControlador.setManejador(this);
			agregarEmpresaControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
	}

	public void ventanaAgregarTipo() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/agregar_tipo.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setResizable(false);
			escenario.getIcons().add(new Image(imagen));
			escenario.setTitle("Agregar Tipo de Producto");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			AgregarTipoProductoController agregarTipoProductoControlador = loader.getController();
			agregarTipoProductoControlador.setManejador(this);
			agregarTipoProductoControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
	}

	public void ventanaAgregarProducto() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/formulario_producto.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage agregar = new Stage();
			agregar.setTitle("Agregar Producto");
			Scene scene = new Scene(page);
			agregar.setScene(scene);
			agregar.setResizable(false);
			agregar.getIcons().add(new Image(imagen));

			// se carga el controlador
			agregarProductoControlador = loader.getController();
			agregarProductoControlador.setManejador(this);
			agregarProductoControlador.setStage(agregar);
			validar = true;
			// se crea el escenario
			agregar.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
	}

	public void ventanaModificarProducto(int index) {

		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/modificar_producto.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setResizable(false);
			escenario.getIcons().add(new Image(imagen));
			escenario.setTitle("Modificar un producto");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			modificarProductoControlador = loader.getController();
			modificarProductoControlador.setManejador(this, index);
			modificarProductoControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
	}

	public void abrirAgregarIva() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/agregar_iva.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setResizable(false);
			escenario.getIcons().add(new Image(imagen));
			escenario.setTitle("Agregar nuevo Iva");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			AgregarIvaController agregarIvaControlador = loader.getController();
			agregarIvaControlador.setManejador(this);
			agregarIvaControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
	}

	public void abrirIngresarEfectivo() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/efectivo.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
			escenario.setResizable(false);
			escenario.getIcons().add(new Image(imagen));
			escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {

					Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro quieres salir?", ButtonType.YES,
							ButtonType.NO);
					Optional<ButtonType> action = alert.showAndWait();
					if (action.isPresent()) {
						if (action.get() == ButtonType.YES) {
							abrirCrearFactura();
						} else if (action.get() == ButtonType.NO) {
							event.consume();
						}
					}

				}
			});
			escenario.setTitle("Ingresar Efectivo");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			FacturaController facturaControlador = loader.getController();
			facturaControlador.setManejador(this);
			facturaControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
	}

	public boolean agregarIva(int iva) {

		boolean retorno = tienda.getIva().contains(iva + "");
		if (!retorno) {
			tienda.getIva().add(iva + "");
			try {
				Persistencia.guardarObjetos(tienda, archivo);
			} catch (IOException e) {

				LOGGER.log(Level.WARNING, errorDir);
			}

		}

		if (validar) {
			agregarProductoControlador.getIva().getSelectionModel().select(iva + "");
		} else {
			modificarProductoControlador.getIva().getSelectionModel().select(iva + "");
		}

		return retorno;
	}

	public ObservableList<ProductoObservable> listarProductos() {

		productos = null;
		productos = FXCollections.observableArrayList();

		for (int i = 0; i < tienda.getInventario().getProductosI().size(); i++) {
			productos.add(new ProductoObservable(tienda.getInventario().getProductosI().get(i)));

		}

		return productos;

	}

	public ObservableList<String> listarIva() {
		iva = null;
		iva = FXCollections.observableArrayList();
		iva.add("Otro Iva");
		iva.add("5");
		iva.add("19");
		iva.add("Exento");
		for (int i = 0; i < tienda.getIva().size(); i++) {
			iva.add(tienda.getIva().get(i));
		}

		return iva;
	}

	public ObservableList<String> listarEmpresas() {

		empresas = null;
		empresas = FXCollections.observableArrayList();
		empresas.add("Otra empresa");
		for (int i = 0; i < tienda.getInventario().getEmpresas().size(); i++) {
			empresas.add(tienda.getInventario().getEmpresas().get(i).getNombre());
		}

		return empresas;
	}

	public ObservableList<String> listarTipos() {

		tiposProductos = null;
		tiposProductos = FXCollections.observableArrayList();
		tiposProductos.add("Otro tipo");
		for (int i = 0; i < tienda.getInventario().getProductosI().size(); i++) {
			if (!tiposProductos.contains(tienda.getInventario().getProductosI().get(i).getTipo())) {
				tiposProductos.add(tienda.getInventario().getProductosI().get(i).getTipo());
			}
		}

		return tiposProductos;
	}

	public ObservableList<String> listarTipos1() {

		tiposProductos1 = null;
		tiposProductos1 = FXCollections.observableArrayList();
		tiposProductos1.add(todos);
		for (int i = 0; i < tienda.getInventario().getProductosI().size(); i++) {
			if (!tiposProductos1.contains(tienda.getInventario().getProductosI().get(i).getTipo())) {
				tiposProductos1.add(tienda.getInventario().getProductosI().get(i).getTipo());
			}
		}

		return tiposProductos1;
	}

	public boolean eliminarProducto(int index) {

		int contador1 = 0;
		int contador2 = 0;

		String tipo = productos.get(index).getTipoProducto().getValue();
		String empresa = productos.get(index).getEmpresa().getValue();
		boolean res = tienda.getInventario().borrarProductoI(productos.get(index).getCodigoBarras().getValue());
		productos.remove(index);
		for (int i = 0; i < productos.size(); i++) {
			if (productos.get(i).getTipoProducto().getValue().equalsIgnoreCase(tipo)) {
				contador1++;
			}
			if (productos.get(i).getEmpresa().getValue().equalsIgnoreCase(empresa)) {
				contador2++;
			}
		}
		if (contador1 == 0) {
			tiposProductos1.remove(tipo);
			tiposProductos.remove(tipo);
		}
		if (contador2 == 0) {
			tienda.getInventario().borrarEmpresas(empresa);
			empresas.remove(empresa);
		}

		return res;

	}

	public boolean agregarProducto(ProductosInventario productoInventario) {

		boolean res = tienda.getInventario().agregarProducto(productoInventario);
		if (res) {
			if (tipoSeleccionado.equals(todos) || productoInventario.getTipo().equals(tipoSeleccionado)) {
				productos.add(new ProductoObservable(productoInventario));
			}
			if (!tiposProductos1.contains(productoInventario.getTipo())) {
				tiposProductos1.add(productoInventario.getTipo());
			}
		}
		try {
			Persistencia.guardarObjetos(tienda, archivo);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
		return res;

	}

	public boolean agregarEmpresa(String nombreEmpresa) {
		boolean res = tienda.getInventario().agregarEmpresa(new Empresa(nombreEmpresa));
		empresas.add(nombreEmpresa);
		if (validar) {
			agregarProductoControlador.getEmpresaDistri().getSelectionModel().select(nombreEmpresa);
		} else {
			modificarProductoControlador.getEmpresaDistri().getSelectionModel().select(nombreEmpresa);
		}
		return res;
	}

	public boolean agregarTipoProducto(String tipoProducto) {

		for (int i = 0; i < tiposProductos.size(); i++) {
			if (!(tiposProductos.get(i).equals(tipoProducto) && tiposProductos1.get(i).equals(tipoProducto))) {
				tiposProductos.add(tipoProducto);

				if (validar) {
					agregarProductoControlador.getTipoProducto().getSelectionModel().select(tipoProducto);
				} else {
					modificarProductoControlador.getTipoProducto().getSelectionModel().select(tipoProducto);
				}

				return true;
			}
		}
		return false;

	}

	public ObservableList<ProductoObservable> buscarProductoNombre(String cadena) {

		if (!cadena.equals("") || !cadena.equals(" ")) {
			ObservableList<ProductoObservable> nuevos = FXCollections.observableArrayList();

			for (int j = 0; j < productos.size(); j++) {
				String nombre = productos.get(j).getNombre().getValue();

				if (nombre.contains(cadena)) {
					nuevos.add(productos.get(j));
				}

			}

			return nuevos;
		}
		return productos;

	}

	public ObservableList<ProductoObservable> actualizarTablaTipo(String tipo) {

		tipoSeleccionado = tipo;
		productos = FXCollections.observableArrayList();

		if (tipo.equals(todos)) {
			for (int i = 0; i < tienda.getInventario().getProductosI().size(); i++) {

				productos.add(new ProductoObservable(tienda.getInventario().getProductosI().get(i)));

			}
		} else {
			for (int i = 0; i < tienda.getInventario().getProductosI().size(); i++) {
				if (tienda.getInventario().getProductosI().get(i).getTipo().equals(tipo)) {
					productos.add(new ProductoObservable(tienda.getInventario().getProductosI().get(i)));
				}
			}
		}

		return productos;
	}

	public boolean modificarProducto(ProductosInventario producto, ProductosInventario anterior) {

		boolean res = tienda.getInventario().modificarProducto(anterior, producto);
		try {
			actualizarTablaTipo(tipoSeleccionado);
		} catch (NullPointerException e) {
			LOGGER.log(Level.WARNING, "Error Elemento Nulo");
		}
		inventarioControlador.actualizarTabla();
		if (res && tiposProductos1.contains(producto.getTipo())) {

			tiposProductos1.add(producto.getTipo());

		}
		try {
			Persistencia.guardarObjetos(tienda, archivo);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
		return res;

	}

	public void adjuntarRecibo(Recibo recibo) {
		tienda.getRegistroVentas().adjuntarUnRecibo(recibo, tienda.getInventario());
		imprimirRecibo(recibo);

		try {
			Persistencia.guardarObjetos(tienda, archivo);

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}
		reciboTemp = null;
	}

	public void ingresarEfectivo(Recibo recibo) {
		reciboTemp = recibo;
		abrirIngresarEfectivo();
	}

	public void generarInformeFiscal() {
		InformeFiscal informe = tienda.getRegistroVentas().generarInformeFiscal();

		if (informe != null) {
			imprimirInformeFiscal(informe);
			imprimirCuadreDeCaja(informe);
			Alert alert = new Alert(AlertType.INFORMATION, "Informe registrado \nConsultelo en" + rutaInformesFiscales,
					ButtonType.OK);
			alert.show();

			try {
				Persistencia.guardarObjetos(tienda, archivo);
			} catch (IOException e) {

				LOGGER.log(Level.WARNING, errorDir);
			}

		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "No se encuentran recibos registrados", ButtonType.OK);
			alert.show();
		}

	}

	private void imprimirCuadreDeCaja(InformeFiscal informe) {

		ArrayList<String> renglones = new ArrayList<String>();
		String tabla = "|         ----------------------------------------------------------- ------------------ ---------------- ";
		renglones.add(
				" ------------------------------------------------------------------------------------------------------------------------ ");
		renglones.add(
				"|                                            INFORME FISCAL DE VENTAS DIARIAS                                            |");
		renglones.add(
				"|                                    CAFETERIA LA CUEVA DEL LOCO    ARMENIA-QUINDIO                                      |");
		renglones.add(
				"|                                              ARIGO COMERCIALIZADORA S.A.S.                                             |");

		GregorianCalendar fecha = informe.getFecha();
		String prefijo = "|           Fecha:              " + fecha.get(Calendar.YEAR) + "-" + fecha.get(Calendar.MONTH)
				+ "-" + fecha.get(Calendar.DAY_OF_MONTH);
		int tamano = renglones.get(0).length();
		String sufijo = "Consec. Z No:      " + informe.getNumeroInforme() + "         |";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - sufijo.length()) + sufijo);

		prefijo = "|           Número caja:        " + "1";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|           Registro Inicial:   " + informe.numeroPrimerRecibo();
		sufijo = "Registro Final:     " + informe.numeroUltimoRecibo() + "       |";
		renglones.add(prefijo + calcularEspacios(sufijo, tamano - prefijo.length()) + sufijo);

		prefijo = tabla;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|        |                    TIPO DE VENTA                          |    IVA O IPOC    |    VR. BASE    |";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = tabla;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|        |                   VENTAS EXENTAS                          |";

		sufijo = 0 + "";
		String ivaIpoc = "    IVA O IPOC    ";
		prefijo += calcularEspacios(sufijo, ivaIpoc.length() - 1) + sufijo;
		Integer[][] iva1 = informe.getIva();
		Integer[][] ganancias = informe.getGanancia();
		if (ganancias[0][0] == 0) {
			sufijo = ganancias[0][1] + "";
		} else {
			sufijo = 0 + "";
		}
		String var = "    VR. BASE    ";
		prefijo += " |" + calcularEspacios(sufijo, var.length() - 1) + sufijo + " |";
		prefijo += calcularEspacios("|", tamano - prefijo.length()) + "|";
		renglones.add(prefijo);

		prefijo = tabla;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|        |                VALORES BOLSAS PLÁSTICAS                   |";
		sufijo = 0 + "";
		prefijo += calcularEspacios(sufijo, ivaIpoc.length() - 1) + sufijo;
		sufijo = (informe.calcularValorBolsasEfectivo() + informe.calcularValorBolsasTarjeta()) + "";
		prefijo += " |" + calcularEspacios(sufijo, var.length() - 1) + sufijo + " |";
		prefijo += calcularEspacios("|", tamano - prefijo.length()) + "|";
		renglones.add(prefijo);

		prefijo = tabla;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|        |                VENTAS GRAVADAS AL 5%                      |";
		String sufijo1 = "";
		boolean ward = true;
		for (int i = 0; i < ganancias.length && ward; i++) {
			if (ganancias[i][0] == 5) {
				sufijo1 = "" + iva1[i][1];
				sufijo = "" + ganancias[i][1];
				ward = false;
			}
		}
		if (ward) {
			sufijo1 = 0 + "";
			sufijo = 0 + "";
		}

		prefijo += calcularEspacios(sufijo1, ivaIpoc.length() - 1) + sufijo1;
		prefijo += " |" + calcularEspacios(sufijo, var.length() - 1) + sufijo + " |";
		prefijo += calcularEspacios("|", tamano - prefijo.length()) + "|";
		renglones.add(prefijo);

		prefijo = tabla;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|        |               VENTAS GRAVADAS AL 19%                      |";
		sufijo1 = "";
		ward = true;
		for (int i = 0; i < ganancias.length && ward; i++) {
			if (ganancias[i][0] == 19) {
				sufijo1 = "" + iva1[i][1];
				sufijo = "" + ganancias[i][1];
				ward = false;
			}
		}
		if (ward) {
			sufijo1 = 0 + "";
			sufijo = 0 + "";
		}

		prefijo += calcularEspacios(sufijo1, ivaIpoc.length() - 1) + sufijo1;
		prefijo += " |" + calcularEspacios(sufijo, var.length() - 1) + sufijo + " |";
		prefijo += calcularEspacios("|", tamano - prefijo.length()) + "|";
		renglones.add(prefijo);

		prefijo = tabla;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|        |                   TOTAL DE VENTAS                         |";
		prefijo += calcularEspacios(informe.getTotalIva() + "", ivaIpoc.length() - 1) + informe.getTotalIva();
		prefijo += " |" + calcularEspacios(informe.getTotalGanancia() + "", var.length() - 1)
				+ (informe.getTotalGanancia() + informe.calcularValorBolsasEfectivo()
						+ informe.calcularValorBolsasTarjeta())
				+ " |";
		prefijo += calcularEspacios("|", tamano - prefijo.length()) + "|";
		renglones.add(prefijo);

		String tabla1 = "|       ------------------ ------------------ ------------------- ------------------- ---------------- ";
		prefijo = tabla;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = tabla1;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|      |     EFECTIVO     |      CHEQUES     |   TARJ. CREDITO   |   VIAS CREDITO   | TOTAL RECIBIDO |";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = tabla1;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		prefijo = "|      |"
				+ calcularEspacios((informe.getEfectivo() + informe.calcularValorBolsasEfectivo()) + "",
						"     EFECTIVO     ".length() - 1)
				+ (informe.getEfectivo() + informe.calcularValorBolsasEfectivo()) + " |";
		prefijo += calcularEspacios(0 + "", "      CHEQUES     ".length() - 1) + 0 + " |";
		prefijo += calcularEspacios((informe.getTarjeta() + informe.calcularValorBolsasTarjeta()) + "",
				"  TARJ. CREDITO   ".length() - 1) + (informe.getTarjeta() + informe.calcularValorBolsasTarjeta())
				+ " |";
		prefijo += calcularEspacios(0 + "", "   VIAS CREDITO   ".length() - 1) + 0 + "|";

		prefijo += calcularEspacios((informe.getTotalEnCaja() + informe.calcularValorBolsasTarjeta()) + "",
				" TOTAL RECIBIDO ".length() - 1) + (informe.getTotalEnCaja() + informe.calcularValorBolsasTarjeta())
				+ " |";

		prefijo += calcularEspacios("|", tamano - prefijo.length() - 1) + " |";
		renglones.add(prefijo);

		prefijo = tabla1;
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "|");

		renglones.add(
				"|                                                                                                                        |");

		renglones.add(
				"|                                                                                                                        |");
		renglones.add(
				" ------------------------------------------------------------------------------------------------------------------------ ");

		try {
			String nombreArchivo = "Cuadre " + informe.getNumeroInforme() + " " + fecha.get(Calendar.YEAR) + "-"
					+ (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + ".txt";

			Persistencia.escribirArchivo(rutaInformesFiscales + nombreArchivo, renglones);
			impressor.imprimirArchivo(rutaInformesFiscales + nombreArchivo);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}

	}

	public void imprimirRecibo(Recibo recibo) {

		ArrayList<String> renglones = new ArrayList<String>();

		renglones.add(inicio);
		renglones.add("|              COMEDAL S.A.S.                  |");
		renglones.add("|           CF/RIA LA CUEVA DEL LOCO           |");
		renglones.add("|               NIT: 901262014-5               |");
		renglones.add("|            RESPONSABLE IMP/CONSUMO           |");
		renglones.add(vacio);
		renglones.add(vacio);
		renglones.add("|             E IMP/VENTAS LEY 1943            |");
		renglones.add("|              CRA 19 # 1N 00 LC 5             |");
		renglones.add("|                ARMENIA QUINDIO               |");
		renglones.add("|                                              |");
		int tamano = renglones.get(0).length() - 1;
		GregorianCalendar fecha = recibo.getFecha();

		String renglon1 = "|  " + fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH) + " " + fecha.get(Calendar.HOUR) + ":" + fecha.get(Calendar.MINUTE)
				+ ":" + fecha.get(Calendar.SECOND);
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "|");
		renglon1 = recibo.getId() + "|";
		renglones.add("|" + calcularEspacios(renglon1, tamano) + renglon1);
		renglones.add(vacio);
		renglones.add(vacio);

		int limite = (renglones.get(0).length() * 60) / 100;

		ponerObjetos(limite, renglones, recibo, tamano);

		int valor = recibo.getValorBolsas();
		if (valor > 0) {
			String renglon = "|  BOLSA PLASTICA";
			renglon += calcularEspacios(renglon, limite);
			String cantidades = " -- " + valor / 50 + " " + valor;
			renglon += calcularEspacios(renglon, tamano - cantidades.length()) + cantidades + "|";
			renglones.add(renglon + calcularEspacios(renglon, tamano - renglon.length() - 1));
		}

		renglones.add(vacio);
		renglones.add(vacio);

		Integer[][] iva1 = recibo.getTotalIvaRecibo();
		Integer[][] ganancias = recibo.getTotalGananciaRecibo();
		String prefijo = "|  BASE GRAVADA";
		for (int i = 0; i < iva1.length; i++) {

			if (iva1[i][0] == 0) {
				renglon1 = ganancias[i][1] + "";
				renglones.add(
						prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "|");
				String iva2 = "|   EXENTOS:";
				renglon1 = iva1[i][1] + "";
				renglones.add(iva2 + calcularEspacios(renglon1, tamano - 1 - iva2.length()) + "$" + renglon1 + "|");
			} else {
				renglon1 = ganancias[i][1] + "";
				renglones.add(
						prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "|");
				String iva2 = "|   GRAVADOS " + iva1[i][0] + "%";
				renglon1 = iva1[i][1] + "";
				renglones.add(iva2 + calcularEspacios(renglon1, tamano - 1 - iva2.length()) + "$" + renglon1 + "|");
			}

		}

		renglones.add(vacio);
		String tarjeta = recibo.getTarjeta() ? "SI" : "NO";
		renglon1 = "|      PAGO CON TARJETA:    " + tarjeta;
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "|");
		renglon1 = "|      TOTAL:             " + recibo.getPrecioTotal();
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "|");
		renglon1 = "|      CAJA:              " + recibo.getEfectivoRegistrado();
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "|");
		renglon1 = "|      CAMBIO:            " + recibo.getCambio();
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "|");
		renglones.add(vacio);
		renglones.add(inicio);

		try {
			String nombreArchivo = "Recibo " + recibo.getId() + " " + fecha.get(Calendar.YEAR) + "-"
					+ (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + ".txt";

			Persistencia.escribirArchivo(rutaRecibos + nombreArchivo, renglones);
			impressor.imprimirArchivo(rutaRecibos + nombreArchivo);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}

	}

	public void imprimirInformeFiscal(InformeFiscal informe) {

		ArrayList<String> renglones = new ArrayList<String>();
		String cafeteria = "|           CF/RIA LA CUEVA DEL LOCO           |";
		String nit = "|               NIT: 901262014-5               |";
		String responsable = "|            RESPONSABLE IMP/CONSUMO           |";
		renglones.add(inicio);

		renglones.add("|                COMEDAL S.A.S.                |");
		renglones.add(cafeteria);
		renglones.add(nit);
		renglones.add(responsable);
		renglones.add(vacio);

		int tamano = renglones.get(0).length() - 1;
		GregorianCalendar fecha = new GregorianCalendar();

		String renglon1 = "|  Z      " + fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH) + "   " + fecha.get(Calendar.HOUR) + ":" + fecha.get(Calendar.MINUTE)
				+ ":" + fecha.get(Calendar.SECOND);
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "|");
		if (informe.getLista().size() >= 2) {
			renglon1 = informe.getLista().get(informe.getLista().size() - 2).getId() + "|";
		} else {
			renglon1 = informe.getLista().get(informe.getLista().size() - 1).getId() + "|";
		}
		renglones.add("|  Z" + calcularEspacios(renglon1, tamano - 2) + renglon1);
		renglones.add(vacio);
		renglones.add(vacio);
		renglones.add(vacio);
		renglones.add(vacio);
		renglones.add(vacio);
		renglones.add("|                COMEDAL S.A.S.                |");
		renglones.add(cafeteria);
		renglones.add(nit);
		renglones.add(responsable);
		renglones.add(vacio);

		GregorianCalendar fecha1 = new GregorianCalendar();

		renglon1 = "|   Z      " + fecha1.get(Calendar.YEAR) + "-" + (fecha1.get(Calendar.MONTH) + 1) + "-"
				+ fecha1.get(Calendar.DAY_OF_MONTH) + "   " + fecha1.get(Calendar.HOUR) + ":"
				+ fecha1.get(Calendar.MINUTE) + ":" + fecha1.get(Calendar.SECOND);
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "|");
		renglon1 = informe.getLista().get(informe.getLista().size() - 1).getId() + "|";
		renglones.add("|  Z" + calcularEspacios(renglon1, tamano - 2) + renglon1);

		renglones.add(vacio);
		renglones.add("|" + inicio.trim() + "|");
		renglon1 = "|  Z       Z DIARIO";
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "|");
		renglones.add("|" + inicio.trim() + "|");
		renglon1 = informe.getNumeroInforme();
		String prefijo = "|   Z       DEPTOS";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "|");
		renglones.add(vacio);

		Integer[][] iva2 = informe.getIva();
		for (int i = 0; i < iva2.length; i++) {

			if (iva2[i][0] == 0) {
				String iva1 = "|   EXENTOS:";
				renglon1 = iva2[i][1] + "";
				renglones.add(iva1 + calcularEspacios(renglon1, tamano - 1 - iva1.length()) + "$" + renglon1 + "|");
			} else {
				String iva1 = "|   GRAVADOS " + iva2[i][0] + "%";
				renglon1 = iva2[i][1] + "";
				renglones.add(iva1 + calcularEspacios(renglon1, tamano - 1 - iva1.length()) + "$" + renglon1 + "|");
			}

		}
		renglones.add(vacio);
		prefijo = "|   Z    ToT. FIJOS";
		renglon1 = informe.getNumeroInforme();
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "|");

		prefijo = "|   BRUTO ";
		renglon1 = informe.getTotalEnCaja() + "";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "|");

		prefijo = "|      EFECTIVO: ";
		renglon1 = (informe.getEfectivo() + informe.calcularValorBolsasEfectivo()) + "";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "|");

		prefijo = "|      TARJETA: ";
		renglon1 = (informe.getTarjeta() + informe.calcularValorBolsasTarjeta()) + "";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "|");

		renglones.add(vacio);

		Integer[][] ganancias = informe.getGanancia();
		prefijo = "|   BASE GRAVADA";
		for (int i = 0; i < iva2.length; i++) {

			if (iva2[i][0] == 0) {
				renglon1 = ganancias[i][1] + "";
				renglones.add(
						prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "|");
				String iva1 = "|     EXENTOS:";
				renglon1 = iva2[i][1] + "";
				renglones.add(iva1 + calcularEspacios(renglon1, tamano - 1 - iva1.length()) + "$" + renglon1 + "|");
			} else {
				renglon1 = ganancias[i][1] + "";
				renglones.add(
						prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "|");
				String iva1 = "|     GRAVADOS " + iva2[i][0] + "%";
				renglon1 = iva2[i][1] + "";
				renglones.add(iva1 + calcularEspacios(renglon1, tamano - 1 - iva1.length()) + "$" + renglon1 + "|");
			}

		}

		renglon1 = "$" + 0;
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "|");

		prefijo = "|   IMP/CSUMO 8%";
		renglon1 = "$" + 0;
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "|");

		renglon1 = informe.numeroPrimerRecibo() + "--->" + informe.numeroUltimoRecibo();
		renglones.add("|" + calcularEspacios(renglon1, tamano - 2) + renglon1 + " |");

		renglones.add("|" + inicio.trim() + "|");

		renglon1 = informe.getNumeroInforme();
		prefijo = "|   Z       FUNC LIBRES";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "|");

		prefijo = "|   CAJA           No";
		renglones.add(prefijo + calcularEspacios("1 ", tamano - prefijo.length()) + "1 |");

		renglon1 = informe.getTotalEnCaja() + informe.calcularValorBolsasTarjeta()
				+ informe.calcularValorBolsasTarjeta() + "|";
		renglones.add("|" + calcularEspacios(renglon1, tamano) + renglon1);

		renglones.add(vacio);
		renglones.add(inicio);

		try {
			String nombreArchivo = "Informe " + informe.getNumeroInforme() + " " + fecha.get(Calendar.YEAR) + "-"
					+ (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + ".txt";

			Persistencia.escribirArchivo(rutaInformesFiscales + nombreArchivo, renglones);
			impressor.imprimirArchivo(rutaInformesFiscales + nombreArchivo);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, errorDir);
		}

	}

	private void ponerObjetos(int limite, ArrayList<String> renglones, Recibo recibo, int tamano) {

		for (int i = 0; i < recibo.getProductosV().size(); i++) {
			StringBuilder renglon = new StringBuilder();
			renglon.append("| ");
			ProductosVentas producto = recibo.getProductosV().get(i);
			String[] partes = producto.getProducto().getNombre().split(" ");
			for (int j = 0; j < partes.length; j++) {

				if (j == partes.length - 1) {
					String cantidades = " -- " + producto.getCantidad() + " "
							+ producto.getProducto().getPrecio() * producto.getCantidad();
					String espacios = calcularEspacios(renglon.toString() + partes[j], tamano - cantidades.length());

					renglones.add(renglon.toString() + partes[j] + espacios + cantidades + "|");

				} else {
					if (renglon.toString().length() + partes[j].length() > limite) {

						renglones.add(renglon.toString() + calcularEspacios(renglon.toString(), tamano) + "|");
						renglon = new StringBuilder();
						renglon.append("| ");

					} else {

						renglon.append(partes[j] + " ");

					}
				}
			}
		}

	}

	public String calcularEspacios(String renglon, int limite) {
		StringBuilder espacios = new StringBuilder();

		for (int i = 0; i < limite - renglon.length(); i++) {
			espacios.append(" ");
		}

		return espacios.toString();
	}

	public String getTipoSeleccionado() {
		return tipoSeleccionado;
	}

	public void setTipoSeleccionado(String tipoSeleccionado) {
		this.tipoSeleccionado = tipoSeleccionado;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public Recibo getReciboTemp() {
		return reciboTemp;
	}

	public void setReciboTemp(Recibo reciboTemp) {
		this.reciboTemp = reciboTemp;
	}

	public void ventanaConfirmar(Recibo recibo) {
		reciboTemp = recibo;
		ventanaConfirmarCompra();

	}

	public int getBolsas() {
		return bolsas;
	}

	public void setBolsas(int bolsas) {
		this.bolsas = bolsas;
	}

	public boolean isValidar() {
		return validar;
	}

	public void setValidar(boolean validar) {
		this.validar = validar;
	}

	public AgregarProductoController getAgregarProductoControlador() {
		return agregarProductoControlador;
	}

	public void setAgregarProductoControlador(AgregarProductoController agregarProductoControlador) {
		this.agregarProductoControlador = agregarProductoControlador;
	}

	public ModificarProductoController getModificarProductoControlador() {
		return modificarProductoControlador;
	}

	public void setModificarProductoControlador(ModificarProductoController modificarProductoControlador) {
		this.modificarProductoControlador = modificarProductoControlador;
	}

}
