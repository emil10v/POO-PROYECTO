package logica;

import java.util.List;
import java.util.Map;

public class Persona {
	private String nombre;
	private String telefono;
	private String email;
	private Map<Integer, Prestamo> prestamos;
	
	public Persona(String nombre, String telefono, String email) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public void agregarPrestamo(Prestamo prestamo) {
		Integer numPrestamo = prestamo.getNumero();
		prestamos.put(numPrestamo, prestamo);
	}
	public void eliminarPrestamo(Prestamo prestamo) { 
		
	}

	public List<Prestamo> getPrestamos() {
		
	}
	
}
