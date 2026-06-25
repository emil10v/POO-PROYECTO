package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Item;
import logica.Persona;
import logica.Tipo;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaItems extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableItems;
	private JButton btnCrear;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnConsultar;
	
	private Integer obtenerCodigoSeleccionado() {
	    int fila = tableItems.getSelectedRow();
	    if (fila == -1) {
	        javax.swing.JOptionPane.showMessageDialog(
	                this,
	                "Debe seleccionar un item.",
	                "Error",
	                javax.swing.JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	    return (Integer) tableItems.getValueAt(fila, 0);
	}

	private void cargarItems() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tableItems.getModel();
		model.setRowCount(0);
		List<Item> listaItems = control.getItems();
		for (Item item : listaItems) {
			Object[] fila = new Object[] {item.getCodigo(), item.getNombre(), item.getTipo().getNombre()};
			model.addRow(fila);
		}
	}
	
	private void crearItem() {
	    try {
	        Controladora control = Controladora.getInstance();
	        if(control.getTipos().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Debe crear al menos un tipo antes de crear items.");
	            return;
	        }
	        VentanaEditarItem dialog = new VentanaEditarItem();
	        dialog.setModal(true);
	        dialog.setVisible(true);
	        cargarItems();
	    } catch(Exception e) {
	        JOptionPane.showMessageDialog(this, e.getMessage());
	    }
	}
	
	private void modificarItem() {
	    int fila = tableItems.getSelectedRow();
	    if(fila == -1) {
	        JOptionPane.showMessageDialog(this,"Seleccione un item.");
	        return;
	    }
	    Integer codigo = (Integer) tableItems.getValueAt(fila,0);
	    VentanaEditarItem ventana = new VentanaEditarItem(codigo);
	    ventana.setModal(true);
	    ventana.setVisible(true);
	    cargarItems();
	}
	
	private void borrarItem() {
	    try {
	        Integer codigo = obtenerCodigoSeleccionado();
	        if (codigo == null)
	            return;
	        int opcion = javax.swing.JOptionPane.showConfirmDialog(this,
	                "¿Desea borrar el item?","Confirmar",javax.swing.JOptionPane.YES_NO_OPTION);
	        if (opcion != javax.swing.JOptionPane.YES_OPTION)
	            return;
	        Controladora control = Controladora.getInstance();
	        control.borrarItem(codigo);
	        cargarItems();
	    } catch (Exception e) {
	        javax.swing.JOptionPane.showMessageDialog(this,e.getMessage(), "Error",
	        		javax.swing.JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void consultarItem() {
	    try {
	        Integer codigo = obtenerCodigoSeleccionado();
	        if(codigo == null)
	            return;
	        Controladora control = Controladora.getInstance();
	        String reporte = control.generarReporteItem(codigo);
	        JOptionPane.showMessageDialog(this,reporte,
	                "Información del Item",
	                JOptionPane.INFORMATION_MESSAGE);
	    } catch(Exception e) {
	        JOptionPane.showMessageDialog(this,e.getMessage(),"Error",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaItems dialog = new VentanaItems();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaItems() {
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
				tableItems = new JTable();
				tableItems.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"C\u00F3digo", "Nombre", "Tipo"
					}
				) {
					boolean[] columnEditables = new boolean[] {
						false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				tableItems.getColumnModel().getColumn(0).setPreferredWidth(73);
				tableItems.getColumnModel().getColumn(2).setPreferredWidth(72);
				scrollPane.setViewportView(tableItems);
			}
		}
		{
			btnCrear = new JButton("Crear");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearItem();
				}
			});
			btnCrear.setBounds(310, 11, 126, 40);
			contentPanel.add(btnCrear);
		}
		{
			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					modificarItem();
				}
			});
			btnModificar.setBounds(310, 62, 126, 40);
			contentPanel.add(btnModificar);
		}
		{
			btnBorrar = new JButton("Borrar");
			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarItem();
				}
			});
			btnBorrar.setBounds(310, 113, 126, 40);
			contentPanel.add(btnBorrar);
		}
		{
			btnConsultar = new JButton("Consultar");
			btnConsultar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					consultarItem();
				}
			});
			btnConsultar.setBounds(310, 163, 126, 40);
			contentPanel.add(btnConsultar);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		cargarItems();
	}

}
