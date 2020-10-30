import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		String nombreFicheroTxt = "personas.txt";
		String nombreFicheroObj = "personas.obj";
		leerTxt(nombreFicheroTxt);
		leerObj(nombreFicheroObj);
	}

	
	
	
	
	private static void leerObj(String nombreFicheroObj) {
		Persona personaLeida;
		try {
			// Creamos los Object y file InputStream
			FileInputStream fis=new FileInputStream(nombreFicheroObj);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			
			// available ​Devuelve​ 0 ​si​ no ha ​podido​ leer ​nada
			while(fis.available()!= 0)
			{
				//Lee del archivo el objeto persona y lo almacena en nuestro personaLeida
				personaLeida=(Persona) ois.readObject();
				
				//Envia esta persona leida al metodo escribirPersona 
				escribirPersona(personaLeida);
		}
			
			
			
		} catch (FileNotFoundException e) {

			System.out.println("No se encuentra el archivo "+nombreFicheroObj);
			
		} catch (IOException e) {

			System.out.println(e.getMessage());
			
			
		} catch (ClassNotFoundException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	
	
	

	
	
	private static void leerTxt(String nombreFicheroTxt) {
		try {// Creamos los Buffered y File reader
			BufferedReader br = new BufferedReader(new FileReader(nombreFicheroTxt));

			// Un objeto persona para poder escribirlo luego en nuestro archivo .obj
			Persona persona;

			// Creamos tambien un array para guardar las partes del split de la linea
			String[] partesSplit;

			// Leemos la primera linea
			String linea = br.readLine();

			// Y hacemos un bucle hasta que llegue la ultima linea la cual nos dara null
			while (linea != null) {

				// Hacemos el split y lo guardamos en el array
				partesSplit = linea.split(";");

				// Y utilizamos las dos partes del array para crear el objeto persona
				persona = new Persona(partesSplit[0], partesSplit[1]);
				// Llamamos al object writer
				
				escribirPersona(persona);

				// leemos la siguiente linea
				linea = br.readLine();

			}

		} catch (FileNotFoundException e) {

			System.out.println("No se encuentra el archivo "+nombreFicheroTxt);
		} catch (IOException e) {

			System.out.println(e.getMessage());
		}
	}

	
	
	
	
	private static void escribirPersona(Persona persona) {

		try {
			// Creamos los Object y file outputStream
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("todasPersonas.obj"));
			// Escribimos la persona que nos llegue
			System.out.println("Escrito: \n"+persona.toString());
			oos.writeObject(persona);

		} catch (FileNotFoundException e) {

			System.out.println(e.getMessage());

		} catch (IOException e) {

			System.out.println(e.getMessage());
		}
	}

}
