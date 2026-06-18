package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
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

public class VentanaPrincipal {

	private JFrame frame;
	private JButton btnPersonas;
	private JButton btnItems;
	private JButton btnCategorías;
	private JButton btnTipos;
	private JScrollPane scrollPane;
	private JButton btnCrear;
	private JButton btnAgregarItem;
	private JButton btnRetornarItem;
	private JButton btnFinalizar;

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
		
		JTable tablePrestamos = new JTable();
		scrollPane.setViewportView(tablePrestamos);
		
		btnCrear = new JButton("Crear");
		btnCrear.setBounds(313, 19, 118, 40);
		panelPrestamos.add(btnCrear);
		
		btnAgregarItem = new JButton("Agregar Item");
		btnAgregarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAgregarItem.setBounds(313, 70, 118, 40);
		panelPrestamos.add(btnAgregarItem);
		
		btnRetornarItem = new JButton("Retornar Item");
		btnRetornarItem.setBounds(313, 121, 118, 40);
		panelPrestamos.add(btnRetornarItem);
		
		btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFinalizar.setBounds(313, 172, 118, 40);
		panelPrestamos.add(btnFinalizar);
		
		JPanel panelReportes = new JPanel();
		tabbedPane.addTab("Reportes", null, panelReportes, null);
	}
}
