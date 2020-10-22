import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GestionContenido extends DefaultHandler {
	private String etiquetaActual;
	private String nombreVisitante;
	private String nombreLocal;
	private String resultado;
	private int golesLocal;
	private int golesVisitante;
	private boolean localChecked = false;

	public void startDocument() throws SAXException {

	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// Guardamos en que elemento estamos
		etiquetaActual = qName;

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
		case "equipo": // Si la etiqueta es la de equipo ,almacenamos su nombre
			if (localChecked == true) {// Si ya se ha almacenado al local,almacenamos el nombre del visitante
				nombreVisitante = cadena;

			} else {
				nombreLocal = cadena;// Si no se habia guardado el local lo hacemos

			}
			break;
		case "goles":// Si la etiqueta es la de de goles, los almacenamos
			if (localChecked == true) {

				golesVisitante = Integer.parseInt(cadena);

			} else {

				golesLocal = Integer.parseInt(cadena);
				localChecked = true;// Ponemos el boolean a true porque ya se ha terminado de introducir los datos
									// del equipo local(nombre y goles);
			}
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		// Si el elemento que termina es un partido, hay que mostrar el resultado de
		// este

		if (qName.equals("partido")) {
			if (golesLocal > golesVisitante) {
				resultado = "1";
			} else if (golesLocal < golesVisitante) {
				resultado = "2";
			} else {
				resultado = "X";
			}
			System.out.println(nombreLocal + " - " + nombreVisitante + " = " + resultado);
			localChecked = false;// Indica que hay volver a introducir el resultado del local
		}

	}
}
