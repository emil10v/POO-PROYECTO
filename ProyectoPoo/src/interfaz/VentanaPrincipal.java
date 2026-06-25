package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Prestamo;

public class VentanaPrincipal {

	private JFrame frame;
	private JButton btnPersonas;
	private JButton btnItems;
	private JButton btnCategorías;
	private JButton btnTipos;
	private JScrollPane scrollPane;
	private JButton btnCrear;
	private JButton btnVerItems;
	private JButton btnFinalizar;
	private JTable tablePrestamos;

	
	private void cargarPrestamos() {
		DefaultTableModel model = (DefaultTableModel) tablePrestamos.getModel();
		model.setRowCount(0);
		Controladora control = Controladora.getInstance()
		for(Prestamo p : control.getPrestamos()) {
			model.addRow(new Object[] {p.getNumero(),p.getPersona().getNombre(),p.getFechaPrestamo()
			});
		}
	}
	
	private Integer getPrestamoSeleccionado() {
		int fila = tablePrestamos.getSelectedRow();
		if(fila == -1) {
			JOptionPane.showMessageDialog(frame,"Seleccione un préstamo.");
			return null;
		}
		return (Integer)tablePrestamos.getValueAt(fila, 0);
	}
	
	private void crearPrestamo() {
		Controladora control = Controladora.getInstance();
		if (control.getItems().isEmpty() || control.getPersonas().isEmpty()))
			throw new Exception("Debe de existir al menos una Persona y un Item");
		VentanaCrearPrestamo ventana = new VentanaCrearPrestamo();
		ventana.setModal(true);
		ventana.setVisible(true);
		cargarPrestamos();
	}
	
	
	private void finalizarPrestamo() {
		try {
			Integer numero = getPrestamoSeleccionado();
			if(numero == null)
				return;
			Controladora control = Controladora.getInstance();
			control.terminarPrestamo(numero);
			cargarPrestamos();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(frame,e.getMessage());
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelAdmin = new JPanel();
		panelAdmin.setToolTipText("");
		tabbedPane.addTab("Administraci\u00F3n", null, panelAdmin, null);
		panelAdmin.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnPersonas = new JButton("Personas");
		btnPersonas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPersonas ventana = new VentanaPersonas();
				ventana.setVisible(true);
			}
		});
		panelAdmin.add(btnPersonas);
		
		btnItems = new JButton("Items");
		btnItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaItems ventana = new VentanaItems();
				ventana.setVisible(true);
			}
		});
		panelAdmin.add(btnItems);
		
		btnCategorías = new JButton("Categor\u00EDas");
		btnCategorías.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCategorias ventana = new VentanaCategorias();
				ventana.setVisible(true);
			}
		});
		panelAdmin.add(btnCategorías);
		
		btnTipos = new JButton("Tipos");
		btnTipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaTipos ventana = new VentanaTipos();
				ventana.setVisible(true);
			}
		});
		panelAdmin.add(btnTipos);
		
		JPanel panelPrestamos = new JPanel();
		tabbedPane.addTab("Prestamos", null, panelPrestamos, null);
		panelPrestamos.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 11, 313, 213);
		panelPrestamos.add(scrollPane);
		
		tablePrestamos = new JTable();
		tablePrestamos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00FAmero", "Persona", "Fecha"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tablePrestamos);
		
		btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearPrestamo();
			}
		});
		btnCrear.setBounds(313, 19, 118, 60);
		panelPrestamos.add(btnCrear);
		
		btnVerItems = new JButton("Ver Items");
		btnVerItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DetallePrestamo ventana = new DetallePrestamo();
				ventana.setVisible(true);
			}
		});
		btnVerItems.setBounds(313, 90, 118, 60);
		panelPrestamos.add(btnVerItems);
		
		btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalizarPrestamo();
			}
		});
		btnFinalizar.setBounds(313, 161, 118, 60);
		panelPrestamos.add(btnFinalizar);
		
		JPanel panelReportes = new JPanel();
		tabbedPane.addTab("Reportes", null, panelReportes, null);
		
		cargarPrestamos();
	}
}
