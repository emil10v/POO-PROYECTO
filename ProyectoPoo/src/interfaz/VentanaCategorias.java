package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Categoria;

public class VentanaCategorias extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableCategorias;

	
	private void cargarCategorias() {
		DefaultTableModel model = (DefaultTableModel) tableCategorias.getModel();
		model.setRowCount(0);
		Controladora control = Controladora.getInstance();
		for(Categoria c : control.getCategorias()) {
			model.addRow(new Object[] { c.getNombre() });
		}
	}
	
	private String getCategoriaSeleccionada() {
		int fila = tableCategorias.getSelectedRow();
		if(fila == -1) {
			JOptionPane.showMessageDialog(this,"Seleccione una categoría.");
			return null;
		}
		return (String) tableCategorias.getValueAt(fila, 0);
	}
	
	private void crearCategoria() {
		try {
			String nombre = JOptionPane.showInputDialog(this,"Nombre de la categoría:");
			if(nombre == null || nombre.isBlank())
				return;
			Controladora.getInstance().crearCategoria(nombre);
			cargarCategorias();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}
	
	private void modificarCategoria() {
		try {
			String nombre = getCategoriaSeleccionada();
			if(nombre == null)
				return;
			String nuevo = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombre);
			if(nuevo == null || nuevo.isBlank())
				return;
			Controladora.getInstance().editarCategoria(nombre, nuevo);
			cargarCategorias();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this,	e.getMessage());
		}
	}
	
	private void borrarCategoria() {
		try {
			String nombre = getCategoriaSeleccionada();
			if(nombre == null)
				return;
			Controladora.getInstance().eliminarCategoria(nombre);
			cargarCategorias();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}
	
	private void consultarCategoria() {
		try {
			String nombre = getCategoriaSeleccionada();
			if(nombre == null)
				return;
			String reporte =Controladora.getInstance().generarReporteCategoria(nombre);
			JOptionPane.showMessageDialog(this,reporte,"Información de la categoría",JOptionPane.INFORMATION_MESSAGE);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaCategorias dialog = new VentanaCategorias();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCategorias() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 310, 230);
			contentPanel.add(scrollPane);
			{
				tableCategorias = new JTable();
				tableCategorias.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Categor\u00EDa"
					}
				) {
					boolean[] columnEditables = new boolean[] {
						false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				scrollPane.setViewportView(tableCategorias);
			}
		}
		{
			JButton btnCrear = new JButton("Crear");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearCategoria();
				}
			});
			btnCrear.setBounds(316, 11, 110, 40);
			contentPanel.add(btnCrear);
		}
		{
			JButton btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					modificarCategoria();
				}
			});
			btnModificar.setBounds(320, 62, 106, 40);
			contentPanel.add(btnModificar);
		}
		{
			JButton btnBorrar = new JButton("Borrar");
			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarCategoria();
				}
			});
			btnBorrar.setBounds(320, 113, 106, 40);
			contentPanel.add(btnBorrar);
		}
		{
			JButton btnConsultar = new JButton("Consultar");
			btnConsultar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					consultarCategoria();
				}
			});
			btnConsultar.setBounds(320, 163, 106, 40);
			contentPanel.add(btnConsultar);
		}
		cargarCategorias();
	}

}
