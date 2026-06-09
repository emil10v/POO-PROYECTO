package control;

import java.util.List;
import java.util.Map;

import logica.Categoria;
import logica.Item;
import logica.Persona;
import logica.Prestamo;
import logica.Tipo;

public class Controladora {
	private static Controladora instance = null; 
	private Map<String, Persona> personas;
	private Map<Integer, Prestamo> prestamos;
	private Map<Integer, Item> items;
	private List<Tipo> tipos;
	private List<Categoria> categorias;

	private int consecutivoPrestamo;
	private int consecutivoItem;
	
	
	
	
}
