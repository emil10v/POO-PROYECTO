package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaItems extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableItems;
	private JButton btnCrear;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnConsultar;

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
				scrollPane.setViewportView(tableItems);
			}
		}
		{
			btnCrear = new JButton("Crear");
			btnCrear.setBounds(316, 11, 110, 40);
			contentPanel.add(btnCrear);
		}
		{
			btnModificar = new JButton("Modificar");
			btnModificar.setBounds(320, 62, 106, 40);
			contentPanel.add(btnModificar);
		}
		{
			btnBorrar = new JButton("Borrar");
			btnBorrar.setBounds(320, 113, 106, 40);
			contentPanel.add(btnBorrar);
		}
		{
			btnConsultar = new JButton("Consultar");
			btnConsultar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnConsultar.setBounds(320, 163, 106, 40);
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
	}

}
