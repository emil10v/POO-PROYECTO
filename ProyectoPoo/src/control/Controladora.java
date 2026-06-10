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
	
	public static Controladora getInstance() {
		
	}

	// Personas
	public void crearPersona(String nombre, String telefono, String email) {
		
	}

	public void editarPersona(String nombre, String telefono, String email) {
		
	}
	public void borrarPersona(String telefono) {
		
	}
	public Persona getPersona(String ) {
		
	}

	public List<Persona> getPersonas();

	// Préstamos
	public Prestamo crearPrestamo(Persona persona,
	                              List<Item> items,
	                              Alerta alerta);

	public void eliminarPrestamo(int numero);

	public Prestamo getPrestamo(int numero);

	public List<Prestamo> getPrestamos();

	public void terminarPrestamo(int numero);

	public void actualizarPrestamo(int numero);

	// Ítems
	public void crearItem(Tipo tipo,
	                      String nombre,
	                      String descripcion);

	public void editarItem(int codigo,
	                       Tipo tipo,
	                       String nombre,
	                       String descripcion);

	public void borrarItem(int codigo);

	public Item getItem(int codigo);

	public List<Item> getItems();

	// Tipos
	public void crearTipo(String nombre);

	public Tipo getTipo(String nombre);

	public List<Tipo> getTipos();

	// Categorías
	public void crearCategoria(String nombre);

	public Categoria getCategoria(String nombre);

	public List<Categoria> getCategorias();

	// Alertas
	public List<Alerta> mostrarAlertas();

	// Reportes
	public String generarReportePersona(String idPersona);

	public String generarReporteItem(int codigoItem);

	public String generarReporteCategoria(String nombreCategoria);

	public String generarReporteTipo(String nombreTipo);
	
	
}
