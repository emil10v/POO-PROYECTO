package logica;

import java.util.ArrayList;
import java.util.List;

public class Item {
	private String nombre;
	private Tipo tipo;
	private List<Categoria> categorias;
	private Prestamo prestamo;
	
	public Item(Tipo tipo, String nombre) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.categorias = new ArrayList<Categoria>();
		this.prestamo = null;
	}
	public Item(Tipo tipo, String nombre, List<Categoria> categorias) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.categorias = categorias;
		this.prestamo = null;
	}

	public String getNombre() {
		return nombre;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public void agregarCategoria(Categoria categoria) {
		categorias.add(categoria);
	}
	public void eliminarCategoria(Categoria categoria) throws Exception {
		if (!categorias.remove(categoria))
		    throw new Exception("Categoria no encontrada.");
	}
	
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
	
	public Prestamo getPrestamo() {
		return prestamo;
	}

}
