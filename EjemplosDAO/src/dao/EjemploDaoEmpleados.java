/**
 * 
 */
package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Scanner;


public class EjemploDaoEmpleados {

	private static Scanner teclado=new Scanner(System.in);

	public static void main(String[] args) {

		try {
			int opcion;
			


			do {
				menu();
				opcion = Integer.parseInt(teclado.nextLine());

				switch (opcion) {
				case 0:
					DaoEmpleados dao = DaoEmpleados.getInstance();
					dao.cerrarSesion();
					break;
				case 1:
					listarTodosEmpleados();
					break;
				case 2:
					listarUnEmpleado();
					break;
				case 3:
					nuevoEmpleado();
					break;
				case 4:
					actualizarUnEmpleado();
					break;
				case 5:
					eliminarUnEmpleado();
					break;

				}

			} while (opcion != 0);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}

	}

	

	



	public static void menu() {

		System.out.println("SISTEMA DE GESTI�N DE EMPLEADOS");
		System.out.println("===============================");
		System.out.println("0. Salir");
		System.out.println("1. Listar todos los empleados");
		System.out.println("2. Listar un empleado por su ID");
		System.out.println("3. Insertar un nuevo empleado");
		System.out.println("4. Actualizar un empleado");
		System.out.println("5. Eliminar un empleado");

	}
	
	private static void listarTodosEmpleados() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		List<Empleado> lista = null;
		
		lista = dao.findAll();
		
		
		if (lista != null)

			
			for (Empleado e: lista) {
				System.out.printf("%d %s %s\t\t (%s) - %.2f %n", e.getId(), e.getNombre(), e.getApellido(), 
						e.getFechaNacimiento().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
						e.getSueldo(),e.getDepartamento());
			}
		else
			System.out.println("No hay empleados registrados en la base de datos");
		
		System.out.println("");
		
	}
	
	private static void listarUnEmpleado() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		System.out.println("Búsqueda de un empleado");
		System.out.print("Introduzca el ID del empleado: ");
		int id = Integer.parseInt(teclado.nextLine());
		
		Empleado empleado = null;
		
		empleado = dao.findByPk(id);
		
		
		if (empleado != null) {
			System.out.printf("%s %s\t\t (%s) - %.2f� %n", empleado.getNombre(), empleado.getApellido(), 
					empleado.getFechaNacimiento().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
					empleado.getSueldo(),empleado.getDepartamento());
		} else {
			System.out.println("No existe ning�n registro con ese ID");
		}
		
		System.out.println("");
		
	}

	private static void nuevoEmpleado() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		int idDepartamento = 0;
		DaoDepartamento daoD = new DaoDepartamento();
		System.out.println("Inserci�n de un nuevo empleado");
		System.out.print("Introduzca el nombre del empleado: ");
		String nombre = teclado.nextLine();
		System.out.print("Introduzca los apellidos del empleado: ");
		String apellidos = teclado.nextLine();
		System.out.print("Introduzca la fecha de nacimiento (dd/mm/aaaa) : ");
		String strFecha = teclado.nextLine();
		LocalDate fecha = LocalDate.parse(strFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.print("Introduzca el sueldo del empleado: ");
		float sueldo = Float.parseFloat(teclado.nextLine());
		System.out.println("Introduzca el nombre del departamento al que pertenece");
		String nombreDepartamento=teclado.nextLine();
		while(daoD.findByNombre(nombreDepartamento)==null) {
			System.out.println("No existe ese departamento");
			System.out.println("Introduzca el nombre del departamento al que pertenece");
			nombreDepartamento=teclado.nextLine();
		}
	
		dao.insert(new Empleado(nombre, apellidos, fecha, sueldo,idDepartamento));
		System.out.println("Nuevo registro insertado");
		
		System.out.println("");

	}
	
	
	private static void actualizarUnEmpleado() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		System.out.println("Actualización de un empleado");
		System.out.print("Introduzca el ID del empleado: ");
		int id = Integer.parseInt(teclado.nextLine());
		
		Empleado empleado = null;
		
		empleado = dao.findByPk(id);
		
		
		if (empleado == null) {
			System.out.println("El empleado a modificar no est� registrado en la base de datos");			
		} else {
			System.out.print("Introduzca el nombre del empleado: ");
			String nombre = teclado.nextLine();
			System.out.print("Introduzca los apellidos del empleado: ");
			String apellidos = teclado.nextLine();
			System.out.print("Introduzca la fecha de nacimiento (dd/mm/aaaa) : ");
			String strFecha = teclado.nextLine();
			LocalDate fecha = LocalDate.parse(strFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			System.out.print("Introduzca el sueldo del empleado: ");
			float sueldo = Float.parseFloat(teclado.nextLine());
			System.out.print("Introduzca el departamento del empleado: ");
			int idDepartamento = Integer.parseInt(teclado.nextLine());
			empleado.setNombre(nombre);
			empleado.setApellido(apellidos);
			empleado.setFechaNacimiento(fecha);
			empleado.setSueldo(sueldo);
			
			
			dao.update(empleado);
			System.out.println("Registro actualizado");
			
			
		}
		
		System.out.println("");
		
	}

	private static void eliminarUnEmpleado() throws SQLException {
		DaoEmpleados dao = DaoEmpleados.getInstance();
		System.out.println("Borrado de un empleado");
		System.out.print("Introduzca el ID del empleado: ");
		int id = Integer.parseInt(teclado.nextLine());
		
		System.out.println("¿Está usted seguro de eliminar dicho registro? (S/N)");
		String opcion = teclado.nextLine();
		
		if (opcion.equalsIgnoreCase("S")) {
			
			try {
				dao.delete(id);
				System.out.println("Registro eliminado");
			} catch (EmpleadoException e) {
				System.out.println(e.getMessage());
			}
			
		
		}
		
		System.out.println("");
		
	}



	
}
