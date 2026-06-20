package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Persona;

public class VentanaPersonas extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tablePersonas;
	private JButton btnModificar;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnConsultar;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaPersonas dialog = new VentanaPersonas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarPersonas() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablePersonas.getModel();
		model.setRowCount(0);
		List<Persona> listaPersonas = control.getPersonas();
		for (Persona persona : listaPersonas) {
			Object[] fila = new Object[] { persona.getNombre(), persona.getTelefono(), persona.getEmail() };
			model.addRow(fila);
		}
	}
	
	private void agregarPersona() {
	    try {
	        String nombre = JOptionPane.showInputDialog(frame,"Nombre:");
	        if (nombre == null) return;
	        String telefono = JOptionPane.showInputDialog(frame,"Número de teléfono:");
	        if (telefono == null) return;
	        String email = JOptionPane.showInputDialog(frame,"Email:");
	        if (email == null) return;
	        Controladora control = Controladora.getInstance();
	        control.crearPersona(nombre, telefono, email);
	        cargarPersonas();
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(
	        		frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private String getTelefonoSeleccionado() {
	    int fila = tablePersonas.getSelectedRow();
	    if (fila == -1) {
	        JOptionPane.showMessageDialog(
	                this,
	                "Debe seleccionar una persona.",
	                "Error",
	                JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	    return (String) tablePersonas.getValueAt(fila, 1);
	}
	
	private void modificarPersona() {
	    try {
	        String telefono = getTelefonoSeleccionado();
	        if (telefono == null)
	            return;
	        Controladora control = Controladora.getInstance();
	        Persona persona = control.getPersona(telefono);
	        String nombre = JOptionPane.showInputDialog( this, "Nombre:", persona.getNombre());
	        if (nombre == null) return;
	        String email = JOptionPane.showInputDialog(this,"Email:",persona.getEmail());
	        if (email == null)
	            return;
	        control.editarPersona(nombre, telefono, email);
	        cargarPersonas();
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(
	                this,
	                e.getMessage(),
	                "Error",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void consultarPersona() {
	    try {
	        String telefono = getTelefonoSeleccionado();
	        if(telefono == null)
	            return;
	        Controladora control = Controladora.getInstance();
	        String reporte = control.generarReportePersona(telefono);
	        JOptionPane.showMessageDialog(
	                this,
	                reporte,
	                "Préstamos de la persona",
	                JOptionPane.INFORMATION_MESSAGE);
	    } catch(Exception e) {
	        JOptionPane.showMessageDialog(
	                this,
	                e.getMessage(),
	                "Error",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void borrarPersona() {
	    try {
	        String telefono = getTelefonoSeleccionado();
	        if (telefono == null)
	            return;
	        int opcion = JOptionPane.showConfirmDialog(
	                this,
	                "¿Desea borrar la persona seleccionada?",
	                "Confirmar",
	                JOptionPane.YES_NO_OPTION);
	        if (opcion != JOptionPane.YES_OPTION)
	            return;
	        Controladora control = Controladora.getInstance();
	        control.borrarPersona(telefono);
	        cargarPersonas();

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(
	                this,
	                e.getMessage(),
	                "Error",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}

	public VentanaPersonas() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 313, 230);
		contentPanel.add(scrollPane);
		
		tablePersonas = new JTable();
		tablePersonas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Tel\u00E9fono", "Email"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tablePersonas.getColumnModel().getColumn(0).setPreferredWidth(150);
		tablePersonas.getColumnModel().getColumn(1).setPreferredWidth(150);
		tablePersonas.getColumnModel().getColumn(2).setPreferredWidth(150);
		tablePersonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tablePersonas);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarPersona();
			}
		});
		btnAgregar.setBounds(323, 11, 113, 34);
		contentPanel.add(btnAgregar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarPersona();
			}
		});
		btnModificar.setBounds(323, 56, 113, 34);
		contentPanel.add(btnModificar);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarPersona();
			}
		});
		btnBorrar.setBounds(323, 101, 113, 34);
		contentPanel.add(btnBorrar);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarPersona();
			}
		});
		btnConsultar.setBounds(323, 146, 113, 34);
		contentPanel.add(btnConsultar);
		cargarPersonas();
	}
}
