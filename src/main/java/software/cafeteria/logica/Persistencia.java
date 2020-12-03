package main.java.software.cafeteria.logica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para representar un archivo
 * 
 * @author
 *
 */
public class Persistencia {

	private Persistencia() {

	}

	private static final Logger LOGGER = Logger
			.getLogger(Persistencia.class.getPackage().getName() + "." + Persistencia.class.getName());
	private static String error = "Error";

	/**
	 * Permite escribir un archivo
	 * 
	 * @param rutaArchivo
	 *            Es la ruta del archivo
	 * @param miTexto
	 *            Lineas a escribir
	 * @throws IOException
	 */
	public static void escribirArchivo(String rutaArchivo, List<String> miTexto) throws IOException {

		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaArchivo), "utf-8");

		BufferedWriter miBufferWriter = new BufferedWriter(osw);
		try {

			for (String miLinea : miTexto) {
				miBufferWriter.write(miLinea + "\n");
			}

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, error, e);
		} finally {
			miBufferWriter.close();
		}

	}

	/**
	 * Permite cargar un archivo de texto
	 * 
	 * @param ruta
	 * @return Las lineas que tiene el archivo
	 * @throws IOException
	 */

	public static List<String> cargarArchivoTexto(String ruta) throws IOException {

		File miArchivo = new File(ruta);
		FileReader miFileReader = new FileReader(miArchivo);
		BufferedReader miBufferReader = new BufferedReader(miFileReader);
		String linea;
		ArrayList<String> misLineas = new ArrayList<String>();
		try {
			while ((linea = miBufferReader.readLine()) != null) {
				misLineas.add(linea);
			}

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, error, e);
		} finally {
			miBufferReader.close();
			miFileReader.close();
		}

		return misLineas;
	}

	/**
	 * Permite cargar multiples archivos
	 * 
	 * @param directorio
	 * @param nombres
	 *            Los nombres de los archivos a cargar
	 * @return Un arraylist de arraylist, donde cada posicion corresponde a un
	 *         archivo
	 * @throws Exception
	 */
	public static List<List<String>> cargarArchivos(File directorio, List<String> nombres) throws IOException {
		List<String> archivosRuta = new ArrayList<String>();
		listarRecursivamenteArchivos(directorio, archivosRuta);

		List<List<String>> misArchivos = new ArrayList<List<String>>();

		for (int i = 0; i < archivosRuta.size(); i++) {
			misArchivos.add(cargarArchivoTexto(archivosRuta.get(i)));
			nombres.add(archivosRuta.get(i).substring(archivosRuta.get(i).lastIndexOf("\\") + 1));

		}

		return misArchivos;

	}

	/**
	 * Permite listar recursivamente los archivos dentro de carpetas
	 * 
	 * @param directorio.
	 *            La carpeta
	 * @param array,
	 *            el arreglo con los nombres del archivo
	 * @throws IOException
	 */

	public static void listarRecursivamenteArchivos(File directorio, List<String> array) throws IOException {

		File[] archivos = directorio.listFiles();

		for (int i = 0; i < archivos.length; i++) {

			String ruta = archivos[i].getPath();

			if (ruta.substring(ruta.length() - 7, ruta.length()).contains("txt")) {
				if (archivos[i].isDirectory()) {

					listarRecursivamenteArchivos(archivos[i], array);
				} else {
					array.add(ruta);
				}

			}

		}

	}

	/**
	 * Permite cargar Rutas
	 * 
	 * @param directorio
	 * @return Un arraylist de arraylist, donde cada posicion corresponde a un
	 *         archivo
	 * @throws IOException
	 */
	public static List<List<String>> cargarRutas(File directorio) throws IOException {
		List<String> archivosRuta = new ArrayList<String>();
		listarRecursivamenteArchivos(directorio, archivosRuta);

		List<List<String>> misArchivos = new ArrayList<List<String>>();

		for (int i = 0; i < archivosRuta.size(); i++) {
			ArrayList<String> miR = new ArrayList<String>();
			miR.add(archivosRuta.get(i));

			misArchivos.add(miR);

		}

		return misArchivos;

	}

	public static void guardarObjetos(Object objeto, String ruta) throws IOException {
		FileOutputStream fos = new FileOutputStream(ruta);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			try {

				oos.writeObject(objeto);

			} finally {
				oos.close();
			}

		} finally {
			fos.close();

		}

	}

	public static Object cargarObjeto(String ruta) throws IOException, ClassNotFoundException {

		Object objeto = null;
		FileInputStream fis = new FileInputStream(ruta);
		try {
			ObjectInputStream ois = new ObjectInputStream(fis);
			try {

				objeto = ois.readObject();
			} finally {
				ois.close();
			}

		} finally {
			fis.close();
		}

		return objeto;
	}

}
