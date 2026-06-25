package interfaz;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Controladora;
import logica.Categoria;
import logica.Item;
import logica.Tipo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaEditarItem extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNombre;
	private JTextField textNombre;
	private JLabel lblTipo;
	private JComboBox<String> comboTipos;
	private JPanel panelCategorias;
	private JPanel buttonPane_1;
	private JButton okButton_1;
	private JButton cancelButton_1;
	private Integer codigoItem = null;

	private void cargarTipos() {
	    Controladora control = Controladora.getInstance();
	    comboTipos.removeAllItems();
	    for(Tipo t : control.getTipos()) {
	        comboTipos.addItem(t.getNombre());
	    }
	}	
	private void cargarCategorias() {
	    Controladora control = Controladora.getInstance();
	    panelCategorias.removeAll();
	    for(Categoria c : control.getCategorias()) {
	        JCheckBox check = new JCheckBox(c.getNombre());
	        panelCategorias.add(check);
	    }
	    panelCategorias.revalidate();
	    panelCategorias.repaint();
	}
	private void guardarItem() {
	    try {
	        Controladora control = Controladora.getInstance();
	        String nombre = textNombre.getText();
	        String tipo = (String) comboTipos.getSelectedItem();
	        if(codigoItem == null) {
	            Item item = control.crearItem(tipo, nombre);
	            for(Component comp : panelCategorias.getComponents()) {
	                JCheckBox check = (JCheckBox) comp;
	                if(check.isSelected()) {
	                    control.agregarCategoriaItem(item.getCodigo(),check.getText());
	                }
	            }
	        } else {
	            List<String> categorias = new ArrayList<>();
	            for(Component comp : panelCategorias.getComponents()) {
	                JCheckBox check = (JCheckBox) comp;
	                if(check.isSelected()) 
	                    categorias.add(check.getText());
	            }
	            control.editarItem(codigoItem, tipo, nombre, categorias);
	        }
	        dispose();
	    } catch(Exception e) {
	        JOptionPane.showMessageDialog(this,e.getMessage());
	    }
	}
	private void cargarItem() {
	    try {
	        Controladora control = Controladora.getInstance();
	        Item item = control.getItem(codigoItem);
	        textNombre.setText(item.getNombre());
	        comboTipos.setSelectedItem(item.getTipo().getNombre());
	        for(Component comp : panelCategorias.getComponents()) {
	            JCheckBox check =(JCheckBox) comp;
	            for(Categoria categoria : item.getCategorias()) {
	                if(check.getText().equals(categoria.getNombre())) {
	                    check.setSelected(true);
	                }
	            }
	        }
	    } catch(Exception e) {
	        JOptionPane.showMessageDialog(this,e.getMessage());
	    }
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaEditarItem dialog = new VentanaEditarItem();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Create the dialog.
	 */
	
	public VentanaEditarItem() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 11, 65, 14);
		contentPanel.add(lblNombre);
		
		lblTipo = new JLabel("Tipo: ");
		lblTipo.setBounds(10, 40, 44, 14);
		contentPanel.add(lblTipo);
		
		JLabel labelCategorias = new JLabel("Categorías: ");
		labelCategorias.setBounds(10, 63, 75, 14);
		contentPanel.add(labelCategorias);
		
		textNombre = new JTextField();
		textNombre.setBounds(70, 8, 286, 22);
		contentPanel.add(textNombre);
		textNombre.setColumns(10);
		
		comboTipos = new JComboBox<String>();
		comboTipos.setBounds(70, 38, 286, 22);
		contentPanel.add(comboTipos);
		
		panelCategorias = new JPanel();
		panelCategorias.setBounds(10, 77, 357, 142);
		contentPanel.add(panelCategorias);
			
			buttonPane_1 = new JPanel();
			buttonPane_1.setBounds(0, 230, 436, 33);
			contentPanel.add(buttonPane_1);
			buttonPane_1.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			okButton_1 = new JButton("OK");
			okButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardarItem();
				}
			});
			okButton_1.setActionCommand("OK");
			buttonPane_1.add(okButton_1);
			
			cancelButton_1 = new JButton("Cancel");
			cancelButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton_1.setActionCommand("Cancel");
			buttonPane_1.add(cancelButton_1);
		
		cargarTipos();
		cargarCategorias();
	}
	
	public VentanaEditarItem(Integer codigoItem) {
	    this();
	    this.codigoItem = codigoItem;
	    cargarItem();
	}
}
