import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Principal {

	private static final String FICHERO_XML = "universidad.xml";

	public static void main(String[] args) {

		try {

			// TODO Auto-generated method stub
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document arbol = builder.parse(new File(FICHERO_XML));

			modificandoArbolDom(arbol);

			// Pasamos el arbol al numero fichero xml
			Source source = new DOMSource(arbol);
			Result result = new StreamResult("universidadNuevo.xml");
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void modificandoArbolDom(Document arbol) {
		Element  empleado;
		Element raiz = (Element) arbol.getFirstChild();
		Node puesto;
		
			NodeList listaEmpleados = raiz.getElementsByTagName("empleado");

			// Y por cada empleado obtenemos su nodo puesto
			for (int i1 = 0; i1 < listaEmpleados.getLength(); i1++) {
				empleado = (Element) listaEmpleados.item(i1);
				try {

					// Obtenemos el nodo puesto del empleado
					puesto = getNodo("puesto", empleado);

					if (puesto.getTextContent().equals("Asociado")) {
						int salario;

						// Obtenemos el salario
						salario = Integer.parseInt(empleado.getAttribute("salario"));

						// Lo subimos un 5%
						empleado.setAttribute("salario", String.valueOf(salario * 1.05));

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	private static Node getNodo(String etiqueta, Element ele) throws IOException {

		// Obtenemos la lista de hijos puesto
		NodeList listaDeHijosDeEtiqueta = ele.getElementsByTagName(etiqueta);

		if (listaDeHijosDeEtiqueta.getLength() == 0) {
			throw new IOException("No existe el elemento " + etiqueta);

		} else {
			// Devolvemos solo el primero
			return listaDeHijosDeEtiqueta.item(0);
		}
	}
}
