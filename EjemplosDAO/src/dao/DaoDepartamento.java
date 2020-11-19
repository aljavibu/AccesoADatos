
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singleton.DBConnection;

public class DaoDepartamento {

	/*
	 * PROPIEDADES Y M�TODOS SINGLETON
	 */

	private Connection con = null;

	private static DaoDepartamento instance = null;

	DaoDepartamento() throws SQLException {
		con = DBConnection.getConnection();
	}

	public static DaoDepartamento getInstance() throws SQLException {
		if (instance == null)
			instance = new DaoDepartamento();

		return instance;
	}

	/*
	 * M�TODOS PROPIOS DE LA CLASE DAO
	 */

	public List<Departamento> findAll() throws SQLException {

		List<Departamento> result = null;

		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM departamentos");) {
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (result == null)
					result = new ArrayList<>();

				result.add(new Departamento(rs.getInt("id"), rs.getString("nombre"), rs.getFloat("sueldominimo")));
			}
			rs.close();
		}

		return result;
	}

	public Departamento findByPk(int id) throws SQLException {

		Departamento result = null;
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM departamentos WHERE id = ?");) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = new Departamento(rs.getInt("id"), rs.getString("nombre"), rs.getFloat("sueldominimo"));
			}
			rs.close();
		}

		return result;

	}
	
	public Departamento findByNombre(String nombre) throws SQLException {

		Departamento result = null;
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM departamentos WHERE nombre = ?");) {

			ps.setString(1,nombre);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = new Departamento(rs.getInt("id"), rs.getString("nombre"), rs.getFloat("sueldominimo"));
			}
			rs.close();
		}

		return result;

	}

	public void delete(int id) throws SQLException, DepartamentoException {
		Departamento departamento = findByPk(id);
		if (departamento == null) {
			throw new DepartamentoException("No existe el departamento con id " + id);
		}

		try (PreparedStatement ps = con.prepareStatement("DELETE FROM departamentos WHERE id = ?");) {

			ps.setInt(1, id);

			ps.executeUpdate();

		}
	}

	public void update(Departamento d) throws SQLException {

		if (d.getId() == 0)
			return;

		try (PreparedStatement ps = con.prepareStatement(
				"UPDATE departamentos SET id = ?,nombre = ?, sueldominimo = ? WHERE id = ?");) {

			ps.setInt(1, d.getId());
			ps.setString(2, d.getNombre());
			ps.setFloat(3, d.getSueldoMinimo());

			ps.executeUpdate();

		}

	}

	public void cerrarSesion() throws SQLException {
		con.close();

	}

}
