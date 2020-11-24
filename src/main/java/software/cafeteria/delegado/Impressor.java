package main.java.software.cafeteria.delegado;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.JOptionPane;

import main.java.software.cafeteria.Main;

public class Impressor {

	public void imprimirArchivo(String rutaArchivo) {
		// try {
		// PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		// pras.add(new Copies(1));
		// PrintService pss[] =
		// PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE,
		// pras);
		// if (pss.length == 0)
		// throw new RuntimeException("No printer services available.");
		// PrintService ps = pss[6];
		// System.out.println("Printing to " + ps);
		// DocPrintJob job = ps.createPrintJob();
		// FileInputStream fin = new FileInputStream(rutaArchivo);
		// Doc doc = new SimpleDoc(fin, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
		// job.print(doc, pras);
		// fin.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (PrintException e) {
		// e.printStackTrace();
		// }

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
					"El sistema no permite imprimir usando la clase Desktop.\n" + "Actualiza tu versi�n de JVM",
					"Error imprimiendo", JOptionPane.ERROR_MESSAGE);
		}

	}

}
