/*
		Utiliza SAX para visualizar el contenido del fichero un fichero XML
*/


import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class MainSAX
{

	private static final String NOMBRE_FICHERO = "resultados.xml";

	
	public static void main(String[] args) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	{
		
		leerXMLConSax(NOMBRE_FICHERO);
	}
	
	private static void leerXMLConSax(String nombreFichero) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException
	{
		
		
		System.out.println("Comienzo");
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
		
		XMLReader reader = parser.getXMLReader();
		reader.setContentHandler(new GestionContenido());
		reader.parse(new InputSource(nombreFichero));
		
	}



}