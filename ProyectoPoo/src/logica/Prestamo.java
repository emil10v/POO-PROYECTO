package logica;

import java.time.LocalDateTime;
import java.util.List;

public class Prestamo {
	private Integer numero;
	private LocalDateTime fechaPrestamo;
	private Persona persona;
	private List<Item> items;
	private Alerta alerta;
	
	public Prestamo(Integer numero,List<Item> items, Persona persona, Alerta alerta) {
		this.numero = numero;
		this.items = items;
		this.persona = persona;
		this.alerta = alerta;
	}

	public int getNumero() {
		return numero;
	}
	
	public LocalDateTime getFechaPrestamo() {
		return fechaPrestamo;
	}
	
	public Persona getPersona() {
		return persona;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public Alerta getAlerta() {
		return alerta;
	}
	
	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}
	
	public void agregarItem(Item item) {
		items.add(item);
	}
	
	public void eliminarItem(Item item) {
		
	}
	
	public String mostrarAlerta() {
		
	}
	
}
