package main.java.software.cafeteria.delegado;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import main.java.software.cafeteria.Main;

public class Impressor {

	public void imprimirArchivo(String rutaArchivo) {

		Desktop desktop = Desktop.getDesktop();
		File fichero = new File(rutaArchivo);
		if (desktop.isSupported(Desktop.Action.PRINT)) {
			try {
				desktop.print(fichero);
			} catch (IOException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"El sistema no permite imprimir usando la clase Desktop.\n" + "Actualiza tu versión de JVM",
					"Error imprimiendo", JOptionPane.ERROR_MESSAGE);
		}

	}

}
