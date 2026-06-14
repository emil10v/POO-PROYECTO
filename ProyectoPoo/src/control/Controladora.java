package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import logica.Alerta;
import logica.Categoria;
import logica.Item;
import logica.Persona;
import logica.Prestamo;
import logica.Tipo;

public class Controladora implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Controladora instance = null; 
	private Map<String, Persona> personas;
	private Map<Integer, Prestamo> prestamos;
	private Map<Integer, Item> items;
	private List<Tipo> tipos;
	private List<Categoria> categorias;

	private int consecutivoPrestamo;
	private int consecutivoItem;
	
	private Controladora() {
	    personas = new TreeMap<>();
	    prestamos = new TreeMap<>();
	    items = new TreeMap<>();
	    tipos = new ArrayList<>();
	    categorias = new ArrayList<>();
	    this.consecutivoItem = 1;
	    this.consecutivoPrestamo = 1;
	}
	private void verificarPersonaNoExiste(String telefono) throws Exception {
	    if(personas.containsKey(telefono))
	        throw new Exception("La persona ya existe.");
	}
	private void verificarPersonaExiste(String telefono) throws Exception {
	    if(!personas.containsKey(telefono))
	        throw new Exception("Persona no encontrada.");
	}
	
	public static Controladora getInstance() {
	    if(instance == null)
	        instance = new Controladora();
	    return instance;
	}

	// Personas
	public void crearPersona(String nombre, String telefono, String email) throws Exception {
	    verificarPersonaNoExiste(telefono);
	    Persona p = new Persona(nombre, telefono, email);
	    personas.put(telefono, p);
	}

	public void editarPersona(String nombre, String telefono, String email) throws Exception {
		verificarPersonaExiste(telefono)
	    Persona p = personas.get(telefono);
	    p.setNombre(nombre);
	    p.setEmail(email);
	}
	public void borrarPersona(String telefono) throws Exception {
		verificarPersonaExiste(telefono);
	    Persona persona = personas.get(telefono);
	    for(Prestamo p : persona.getPrestamos().values()) {
	        prestamos.remove(p.getNumero());
	    }
	    personas.remove(telefono);
	}
	public Persona getPersona(String telefono) {
	    return personas.get(telefono);
	}

	public List<Persona> getPersonas() {
		  return new ArrayList<>(personas.values());
	}

	// Préstamos
	public void crearPrestamo(Persona persona, List<Item> items) throws Exception {
		for(Item item : items) {
		    if(item.getPrestamo() != null) 
		    	throw new Exception(item.getNombre()+ " ya está prestado.");
		}
		Prestamo prestamo = new Prestamo(consecutivoPrestamo, items, persona);
		prestamos.put(consecutivoPrestamo,prestamo);
		persona.agregarPrestamo(prestamo);
		for(Item item : items) {
		    item.setPrestamo(prestamo);
		}
		consecutivoPrestamo++;
	}
	
	
	public void terminarPrestamo(Integer numPrestamo) throws Exception {
		Prestamo p = prestamos.get(numPrestamo);
	    if(p == null)
	        throw new Exception("Préstamo no encontrado.");
		p.finalizar();
		prestamos.remove(numPrestamo);
	}
	

	public Prestamo getPrestamo(int numero) {
		return prestamos.get(numero);
	}

	public List<Prestamo> getPrestamos() {
		return new ArrayList<Prestamo>(prestamos.values());
	}

	public void agregarItemsPrestamo(List<Item> items, Prestamo prestamo) throws Exception {
		for (Item i : items) {
			prestamo.agregarItem(i);
		}
	}
	
	// Ítems
	public void crearItem(Tipo tipo, String nombre) throws Exception {
		Item item = new Item(consecutivoItem, tipo, nombre);
		items.put(consecutivoItem, item);
		tipo.agregarItem(item);
		items.put(consecutivoItem,item);
		consecutivoItem++;
	}

	public void editarItem(int codigo, Tipo tipo, String nombre) throws Exception {
	    Item item = getItem(codigo);
	    Tipo anterior = item.getTipo();
	    if (anterior != tipo) {
	        anterior.eliminarItem(item);
	        tipo.agregarItem(item);
	        item.setTipo(tipo);
	    }
	    item.setNombre(nombre);
	    item.setNombre(nombre);
	}

	public void borrarItem(int codigo) throws Exception {
		    Item item = items.get(codigo);
		    if(item.getPrestamo() != null)
		        throw new Exception("El item está prestado.");
		    item.getTipo().eliminarItem(item);
		    for(Categoria c : item.getCategorias()) {
		        c.eliminarItem(item);
		    }
		    items.remove(codigo);
		}

	public Item getItem(int codigo) {
		return items.get(codigo);
	}

	public List<Item> getItems() {
		 return new ArrayList<>(items.values());
	}

	// Tipos
	public void crearTipo(String nombre) throws Exception {
		  if(getTipo(nombre) != null)
		        throw new Exception("Tipo duplicado.");
		    tipos.add(new Tipo(nombre));
	}

	public Tipo getTipo(String nombre) throws Exception {
	    for(Tipo t : tipos) {
	        if(t.getNombre().equals(nombre))
	            return t;
	    }
	    throw new Exception("Tipo no existe");
	}

	public List<Tipo> getTipos() {
		return tipos;
	}

	// Categorías
	public void crearCategoria(String nombre) {
		   categorias.add(new Categoria(nombre));
	}

	public Categoria getCategoria(String nombre) throws Exception {
	    for(Categoria c : categorias) {
	        if(c.getNombre().equals(nombre))
	            return c;
	    }
	    throw new Exception("Categoria no existe");
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	// Alertas
	public void crearAlertaPrestamo(Integer numPrestamo, boolean recurrente, int minutos) throws Exception {
	    Prestamo p = getPrestamo(numPrestamo);
	    if (p == null)
	        throw new Exception("Préstamo no encontrado.");
	    Alerta alerta = new Alerta(recurrente, minutos, p);
	    p.setAlerta(alerta);
	}
	
	public List<String> mostrarAlertas() {
	    List<String> alertas = new ArrayList<>();
	    for(Prestamo p : prestamos.values()) {
	        String mensaje = p.mostrarAlerta();
	        if(mensaje != null)
	            alertas.add(mensaje);
	    }
	    return alertas;
	}

	// Reportes
	public String generarReportePersona(String telefono) throws Exception {
	    verificarPersonaExiste(telefono);
	    Persona persona = personas.get(telefono);
	    String reporte = "";
	    reporte += "--- REPORTE PERSONA ---\n";
	    reporte += "Nombre: " + persona.getNombre() + "\n";
	    reporte += "Telefono: " + persona.getTelefono() + "\n";
	    reporte += "Email: " + persona.getEmail() + "\n\n";
	    reporte += "Prestamos:\n";
	    for (Prestamo p : persona.getPrestamos().values()) {
	        reporte += "- Prestamo #" + p.getNumero();
	        reporte += " (" + p.getFechaPrestamo() + ")\n";
	    }
	    return reporte;
	}

	public String generarReporteItem(int codigoItem) throws Exception {

	    Item item = getItem(codigoItem);

	    String reporte = "";
	    reporte += "--- REPORTE ITEM ---\n";
	    reporte += "Codigo: " + item.getCodigo() + "\n";
	    reporte += "Nombre: " + item.getNombre() + "\n";
	    reporte += "Tipo: " + item.getTipo().getNombre() + "\n";
	    reporte += "\nCategorias:\n";
	    for (Categoria c : item.getCategorias()) {
	        reporte += "- " + c.getNombre() + "\n";
	    }
	    if (item.getPrestamo() != null) {
	        reporte += "\nEstado: PRESTADO\n";
	        reporte += "Prestamo #: "  + item.getPrestamo().getNumero() + "\n";
	    }
	    else {
	        reporte += "\nEstado: DISPONIBLE\n";
	    }
	    return reporte;
	}

	public String generarReporteCategoria(String nombreCategoria) throws Exception {
	    Categoria categoria = getCategoria(nombreCategoria);
	    String reporte = "";
	    reporte += "--- REPORTE CATEGORIA ---\n";
	    reporte += "Nombre: " + categoria.getNombre() + "\n\n";
	    reporte += "Items:\n";
	    for (Item item : categoria.getItems()) {
	        reporte += "- " + item.getNombre() + "\n";
	    }
	    return reporte;
	}

	public String generarReporteTipo(String nombreTipo) throws Exception {
	    Tipo tipo = getTipo(nombreTipo);
	    String reporte = "";
	    reporte += "--- REPORTE TIPO ---\n";
	    reporte += "Nombre: " + tipo.getNombre() + "\n\n";
	    reporte += "Items:\n";
	    for (Item item : tipo.getItems()) {
	        reporte += "- " + item.getNombre() + "\n";
	    }
	    return reporte;
	}
	
	// Datos
	public static void guardarDatos() throws IOException {
	    FileOutputStream file = new FileOutputStream("DatosPrestamos.dat");
	    ObjectOutputStream stream = new ObjectOutputStream(file);
	    stream.writeObject(instance);
	    stream.close();
	    file.close();
	}
	public static void cargarDatos() throws IOException, ClassNotFoundException {
	    FileInputStream file = new FileInputStream("DatosPrestamos.dat");
	    ObjectInputStream stream = new ObjectInputStream(file);
	    instance = (Controladora) stream.readObject();
	    stream.close();
	    file.close();
	}
}
