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
import logica.Item;
import logica.Tipo;

public class VentanaTipos extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableTipos;
	
	
	private void cargarTipos() {
	    Controladora control = Controladora.getInstance();
	    DefaultTableModel model = (DefaultTableModel) tableTipos.getModel();
	    model.setRowCount(0);
	    for(Tipo tipo : control.getTipos()) {
	        model.addRow(new Object[] {tipo.getNombre()
	        });
	    }
	}
	
	private String getTipoSeleccionado() {
	    int fila = tableTipos.getSelectedRow();
	    if(fila == -1) {JOptionPane.showMessageDialog(this,"Seleccione un tipo.");
	        return null;
	    }
	    return (String)tableTipos.getValueAt(fila, 0);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaTipos dialog = new VentanaTipos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void crearTipo() {
	    try {
	        String nombre = JOptionPane.showInputDialog(this, "Nombre del tipo:");
	        if(nombre == null || nombre.isBlank())
	            return;
	        Controladora control = Controladora.getInstance();
	        control.crearTipo(nombre);
	        cargarTipos();
	    } catch(Exception e) {
	        JOptionPane.showMessageDialog(this, e.getMessage());
	    }
	}
	
	private void modificarTipo() {
	    try {
	        String nombreActual = getTipoSeleccionado();
	        if(nombreActual == null)
	            return;
	        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre: ",nombreActual);
	        if(nuevoNombre == null || nuevoNombre.isBlank())
	            return;
	        Controladora control = Controladora.getInstance();
	        control.editarTipo(nombreActual, nuevoNombre);
	        cargarTipos();

	    } catch(Exception e) {

	        JOptionPane.showMessageDialog(
	                this,
	                e.getMessage());
	    }
	}
	
	private void borrarTipo() {
	    try {
	        String nombre = getTipoSeleccionado();
	        if(nombre == null)
	            return;
	        Tipo tipo = Controladora.getInstance().getTipo(nombre);
	        String mensaje ="Los siguientes items pasarán al tipo Genérico:\n\n";
	        for(Item item : tipo.getItems()) {
	            mensaje += "- " + item.getNombre() + "\n";
	        }
	        int opcion =JOptionPane.showConfirmDialog(this,mensaje,"Confirmar borrado",JOptionPane.YES_NO_OPTION);
	        if(opcion != JOptionPane.YES_OPTION)
	            return;
	        Controladora.getInstance().borrarTipo(nombre);
	        cargarTipos();
	    } catch(Exception e) {
	        JOptionPane.showMessageDialog(this,e.getMessage());
	    }
	}
	
	
	private void consultarTipo() {
	    try {
	        String nombre =  getTipoSeleccionado();
	        if(nombre == null)
	            return;
	        Controladora control =  Controladora.getInstance();
	        String reporte = control.generarReporteTipo(nombre);
	        JOptionPane.showMessageDialog(this,reporte,"Información del tipo",JOptionPane.INFORMATION_MESSAGE);
	    } catch(Exception e) {JOptionPane.showMessageDialog(this,e.getMessage());}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaTipos() {
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
				tableTipos = new JTable();
				tableTipos.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Tipo"
					}
				) {
					boolean[] columnEditables = new boolean[] {
						false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				scrollPane.setViewportView(tableTipos);
			}
		}
		{
			JButton btnCrear = new JButton("Crear");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearTipo();
				}
			});
			btnCrear.setBounds(316, 11, 110, 40);
			contentPanel.add(btnCrear);
		}
		{
			JButton btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					modificarTipo();
				}
			});
			btnModificar.setBounds(320, 62, 106, 40);
			contentPanel.add(btnModificar);
		}
		{
			JButton btnBorrar = new JButton("Borrar");
			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarTipo();
					
				}
			});
			btnBorrar.setBounds(320, 113, 106, 40);
			contentPanel.add(btnBorrar);
		}
		{
			JButton btnConsultar = new JButton("Consultar");
			btnConsultar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					consultarTipo();
				}
			});
			btnConsultar.setBounds(320, 163, 106, 40);
			contentPanel.add(btnConsultar);
		}
		cargarTipos();
	}

}
