package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
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
	private JPanel panelReportes;
	private JButton btnPersonas_2;
	private JButton btnItems_2;
	private JButton btnCategorías_2;
	private JButton btnTipos_2;

	
	private void cargarPrestamos() {
		DefaultTableModel model = (DefaultTableModel) tablePrestamos.getModel();
		model.setRowCount(0);
		Controladora control = Controladora.getInstance();
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
	private void crearPrestamo()  {
		try {
		Controladora control = Controladora.getInstance();
		if (control.getItemsDisponibles().isEmpty() || control.getPersonas().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Debe de existir al menos una Persona y un Item");
			return;
			}
		VentanaCrearPrestamo ventana = new VentanaCrearPrestamo();
		ventana.setModal(true);
		ventana.setVisible(true);
		cargarPrestamos();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(frame,e.getMessage());
		}
		
	}
	
	

    private void finalizarPrestamo() {
    	Integer numPrestamo = getPrestamoSeleccionado();
    	if (numPrestamo == null)
    		return;
        int confirmacion = JOptionPane.showConfirmDialog(
            frame,
            "¿Desea finalizar este préstamo? Todos los ítems serán retornados.",
            "Confirmar finalización",
            JOptionPane.YES_NO_OPTION
        );
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                Controladora control = Controladora.getInstance();
                control.terminarPrestamo(numPrestamo);
                cargarPrestamos();
                JOptionPane.showMessageDialog(frame, "Préstamo finalizado.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage());
            }
        }
    }

	
    public void mostrarAlertas() {
    	Controladora control = Controladora.getInstance();
        List<String> alertas = control.mostrarAlertas();
        if (!alertas.isEmpty()) {
            StringBuilder sb = new StringBuilder(" Alertas de préstamos:\n\n");
            for (String a : alertas)
                sb.append(a).append("\n---\n");
            JOptionPane.showMessageDialog(frame, sb.toString(), "Alertas", JOptionPane.WARNING_MESSAGE);
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
				     window.mostrarAlertas();
					
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
		try {
			Controladora.cargarDatos();
		} catch (Exception e) {
	        System.out.println("Sin datos previos, iniciando nuevo.");
	    }
		
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
		    	Integer numPrestamo = getPrestamoSeleccionado();
		    	if (numPrestamo == null)
		    		return;
				DetallePrestamo ventana = new DetallePrestamo(numPrestamo);
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
		
		panelReportes = new JPanel();
		panelReportes.setToolTipText("");
		tabbedPane.addTab("Reportes", null, panelReportes, null);
		panelReportes.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnPersonas_2 = new JButton("Personas");
		btnPersonas_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    VentanaReportes v = new VentanaReportes();
			    v.setVisible(true);
			}
		});
		panelReportes.add(btnPersonas_2);
		
		btnItems_2 = new JButton("Items");
		btnItems_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    VentanaReportes v = new VentanaReportes();
			    v.setVisible(true);
			}
		});
		panelReportes.add(btnItems_2);
		
		btnCategorías_2 = new JButton("Categor\u00EDas");
		btnCategorías_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    VentanaReportes v = new VentanaReportes();
			    v.setVisible(true);
			}
		});
		panelReportes.add(btnCategorías_2);
		
		btnTipos_2 = new JButton("Tipos");
		btnTipos_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    VentanaReportes v = new VentanaReportes();
			    v.setVisible(true);
			}
		});
		panelReportes.add(btnTipos_2);
		
		cargarPrestamos();
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowClosing(java.awt.event.WindowEvent e) {
	            try {
	                Controladora.guardarDatos();
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(frame, "Error al guardar datos: " + ex.getMessage());
	            }
	        }
	    });
	}
}
