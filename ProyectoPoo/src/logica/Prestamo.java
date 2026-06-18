package logica;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Prestamo implements Serializable {
	private Integer numero;
	private LocalDateTime fechaPrestamo;
	private Persona persona;
	private List<Item> items;
	private Alerta alerta;
	
	public Prestamo(Integer numero, Persona persona) {
		this.numero = numero;
		this.items = new ArrayList<Item>();
		this.persona = persona;
		this.fechaPrestamo = LocalDateTime.now();
		this.alerta = null;
	}

	public int getNumero() {
		return numero;
	}
	
	public Persona getPersona() {
		return persona;
	}
	
	public LocalDateTime getFechaPrestamo() {
		return fechaPrestamo;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void agregarItem(Item item) throws Exception {
		if (item.getPrestamo() != null)
			throw new Exception("El item " + item.getNombre() + " ya se encuentra en un prestamo.");
		items.add(item);
		item.setPrestamo(this);
	}
	
	public void eliminarItem(Item item) throws Exception {
		if (items.remove(item)) {
		    item.setPrestamo(null);
		} else {
		    throw new Exception("Item no encontrado.");
		}
	}
		
	public Alerta getAlerta() {
		return alerta;
	}
	
	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}
	
	public String mostrarAlerta() {
	    if (alerta == null)
	        return null;
	    return alerta.mostrar();
	}
	public void finalizar() {
	    for (Item i : items) {
	        i.setPrestamo(null);
	    }
	    items.clear();
	    if (alerta != null)
	        alerta.desactivar();
	}
}
