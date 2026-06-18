package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable  {
	private Integer codigo;
	private String nombre;
	private Tipo tipo;
	private List<Categoria> categorias;
	private Prestamo prestamo;
	
	public Item(Integer codigo, Tipo tipo, String nombre) {
		this.codigo = codigo;
		this.tipo = tipo;
		this.nombre = nombre;
		this.categorias = new ArrayList<Categoria>();
		this.prestamo = null;
	}
	public Item(Integer codigo,Tipo tipo, String nombre, List<Categoria> categorias) {
		this.codigo = codigo;
		this.tipo = tipo;
		this.nombre = nombre;
		this.categorias = categorias;
		this.prestamo = null;
		tipo.agregarItem(this);
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
		tipo.agregarItem(this);
	}
	
	public void agregarCategoria(Categoria categoria) {
		categorias.add(categoria);
		categoria.agregarItem(this);
	}
	
	public void eliminarCategoria(Categoria categoria) throws Exception {
		if (!categorias.remove(categoria))
		    throw new Exception("Categoria no encontrada.");
		categoria.eliminarItem(this);
	}
	
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
	
	public Prestamo getPrestamo() {
		return prestamo;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

}
