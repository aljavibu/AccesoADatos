/**
 * CLASE QUE IMPLEMENTA EL PATR�N SINGLETON PARA OBTENER LA CONSULTA A LA BASE DE DATOS
 */
package singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Openwebinars
 *
 */
public class DBConnection {
	
	private static final String CONTRASENNA = "mipass";

	private static final String USUARIO = "root";

	private static final String JDBC_URL = "jdbc:mysql://localhost:3307/empleados";
	
	private static Connection instance = null;
	
	private DBConnection() throws SQLException {
		
		Properties props = new Properties();
		props.put("user", USUARIO);
		props.put("password", CONTRASENNA);
		instance = DriverManager.getConnection(JDBC_URL, props);
		
	
	}
	
	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			new DBConnection();
		}
		
		return instance;
	}
	

}
