package logica;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
	private String nombre;
	private List<Item> items;
	
	public Categoria(String nombre) {
		this.nombre = nombre;
		this.items = new ArrayList<Item>();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Item> getItems() {
		return items;
	}
	public void agregarItem(Item item) {
		items.add(item);
	}
	public void eliminarItem(Item item) throws Exception {
		for	(Item i : items) {
			if (i == item)
				items.remove(i);
				return;
		}
		throw new Exception("Item no encontrada.");
	}
}
