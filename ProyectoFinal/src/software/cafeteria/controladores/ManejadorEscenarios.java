package software.cafeteria.controladores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import software.cafeteria.Main;
import software.cafeteria.delegado.ProductoObservable;
import software.cafeteria.entidades.Empresa;
import software.cafeteria.entidades.InformeFiscal;
import software.cafeteria.entidades.ProductosInventario;
import software.cafeteria.entidades.ProductosVentas;
import software.cafeteria.entidades.Recibo;
import software.cafeteria.logica.Persistencia;
import software.cafeteria.logica.Tienda;

public class ManejadorEscenarios {

	private ObservableList<ProductoObservable> productos;
	private ObservableList<String> iva;
	private ObservableList<String> tiposProductos;
	private ObservableList<String> tiposProductos1;
	private ObservableList<String> empresas;
	private Stage stage;
	private Stage agregar;
	private Tienda tienda;
	private String tipoSeleccionado;
	private InventarioController inventarioControlador;
	private VentanaInicioController ventanaInicioController;
	private ReciboController facturaControlador;
	private Recibo reciboTemp;
	public static InformeFiscal informe;
	private String rutaRecibos = System.getProperty("user.home") + "/Desktop/recibos/";
	private String rutaInformesFiscales = System.getProperty("user.home") + "/Desktop/informesFiscales/";

	private String archivo = "src/cafeteria.dat";

	public ManejadorEscenarios(Stage stage) {

		this.stage = stage;
		if (new File(archivo).exists()) {
			try {
				tienda = (Tienda) Persistencia.cargarObjeto(archivo);
			} catch (ClassNotFoundException | IOException e) {

				e.printStackTrace();
			}
		} else {
			tienda = new Tienda();
		}
		iva = listarIva();
		productos = listarProductos();
		empresas = listarEmpresas();
		tiposProductos1 = listarTipos();
		tiposProductos1 = listarTipos1();

		tipoSeleccionado = "Todos";

		ventanaPrincipal();

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
				@Override
				public void handle(WindowEvent event) {

					Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro quieres salir de la aplicación?",
							ButtonType.YES, ButtonType.NO);
					Optional<ButtonType> action = alert.showAndWait();
					if (action.get() == ButtonType.YES) {
						try {
							Persistencia.guardarObjetos(tienda, archivo);
							stage.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (action.get() == ButtonType.NO) {
						event.consume();
					}

				}
			});
			stage.setTitle("Ventana Principal");
			Scene scene = new Scene(page);
			stage.setScene(scene);

			// se carga el controlador
			ventanaInicioController = loader.getController();
			ventanaInicioController.setManejador(this);
			ventanaInicioController.setStage(stage);

			// se crea el escenario
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
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

			escenario.setScene(scene);
			escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {

					Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro quieres salir?\nSe perderan los datos",
							ButtonType.YES, ButtonType.NO);
					Optional<ButtonType> action = alert.showAndWait();
					if (action.get() == ButtonType.YES) {
						reciboTemp = null;
						stage.close();
						ventanaPrincipal();

					} else if (action.get() == ButtonType.NO) {
						event.consume();
					}

				}
			});

			// se carga el controlador
			facturaControlador = loader.getController();
			facturaControlador.setManejador(this);
			facturaControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void abrirInventario() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/inventario.fxml"));
			BorderPane page = (BorderPane) loader.load();

			Stage escenario = new Stage();
			escenario.setTitle("Inventario");
			escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	public void ventanaAgregarProducto() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/formulario_producto.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			agregar = new Stage();
			// agregar.setTitle("Agregar Producto");
			Scene scene = new Scene(page);
			agregar.setScene(scene);

			// se carga el controlador
			AgregarProductoController agregarProductoControlador = loader.getController();
			agregarProductoControlador.setManejador(this);
			agregarProductoControlador.setStage(agregar);

			// se crea el escenario
			agregar.show();

		} catch (IOException e) {
			e.printStackTrace();
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
			escenario.setTitle("Modificar un producto");
			Scene scene = new Scene(page);
			escenario.setScene(scene);

			// se carga el controlador
			ModificarProductoController modificarProductoControlador = loader.getController();
			modificarProductoControlador.setManejador(this, index);
			modificarProductoControlador.setStage(escenario);

			// se crea el escenario
			escenario.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void abrirAgregarIva() {
		try {

			// se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/modificar_producto.fxml"));
			BorderPane page = (BorderPane) loader.load();

			// se crea el escenario
			Stage escenario = new Stage();
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
			e.printStackTrace();
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
			escenario.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {

					Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro quieres salir?", ButtonType.YES,
							ButtonType.NO);
					Optional<ButtonType> action = alert.showAndWait();
					if (action.get() == ButtonType.YES) {
						abrirCrearFactura();
					} else if (action.get() == ButtonType.NO) {
						event.consume();
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
			e.printStackTrace();
		}
	}

	public boolean agregarIva(int iva) {

		boolean retorno = tienda.getIva().contains(iva + "");
		if (retorno) {
			tienda.getIva().add(iva + "");
			try {
				Persistencia.guardarObjetos(tienda, archivo);
			} catch (IOException e) {

				e.printStackTrace();
			}
			return retorno;
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
		tiposProductos1.add("Todos");
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

	public boolean agregarProducto(String codigoBarras, String nombre, int costo, int presentacion, int precio, int iva,
			String tipo, int cantidad, String empresa) {

		ProductosInventario productoInventario = new ProductosInventario(codigoBarras, nombre, new Empresa(empresa),
				presentacion, iva, costo, cantidad, tipo, precio);
		boolean res = tienda.getInventario().agregarProducto(productoInventario);
		if (res) {
			if (tipoSeleccionado.equals("Todos")) {
				productos.add(new ProductoObservable(productoInventario));
			} else if (productoInventario.getTipo().equals(tipoSeleccionado)) {
				productos.add(new ProductoObservable(productoInventario));
			}
		}
		try {
			Persistencia.guardarObjetos(tienda, archivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;

	}

	public boolean agregarEmpresa(String nombreEmpresa) {
		boolean res = tienda.getInventario().agregarEmpresa(new Empresa(nombreEmpresa));
		empresas.add(nombreEmpresa);
		return res;
	}

	@SuppressWarnings("unused")
	public boolean agregarTipoProducto(String tipoProducto) {

		for (int i = 0; i < tiposProductos.size(); i++) {
			if (!(tiposProductos.get(i).equals(tipoProducto) && tiposProductos1.get(i).equals(tipoProducto))) {
				tiposProductos.add(tipoProducto);
				tiposProductos1.add(tipoProducto);
				return true;
			}
			return false;
		}
		return false;

	}

	public ObservableList<ProductoObservable> buscarProductoNombre(String cadena) {

		if (!cadena.equals("") || !cadena.equals(" ")) {
			ObservableList<ProductoObservable> nuevos = FXCollections.observableArrayList();

			for (int j = 0; j < productos.size(); j++) {
				String[] partesNombre = productos.get(j).getNombre().getValue().split(" ");
				boolean ward = true;
				for (int i = 0; i < partesNombre.length && ward; i++) {
					if (partesNombre[i].length() > cadena.length()) {

						String nombreProducto = partesNombre[i].substring(0, cadena.length());
						if (nombreProducto.equalsIgnoreCase(cadena)) {
							nuevos.add(productos.get(j));
							ward = false;
						}
					}
				}
			}

			return nuevos;
		}
		return null;

	}

	public ObservableList<ProductoObservable> actualizarTablaTipo(String tipo) {

		tipoSeleccionado = tipo;
		productos = FXCollections.observableArrayList();

		if (tipo.equals("Todos")) {
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

	public boolean modificarProducto(String codigo_Barras, String nombre_Producto, int costo_Producto,
			int presentacion_Producto, int precio_Producto, int iva_, String tipo_Producto, int cantidad_,
			ProductoObservable productoAnterior, String empresa) {

		boolean res = tienda.getInventario().modificarProducto(productoAnterior.getProductoInventario(), codigo_Barras,
				nombre_Producto, new Empresa(empresa), presentacion_Producto, iva_, costo_Producto, cantidad_,
				tipo_Producto, precio_Producto);
		actualizarTablaTipo(tipoSeleccionado);
		inventarioControlador.actualizarTabla();
		try {
			Persistencia.guardarObjetos(tienda, archivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;

	}

	public void adjuntarRecibo(Recibo recibo) {
		tienda.getRegistroVentas().adjuntarUnRecibo(recibo, tienda.getInventario());
		imprimirRecibo(recibo);
		try {
			Persistencia.guardarObjetos(tienda, archivo);

		} catch (IOException e) {
			e.printStackTrace();
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

				e.printStackTrace();
			}

		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "No se encuentran recibos registrados", ButtonType.OK);
			alert.show();
		}

	}

	private void imprimirCuadreDeCaja(InformeFiscal informe) {

		ArrayList<String> renglones = new ArrayList<String>();

		renglones.add(
				"┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
		renglones.add(
				"│                                          INFORME FISCAL DE VENTAS DIARIAS                                          │");
		renglones.add(
				"│                                  CAFETERIA LA CUEVA DEL LOCO    ARMENIA-QUINDIO                                    │");
		renglones.add(
				"│                                            ARIGO COMERCIALIZADORA S.A.S.                                           │");

		GregorianCalendar fecha = informe.getFecha();
		String prefijo = "│         Fecha:              " + fecha.get(Calendar.YEAR) + "-" + fecha.get(Calendar.MONTH)
				+ "-" + fecha.get(Calendar.DAY_OF_MONTH);
		int tamano = renglones.get(0).length();
		String sufijo = "Consec. Z No:      " + informe.getNumeroInforme() + "       │";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - sufijo.length()) + sufijo);

		prefijo = "│         Número caja:        " + "1";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│         Registro Inicial:   " + informe.numeroPrimerRecibo();
		sufijo = "Registro Final:     " + informe.numeroUltimoRecibo() + "      │";
		renglones.add(prefijo + calcularEspacios(sufijo, tamano - prefijo.length()) + sufijo);

		prefijo = "│      ┌──────────────────────────────────────────────────────────┬──────────────────┬────────────────┐";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      │                    TIPO DE VENTA                         │    IVA O IPÓC    │    VR. BASE    │";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      ├──────────────────────────────────────────────────────────┼──────────────────┼────────────────┤";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      │                   VENTAS EXENTAS                         │";

		sufijo = 0 + "";
		prefijo += calcularEspacios(sufijo, "    IVA O IPÓC    ".length() - 1) + sufijo;
		Integer[][] iva = informe.getIva();
		Integer[][] ganancias = informe.getGanancia();
		if (ganancias[0][0] == 0) {
			sufijo = ganancias[0][1] + "";
		} else {
			sufijo = 0 + "";
		}
		prefijo += " │" + calcularEspacios(sufijo, "    VR. BASE    ".length() - 1) + sufijo + " │";
		prefijo += calcularEspacios("│", tamano - prefijo.length()) + "│";
		renglones.add(prefijo);

		prefijo = "│      ├──────────────────────────────────────────────────────────┼──────────────────┼────────────────┤";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      │                VALORES BOLSAS PLÁSTICAS                  │";
		sufijo = 0 + "";
		prefijo += calcularEspacios(sufijo, "    IVA O IPÓC    ".length() - 1) + sufijo;
		sufijo = (informe.calcularValorBolsasEfectivo() + informe.calcularValorBolsasTarjeta()) + "";
		prefijo += " │" + calcularEspacios(sufijo, "    VR. BASE    ".length() - 1) + sufijo + " │";
		prefijo += calcularEspacios("│", tamano - prefijo.length()) + "│";
		renglones.add(prefijo);

		prefijo = "│      ├──────────────────────────────────────────────────────────┼──────────────────┼────────────────┤";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      │                 VENTAS GRAVADAS AL 5%                    │";
		String sufijo1 = "";
		boolean ward = true;
		for (int i = 0; i < ganancias.length && ward; i++) {
			if (ganancias[i][0] == 5) {
				sufijo1 = "" + iva[i][1];
				sufijo = "" + ganancias[i][1];
				ward = false;
			}
		}
		if (ward) {
			sufijo1 = 0 + "";
			sufijo = 0 + "";
		}

		prefijo += calcularEspacios(sufijo1, "    IVA O IPÓC    ".length() - 1) + sufijo1;
		prefijo += " │" + calcularEspacios(sufijo, "    VR. BASE    ".length() - 1) + sufijo + " │";
		prefijo += calcularEspacios("│", tamano - prefijo.length()) + "│";
		renglones.add(prefijo);

		prefijo = "│      ├──────────────────────────────────────────────────────────┼──────────────────┼────────────────┤";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      │                VENTAS GRAVADAS AL 19%                    │";
		sufijo1 = "";
		ward = true;
		for (int i = 0; i < ganancias.length && ward; i++) {
			if (ganancias[i][0] == 19) {
				sufijo1 = "" + iva[i][1];
				sufijo = "" + ganancias[i][1];
				ward = false;
			}
		}
		if (ward) {
			sufijo1 = 0 + "";
			sufijo = 0 + "";
		}

		prefijo += calcularEspacios(sufijo1, "    IVA O IPÓC    ".length() - 1) + sufijo1;
		prefijo += " │" + calcularEspacios(sufijo, "    VR. BASE    ".length() - 1) + sufijo + " │";
		prefijo += calcularEspacios("│", tamano - prefijo.length()) + "│";
		renglones.add(prefijo);

		prefijo = "│      ├──────────────────────────────────────────────────────────┼──────────────────┼────────────────┤";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      │                   TOTAL DE VENTAS                        │";
		prefijo += calcularEspacios(informe.getTotalIva() + "", "    IVA O IPÓC    ".length() - 1)
				+ informe.getTotalIva();
		prefijo += " │" + calcularEspacios(informe.getTotalGanancia() + "", "    VR. BASE    ".length() - 1)
				+ (informe.getTotalGanancia() + informe.calcularValorBolsasEfectivo()
						+ informe.calcularValorBolsasTarjeta())
				+ " │";
		prefijo += calcularEspacios("│", tamano - prefijo.length()) + "│";
		renglones.add(prefijo);

		prefijo = "│      └──────────────────────────────────────────────────────────┴──────────────────┴────────────────┘";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      ┌──────────────────┬──────────────────┬──────────────────┬──────────────────┬────────────────┐";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      │     EFECTIVO     │      CHEQUES     │  TARJ. CRÉDITO   │   VIAS CRÉDITO   │ TOTAL RECIBIDO │";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      ├──────────────────┼──────────────────┼──────────────────┼──────────────────┼────────────────┤";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");

		prefijo = "│      │"
				+ calcularEspacios((informe.getEfectivo() + informe.calcularValorBolsasEfectivo()) + "",
						"     EFECTIVO     ".length() - 1)
				+ (informe.getEfectivo() + informe.calcularValorBolsasEfectivo()) + " │";
		prefijo += calcularEspacios(0 + "", "      CHEQUES     ".length() - 1) + 0 + " │";
		prefijo += calcularEspacios((informe.getTarjeta() + informe.calcularValorBolsasTarjeta()) + "",
				"  TARJ. CRÉDITO   ".length() - 1) + (informe.getTarjeta() + informe.calcularValorBolsasTarjeta())
				+ " │";
		prefijo += calcularEspacios(0 + "", "   VIAS CRÉDITO   ".length() - 1) + 0 + " │";
		prefijo += calcularEspacios((informe.getTotalEnCaja() + informe.calcularValorBolsasTarjeta()) + "",
				" TOTAL RECIBIDO ".length() - 1) + (informe.getTotalEnCaja() + informe.calcularValorBolsasTarjeta())
				+ " │";

		prefijo += calcularEspacios("│", tamano - prefijo.length() - 1) + " │";
		renglones.add(prefijo);

		prefijo = "│      └──────────────────┴──────────────────┴──────────────────┴──────────────────┴────────────────┘";
		renglones.add(prefijo + calcularEspacios(prefijo, tamano - 1) + "│");
		renglones.add(
				"│                                                                                                                    │");

		renglones.add(
				"│                                                                                                                    │");
		renglones.add(
				"└────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");

		try {
			String nombreArchivo = "Cuadre " + informe.getNumeroInforme() + " " + fecha.get(Calendar.YEAR) + "-"
					+ (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + ".txt";

			Persistencia.escribirArchivo(rutaInformesFiscales + nombreArchivo, renglones);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void imprimirRecibo(Recibo recibo) {

		ArrayList<String> renglones = new ArrayList<String>();

		renglones.add("┌──────────────────────────────────────────┐");
		renglones.add("│              COMEDAL S.A.S.              │");
		renglones.add("│         CF/RIA LA CUEVA DEL LOCO         │");
		renglones.add("│             NIT: 901262014-5             │");
		renglones.add("│          RESPONSABLE IMP/CONSUMO         │");
		renglones.add("│                                          │");
		renglones.add("│                                          │");
		renglones.add("│           E IMP/VENTAS LEY 1943          │");
		renglones.add("│            CRA 19 # 1N 00 LC 5           │");
		renglones.add("│              ARMENIA QUINDIO             │");
		renglones.add("│                                          │");
		int tamano = renglones.get(0).length() - 1;
		GregorianCalendar fecha = recibo.getFecha();

		String renglon1 = "│ " + fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH) + " " + fecha.get(Calendar.HOUR) + ":" + fecha.get(Calendar.MINUTE)
				+ ":" + fecha.get(Calendar.SECOND);
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "│");
		renglon1 = recibo.getId() + " │";
		renglones.add("│" + calcularEspacios(renglon1, tamano) + renglon1);
		renglones.add("│                                          │");
		renglones.add("│                                          │");

		int limite = (renglones.get(0).length() * 60) / 100;

		for (int i = 0; i < recibo.getProductosV().size(); i++) {
			String renglon = "│ ";
			ProductosVentas producto = recibo.getProductosV().get(i);
			String[] partes = producto.getProducto().getNombre().split(" ");
			for (int j = 0; j < partes.length; j++) {

				if (j == partes.length - 1) {
					String cantidades = " -- " + producto.getCantidad() + " "
							+ producto.getProducto().getPrecio() * producto.getCantidad();
					String espacios = calcularEspacios(renglon + partes[j], tamano - cantidades.length());

					renglones.add(renglon + partes[j] + espacios + cantidades + "│");

				} else {
					if (renglon.length() + partes[j].length() > limite) {

						renglones.add(renglon + calcularEspacios(renglon, tamano) + "│");
						renglon = "│ ";

					} else {

						renglon += partes[j] + " ";

					}
				}
			}
		}
		int valor = recibo.getValorBolsas();
		if (valor > 0) {
			String renglon = "│ BOLSA PLASTICA";
			renglon += calcularEspacios(renglon, limite);
			String cantidades = " -- " + valor / 50 + " " + valor;
			renglon += cantidades;
			renglones.add(renglon + calcularEspacios(renglon, tamano - renglon.length() - 1) + "│");
		}

		renglones.add("│                                          │");
		renglones.add("│                                          │");

		Integer[][] iva = recibo.getTotalIvaRecibo();
		Integer[][] ganancias = recibo.getTotalGananciaRecibo();
		String prefijo = "│ BASE GRAVADA";
		for (int i = 0; i < iva.length; i++) {

			if (iva[i][0] == 0) {
				renglon1 = ganancias[i][1] + "";
				renglones.add(
						prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "│");
				String iva_ = "│   EXENTOS:";
				renglon1 = iva[i][1] + "";
				renglones.add(iva_ + calcularEspacios(renglon1, tamano - 1 - iva_.length()) + "$" + renglon1 + "│");
			} else {
				renglon1 = ganancias[i][1] + "";
				renglones.add(
						prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "│");
				String iva_ = "│   GRAVADOS " + iva[i][0] + "%";
				renglon1 = iva[i][1] + "";
				renglones.add(iva_ + calcularEspacios(renglon1, tamano - 1 - iva_.length()) + "$" + renglon1 + "│");
			}

		}

		renglones.add("│" + calcularEspacios("│", tamano) + "│");
		String tarjeta = recibo.getTarjeta() ? "SI" : "NO";
		renglon1 = "│    PAGO CON TARJETA:    " + tarjeta;
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "│");
		renglon1 = "│    TOTAL:             " + recibo.getPrecioTotal();
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "│");
		renglon1 = "│    CAJA:              " + recibo.getEfectivoRegistrado();
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "│");
		renglon1 = "│    CAMBIO:            " + recibo.getCambio();
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "│");
		renglones.add("│                                          │");
		renglones.add("└──────────────────────────────────────────┘");

		try {
			String nombreArchivo = "Recibo " + recibo.getId() + " " + fecha.get(Calendar.YEAR) + "-"
					+ (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + ".txt";

			Persistencia.escribirArchivo(rutaRecibos + nombreArchivo, renglones);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String calcularEspacios(String renglon, int limite) {
		String espacios = "";

		for (int i = 0; i < limite - renglon.length(); i++) {
			espacios += " ";
		}

		return espacios;
	}

	public void imprimirInformeFiscal(InformeFiscal informe) {

		ArrayList<String> renglones = new ArrayList<String>();

		renglones.add("┌──────────────────────────────────────────┐");
		renglones.add("│              COMEDAL S.A.S.              │");
		renglones.add("│         CF/RIA LA CUEVA DEL LOCO         │");
		renglones.add("│             NIT: 901262014-5             │");
		renglones.add("│          RESPONSABLE IMP/CONSUMO         │");
		renglones.add("│                                          │");

		int tamano = renglones.get(0).length() - 1;
		GregorianCalendar fecha = new GregorianCalendar();

		String renglon1 = "│ Z      " + fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH) + "   " + fecha.get(Calendar.HOUR) + ":" + fecha.get(Calendar.MINUTE)
				+ ":" + fecha.get(Calendar.SECOND);
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "│");
		if (informe.getLista().size() >= 2) {
			renglon1 = informe.getLista().get(informe.getLista().size() - 2).getId() + "│";
		} else {
			renglon1 = informe.getLista().get(informe.getLista().size() - 1).getId() + "│";
		}
		renglones.add("│ Z" + calcularEspacios(renglon1, tamano - 2) + renglon1);
		renglones.add("│                                          │");
		renglones.add("│                                          │");
		renglones.add("│                                          │");
		renglones.add("│                                          │");
		renglones.add("│              COMEDAL S.A.S.              │");
		renglones.add("│         CF/RIA LA CUEVA DEL LOCO         │");
		renglones.add("│             NIT: 901262014-5             │");
		renglones.add("│          RESPONSABLE IMP/CONSUMO         │");
		renglones.add("│                                          │");

		GregorianCalendar fecha1 = new GregorianCalendar();

		renglon1 = "│ Z      " + fecha1.get(Calendar.YEAR) + "-" + (fecha1.get(Calendar.MONTH) + 1) + "-"
				+ fecha1.get(Calendar.DAY_OF_MONTH) + "   " + fecha1.get(Calendar.HOUR) + ":"
				+ fecha1.get(Calendar.MINUTE) + ":" + fecha1.get(Calendar.SECOND);
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "│");
		renglon1 = informe.getLista().get(informe.getLista().size() - 1).getId() + "│";
		renglones.add("│ Z" + calcularEspacios(renglon1, tamano - 2) + renglon1);

		renglones.add("│                                          │");
		renglones.add("│ ---------------------------------------- │");
		renglon1 = "│ Z       Z DIARIO";
		renglones.add(renglon1 + calcularEspacios(renglon1, tamano) + "│");
		renglones.add("│ ---------------------------------------- │");
		renglon1 = informe.getNumeroInforme();
		String prefijo = "│ Z       DEPTOS";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "│");
		renglones.add("│                                          │");

		Integer[][] iva = informe.getIva();
		for (int i = 0; i < iva.length; i++) {

			if (iva[i][0] == 0) {
				String iva_ = "│   EXENTOS:";
				renglon1 = iva[i][1] + "";
				renglones.add(iva_ + calcularEspacios(renglon1, tamano - 1 - iva_.length()) + "$" + renglon1 + "│");
			} else {
				String iva_ = "│   GRAVADOS " + iva[i][0] + "%";
				renglon1 = iva[i][1] + "";
				renglones.add(iva_ + calcularEspacios(renglon1, tamano - 1 - iva_.length()) + "$" + renglon1 + "│");
			}

		}
		renglones.add("│ ---------------------------------------- │");
		prefijo = "│ Z    ToT. FIJOS";
		renglon1 = informe.getNumeroInforme();
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "│");

		prefijo = "│ BRUTO ";
		renglon1 = informe.getTotalEnCaja() + "";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "│");

		prefijo = "│    EFECTIVO: ";
		renglon1 = (informe.getEfectivo() + informe.calcularValorBolsasEfectivo()) + "";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "│");

		prefijo = "│    TARJETA: ";
		renglon1 = (informe.getTarjeta() + informe.calcularValorBolsasTarjeta()) + "";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "│");

		renglones.add("│ ---------------------------------------- │");

		Integer[][] ganancias = informe.getGanancia();
		prefijo = "│ BASE GRAVADA";
		for (int i = 0; i < iva.length; i++) {

			if (iva[i][0] == 0) {
				renglon1 = ganancias[i][1] + "";
				renglones.add(
						prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "│");
				String iva_ = "│   EXENTOS:";
				renglon1 = iva[i][1] + "";
				renglones.add(iva_ + calcularEspacios(renglon1, tamano - 1 - iva_.length()) + "$" + renglon1 + "│");
			} else {
				renglon1 = ganancias[i][1] + "";
				renglones.add(
						prefijo + calcularEspacios(renglon1, tamano - prefijo.length() - 1) + "$" + renglon1 + "│");
				String iva_ = "│   GRAVADOS " + iva[i][0] + "%";
				renglon1 = iva[i][1] + "";
				renglones.add(iva_ + calcularEspacios(renglon1, tamano - 1 - iva_.length()) + "$" + renglon1 + "│");
			}

		}

		renglon1 = "$" + 0;
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "│");

		prefijo = "│ IMP/CSUMO 8%";
		renglon1 = "$" + 0;
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "│");

		renglon1 = informe.numeroPrimerRecibo() + "--->" + informe.numeroUltimoRecibo();
		renglones.add("│" + calcularEspacios(renglon1, tamano - 2) + renglon1 + " │");

		renglones.add("│ ---------------------------------------- │");

		renglon1 = informe.getNumeroInforme();
		prefijo = "│ Z       FUNC LIBRES";
		renglones.add(prefijo + calcularEspacios(renglon1, tamano - prefijo.length()) + renglon1 + "│");

		prefijo = "│ CAJA           No";
		renglones.add(prefijo + calcularEspacios("1", tamano - prefijo.length()) + "1│");

		renglon1 = informe.getTotalEnCaja() + informe.calcularValorBolsasTarjeta()
				+ informe.calcularValorBolsasTarjeta() + "│";
		renglones.add("│" + calcularEspacios(renglon1, tamano) + renglon1);

		renglones.add("│                                          │");
		renglones.add("└──────────────────────────────────────────┘");

		try {
			String nombreArchivo = "Informe " + informe.getNumeroInforme() + " " + fecha.get(Calendar.YEAR) + "-"
					+ (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + ".txt";

			Persistencia.escribirArchivo(rutaInformesFiscales + nombreArchivo, renglones);
		} catch (IOException e) {
			e.printStackTrace();
		}

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

}
