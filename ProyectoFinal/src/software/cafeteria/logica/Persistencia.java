package software.cafeteria.logica;

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

/**
 * Clase para representar un archivo
 * 
 * @author
 *
 */
public class Persistencia {

	/**
	 * Permite escribir un archivo
	 * 
	 * @param rutaArchivo
	 *            Es la ruta del archivo
	 * @param miTexto
	 *            Lineas a escribir
	 * @throws IOException
	 */
	public static void escribirArchivo(String rutaArchivo, ArrayList<String> miTexto) throws IOException {

		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaArchivo), "utf-8");

		// File miArchivo = new File(rutaArchivo);
		//
		// FileWriter miFileWriter = new FileWriter(miArchivo, adicionar);
		BufferedWriter miBufferWriter = new BufferedWriter(osw);

		for (String miLinea : miTexto) {
			miBufferWriter.write(miLinea + "\n");
		}

		miBufferWriter.close();

	}

	/**
	 * Permite cargar un archivo de texto
	 * 
	 * @param ruta
	 * @return Las lineas que tiene el archivo
	 * @throws IOException
	 */

	public static ArrayList<String> cargarArchivoTexto(String ruta) throws IOException {

		File miArchivo = new File(ruta);
		FileReader miFileReader = new FileReader(miArchivo);
		BufferedReader miBufferReader = new BufferedReader(miFileReader);
		String linea;
		ArrayList<String> misLineas = new ArrayList<>();
		while ((linea = miBufferReader.readLine()) != null) {
			misLineas.add(linea);
		}

		miBufferReader.close();
		miFileReader.close();
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
	public static ArrayList<ArrayList<String>> cargarArchivos(File directorio, ArrayList<String> nombres)
			throws Exception {
		ArrayList<String> archivosRuta = new ArrayList<>();
		listarRecursivamenteArchivos(directorio, archivosRuta);

		ArrayList<ArrayList<String>> misArchivos = new ArrayList<>();
		// System.out.println("archivosRuta.get(i) " +archivosRuta.size());

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

	public static void listarRecursivamenteArchivos(File directorio, ArrayList<String> array) throws IOException {

		File archivos[] = directorio.listFiles();

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
	public static ArrayList<ArrayList<String>> cargarRutas(File directorio) throws IOException {
		ArrayList<String> archivosRuta = new ArrayList<>();
		listarRecursivamenteArchivos(directorio, archivosRuta);

		ArrayList<ArrayList<String>> misArchivos = new ArrayList<>();

		for (int i = 0; i < archivosRuta.size(); i++) {
			ArrayList<String> miR = new ArrayList<>();
			miR.add(archivosRuta.get(i));
			// System.out.println(archivosRuta.get(i));
			misArchivos.add(miR);

		}

		return misArchivos;

	}

	public static void guardarObjetos(Object objeto, String ruta) throws IOException {
		FileOutputStream fos = new FileOutputStream(ruta);

		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(objeto);

		fos.close();
		oos.close();

	}

	public static Object cargarObjeto(String ruta) throws IOException, ClassNotFoundException {

		FileInputStream fis = new FileInputStream(ruta);

		ObjectInputStream ois = new ObjectInputStream(fis);

		Object objeto = ois.readObject();

		fis.close();
		ois.close();

		return objeto;
	}

}
