import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GestionContenido extends DefaultHandler {
	private String etiquetaActual;
	private int pendientes;
	private String nombre;
	private String dni;
	private BufferedWriter filtroEscritura;
	public void startDocument() throws SAXException {
		try {
			 filtroEscritura = new BufferedWriter(new FileWriter("alumnosRepetidores.txt",true));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// Guardamos en que elemento estamos
		etiquetaActual = qName;
		// Y miramos si tiene atributos,solo si la etiqueta es alumno
		if (etiquetaActual.equals("alumno")) {

			// Guardamos su valor
			pendientes = Integer.parseInt(attributes.getValue("pendientes"));
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		// Obtenemos la cadena de caracteres
		String cadena = new String(ch, start, length);
		// Eliminamos los saltos de linea, blanco y tabuladores
		cadena = cadena.replaceAll("[\t\n]", "").trim();

		if (cadena.length() <= 0) {
			// Si la cadena tiene 0 caracteres salimos del metodo
			return;
		}
		switch (etiquetaActual) {
		case "nombre":
			nombre = cadena;
			
			break;
		case "dni":
			dni = cadena;
			break;
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equals("alumno")) {
			if (pendientes >2) {
				try {
					filtroEscritura.write(nombre+" ");
					filtroEscritura.write(dni);
					filtroEscritura.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			} else {
				File directorio = new File("Alumnos/" + nombre);
				directorio.mkdirs();
			}

		}
	}
	 public void endDocument() throws SAXException {
		 try {
			filtroEscritura.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
	
	
