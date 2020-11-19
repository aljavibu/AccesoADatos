package dao;

public class Departamento {

	private int id;
	private String nombre;
	private float sueldoMinimo;
	
	public Departamento(int id, String nombre, float sueldoMinimo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.sueldoMinimo = sueldoMinimo;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getSueldoMinimo() {
		return sueldoMinimo;
	}

	public void setSueldoMinimo(float sueldoMinimo) {
		this.sueldoMinimo = sueldoMinimo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + Float.floatToIntBits(sueldoMinimo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Float.floatToIntBits(sueldoMinimo) != Float.floatToIntBits(other.sueldoMinimo))
			return false;
		return true;
	}

	
}
