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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	    tipos.add(new Tipo("Genérico"));
	}
	private void verificarPersonaNoExiste(String telefono) throws Exception {
	    if(personas.containsKey(telefono))
	        throw new Exception("La persona ya existe.");
	}
	private void verificarPersonaExiste(String telefono) throws Exception {
	    if(!personas.containsKey(telefono))
	        throw new Exception("Persona no encontrada.");
	}
    private void revisarEmailValido(String email) throws Exception {
        Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher m = p.matcher(email);
        if(!m.matches())
        	throw new Exception("Email invalido");
    }  
    
	public static Controladora getInstance() {
	    if(instance == null)
	        instance = new Controladora();
	    return instance;
	}

	// Personas
	public void crearPersona(String nombre, String telefono, String email) throws Exception {
	    verificarPersonaNoExiste(telefono);
	    revisarEmailValido(email);
	    Persona p = new Persona(nombre, telefono, email);
	    personas.put(telefono, p);
	}

	public void editarPersona(String nombre, String telefono, String email) throws Exception {
		verificarPersonaExiste(telefono);
	    revisarEmailValido(email);
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
	public void crearPrestamo(String telefonoPersona) throws Exception {
		if (items.isEmpty())
			throw new Exception("No hay items para prestar.");
		if (personas.isEmpty())
			throw new Exception("No hay personas para hacer el prestamo.");
		Persona persona = getPersona(telefonoPersona);
		Prestamo prestamo = new Prestamo(consecutivoPrestamo, persona);
		prestamos.put(consecutivoPrestamo,prestamo);
		persona.agregarPrestamo(prestamo);
		consecutivoPrestamo++;
	}
	
	
	public void terminarPrestamo(Integer numPrestamo) throws Exception {
		Prestamo p = prestamos.get(numPrestamo);
	    if(p == null)
	        throw new Exception("Préstamo no encontrado.");
		p.finalizar();
		prestamos.remove(p);
	}
	

	public Prestamo getPrestamo(int numero) {
		return prestamos.get(numero);
	}

	public List<Prestamo> getPrestamos() {
		return new ArrayList<Prestamo>(prestamos.values());
	}

	public void agregarItemsPrestamo(List<Integer> codigosItems, Integer numPrestamo) throws Exception {
		Prestamo prestamo = prestamos.get(numPrestamo);
	    if(prestamo == null)
	        throw new Exception("Préstamo no encontrado.");
	    for(Integer codigo : codigosItems) {
	        Item item = getItem(codigo);
	        prestamo.agregarItem(item);
	    }
	}
	
	// Ítems
	public Item crearItem(String nombreTipo, String nombre) throws Exception {
	    Tipo tipo = getTipo(nombreTipo);
	    if(tipo == null)
	        throw new Exception("Debe existir al menos un tipo para crear un item.");
	    Item item = new Item(consecutivoItem, tipo, nombre);
	    items.put(consecutivoItem, item);
	    tipo.agregarItem(item);
	    consecutivoItem++;
	    return item;
	}

	public void editarItem(Integer codigo, String nombreTipo, String nombre, List<String> categorias) throws Exception {
	    Item item = getItem(codigo);
	    Tipo tipo = getTipo(nombreTipo);
	    Tipo anterior = item.getTipo();
	    if(anterior != tipo) {
	        anterior.eliminarItem(item);
	        tipo.agregarItem(item);
	        item.setTipo(tipo);
	    }
	    item.setNombre(nombre);
	    item.getCategorias().clear();
	    for(String nombreCategoria : categorias) {
	        Categoria categoria = getCategoria(nombreCategoria);
	        item.agregarCategoria(categoria);
	    }
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

	public Item getItem(int codigo) throws Exception {
	    Item item = items.get(codigo);
	    if(item == null)
	        throw new Exception("Item no encontrado.");
	    return item;
	}
	public void agregarCategoriaItem(Integer codigoItem, String nombreCategoria) throws Exception {
		Item item = getItem(codigoItem);
		Categoria categoria =
		getCategoria(nombreCategoria);
		item.agregarCategoria(categoria);
	}
	public void eliminarCategoriaItem(Integer codigoItem, String nombreCategoria)throws Exception {
		Item item = getItem(codigoItem);
		Categoria categoria =
		getCategoria(nombreCategoria);
		item.eliminarCategoria(categoria);
	}

	public List<Item> getItems() {
		 return new ArrayList<>(items.values());
	}

	// Tipos
	public void crearTipo(String nombre) throws Exception {
		  if(getTipo(nombre) != null)
		        throw new Exception("Tipo ya existe.");
		   tipos.add(new Tipo(nombre));
	}

	public Tipo getTipo(String nombre) {
	    for(Tipo t : tipos) {
	        if(t.getNombre().equalsIgnoreCase(nombre))
	            return t;
	    }
	    return null;
	}

	public List<Tipo> getTipos() {
		return tipos;
	}
	
	public void editarTipo(String nombre, String nuevo) throws Exception {
	Tipo tipo = getTipo(nombre);
	if(tipo == null)
		throw new Exception("Tipo no encontrado.");
	tipo.setNombre(nuevo);
	}
	
	public void borrarTipo(String nombre) throws Exception {
	    Tipo tipo = getTipo(nombre);
	    if(tipo == null)
	        throw new Exception("Tipo no encontrado.");
	    if(tipo.getNombre().equals("Genérico"))
	        throw new Exception("El tipo genérico no puede borrarse.");
	    Tipo generico = getTipo("Genérico");
	    List<Item> items = tipo.getItems();
	    for(Item item : items) {
	        item.setTipo(generico);
	    }
	    items.clear();
	    tipos.remove(tipo);
	}

	// Categorías
	public void crearCategoria(String nombre) throws Exception {
		  if(getCategoria(nombre) != null)
		        throw new Exception("Categoria ya existe.");
		  categorias.add(new Categoria(nombre));
	}

	public Categoria getCategoria(String nombre) throws Exception {
	    for(Categoria c : categorias) {
	        if(c.getNombre().equalsIgnoreCase(nombre))
	            return c;
	    }
	    return null;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}
	public void eliminarCategoria(String nombre) throws Exception {
		Categoria categoria = getCategoria(nombre);
		List<Item> items = categoria.getItems();
		for(Item i: items)
			categoria.eliminarItem(i);
		categorias.remove(categoria);
	}
	public void editarCategoria(String nombre,String nuevoNombre) throws Exception {
		Categoria c = getCategoria(nombre);
		if(c == null)
			throw new Exception("Categoría no encontrada.");
		c.setNombre(nuevoNombre);
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
	    if (persona.getPrestamos().isEmpty()) {
	    		reporte += "Sin préstamos registrados\n";
	    		return reporte;
	    }
	    for (Prestamo p : persona.getPrestamos().values()) {
	        reporte += "- Prestamo #" + p.getNumero() + " (" + p.getFechaPrestamo() + ")";
	        if (p.estaActivo())
	        	reporte += " (Activo)\n";
	        else 
	        	reporte += " (Terminado)\n";
	        reporte += "Items:\n";
	        for(Item i : p.getItems()) {
	            reporte += "   - " + i.getNombre() + "\n";
	        }
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
