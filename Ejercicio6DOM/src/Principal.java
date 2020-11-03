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
import org.w3c.dom.Text;

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
		Element departamento,empleado;
		Element raiz = (Element) arbol.getFirstChild();
		Node nombre;
		//Lista Departamentos
	
			NodeList listaEmpleados=raiz.getElementsByTagName("empleado");
			
			
			//Y por cada empleado obtenemos su nodo nombre
			for(int i1 = 0;i1< listaEmpleados.getLength();i1++) {
				empleado=(Element)listaEmpleados.item(i1);
				try {
					
					//Obtenemos el nodo nombre del empleado
					nombre= getNodo("nombre", empleado);
					//Pasamos el nodo nombre,el empleado y arbol para construir el nuevo elemento apellido
					separarApellido(nombre,empleado,arbol);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		}



	private static void separarApellido(Node nombreNodo, Element empleado, Document arbol) {
		// TODO Auto-generated method stub
		
		String nombre, apellido,nombreCompleto;
		int posicionEspacio;
		
		//Metemos el valor del nodo en un string
		nombreCompleto=nombreNodo.getTextContent();
		
		//Obtenemos la posicion del espacio
		posicionEspacio=nombreCompleto.lastIndexOf(" ");
		
		//El nombre será lo que esté antes del espacio
		nombre=nombreCompleto.substring(0, posicionEspacio);
		
		//El apellido lo de después
		apellido=nombreCompleto.substring(posicionEspacio);
		
		//Cambiamos el valor del nodo para que solo almacene el nombre
		nombreNodo.setTextContent(nombre);
		
		
		//Creamos el elemento apellido
		Element newElement=arbol.createElement("apellido");
		newElement.setTextContent(apellido);
		
		//Anadimos el elemento al empleado ,que es el padre
		empleado.appendChild(newElement);
		
		
		
	}

	private static Node getNodo(String etiqueta, Element ele) throws IOException {
		
		//Obtenemos la lista de hijos nombre
		NodeList listaDeHijosDeEtiqueta = ele.getElementsByTagName(etiqueta);

		if (listaDeHijosDeEtiqueta.getLength() == 0) {
			throw new IOException("No existe el elemento " + etiqueta);

		} else {
			//Devolvemos solo el primero 
			return listaDeHijosDeEtiqueta.item(0);
		}
	}
}
