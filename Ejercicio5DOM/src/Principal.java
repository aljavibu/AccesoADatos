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

	private static final String FICHERO_XML = "resultado.xml";

	public static void main(String[] args) {

		try {

			// TODO Auto-generated method stub
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document arbol = builder.parse(new File("resultados.xml"));

			modificandoArbolDom(arbol);

			// Pasamos el arbol al numero fichero xml
			Source source = new DOMSource(arbol);
			Result result = new StreamResult("resultadoNuevo.xml");
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void modificandoArbolDom(Document arbol) {
		Element elementoPartido;
		String resultado=" ";
		Element raiz = (Element) arbol.getFirstChild();

		NodeList listaNodos = raiz.getElementsByTagName("partido");
		
		for (int i = 0; i < listaNodos.getLength(); i++) {
			elementoPartido = (Element) listaNodos.item(i);

			resultado=compararGoles(elementoPartido);
		
			elementoPartido.setAttribute("resultado", resultado);

		}
	}
	//Obtiene la lista de nodos
	private static NodeList getNodo(String etiqueta, Element ele) throws IOException {

		NodeList listaDeHijosDeEtiqueta = ele.getElementsByTagName(etiqueta); 

		if (listaDeHijosDeEtiqueta.getLength() == 0) {
			throw new IOException("No existe el elemento " + etiqueta);

		} else {
			return listaDeHijosDeEtiqueta;
		}
	}
	
	//Recibe la lista de nodos y compara los resultados para enviar 1,x,2
	private static String compararGoles(Element elementoPartido) {
		int golesLocal,golesVisitante;
		 NodeList listaDeHijosDeEtiqueta=null;
		
		 try {
			listaDeHijosDeEtiqueta = getNodo("goles",elementoPartido);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
			 golesLocal = Integer.parseInt(listaDeHijosDeEtiqueta.item(0).getTextContent().replaceAll("[\t\n]", "").trim());
			 golesVisitante=Integer.parseInt(listaDeHijosDeEtiqueta.item(1).getTextContent().replaceAll("[\t\n]", "").trim());
			 if(golesLocal>golesVisitante) {
				 return "1";
			 }else if(golesLocal<golesVisitante) {
				 return "2";
			 }
			 else {
				 return "X";			
			 }
		
	}

}
