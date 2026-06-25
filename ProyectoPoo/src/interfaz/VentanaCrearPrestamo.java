package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class VentanaCrearPrestamo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaCrearPrestamo dialog = new VentanaCrearPrestamo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCrearPrestamo() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		{
			JPanel contentPanel = new JPanel();
			contentPanel.setBounds(0, 0, 436, 263);
			contentPanel.setLayout(null);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.NORTH);
			{
				JLabel lblNombre = new JLabel("Nombre: ");
				lblNombre.setBounds(10, 11, 65, 14);
				contentPanel.add(lblNombre);
			}
			{
				JLabel lblTipo = new JLabel("Tipo: ");
				lblTipo.setBounds(10, 40, 44, 14);
				contentPanel.add(lblTipo);
			}
			{
				JLabel labelCategorias = new JLabel("Categor\u00EDas: ");
				labelCategorias.setBounds(10, 63, 75, 14);
				contentPanel.add(labelCategorias);
			}
			{
				textField = new JTextField();
				textField.setColumns(10);
				textField.setBounds(70, 8, 286, 22);
				contentPanel.add(textField);
			}
			{
				JComboBox<String> comboTipos = new JComboBox<String>();
				comboTipos.setBounds(70, 38, 286, 22);
				contentPanel.add(comboTipos);
			}
			{
				JPanel panelCategorias = new JPanel();
				panelCategorias.setBounds(10, 77, 357, 142);
				contentPanel.add(panelCategorias);
			}
			{
				JPanel buttonPane_1 = new JPanel();
				buttonPane_1.setBounds(0, 230, 436, 33);
				contentPanel.add(buttonPane_1);
				buttonPane_1.setLayout(new FlowLayout(FlowLayout.RIGHT));
				{
					JButton okButton_1 = new JButton("OK");
					okButton_1.setActionCommand("OK");
					buttonPane_1.add(okButton_1);
				}
				{
					JButton cancelButton_1 = new JButton("Cancel");
					cancelButton_1.setActionCommand("Cancel");
					buttonPane_1.add(cancelButton_1);
				}
			}
		}
	}

}
