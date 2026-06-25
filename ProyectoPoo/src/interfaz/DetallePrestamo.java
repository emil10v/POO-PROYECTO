package interfaz;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import control.Controladora;
import logica.Item;
import logica.Prestamo;
public class DetallePrestamo extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPanel;
    private JPanel panelItems;
    private JPanel panelItemsDisponibles;
    private Integer numPrestamo;

    private void cargarItems() {
        try {
            Controladora control = Controladora.getInstance();
            Prestamo p = control.getPrestamo(numPrestamo);
            panelItems.removeAll();
            for (Item i : p.getItems()) {
                JCheckBox check = new JCheckBox(i.getNombre());
                check.setName(String.valueOf(i.getCodigo()));
                panelItems.add(check);
            }
            panelItems.revalidate();
            panelItems.repaint();

            panelItemsDisponibles.removeAll();
            for (Item i : control.getItemsDisponibles()) {
                JCheckBox check = new JCheckBox(i.getNombre());
                check.setName(String.valueOf(i.getCodigo()));
                panelItemsDisponibles.add(check);
            }
            panelItemsDisponibles.revalidate();
            panelItemsDisponibles.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void retornarItemsSeleccionados() {
        try {
            Controladora control = Controladora.getInstance();
            List<Integer> codigos = new ArrayList<>();
            for (Component comp : panelItems.getComponents()) {
                JCheckBox check = (JCheckBox) comp;
                if (check.isSelected()) {
                    codigos.add(Integer.parseInt(check.getName()));
                }
            }
            if (codigos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione al menos un ítem para retornar.");
                return;
            }
            for (Integer codigo : codigos) {
                control.retornarItemPrestamo(codigo, numPrestamo);
            }
            JOptionPane.showMessageDialog(this, "Ítem(s) retornado(s) correctamente.");
            cargarItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void agregarItemsSeleccionados() {
        try {
            Controladora control = Controladora.getInstance();
            List<Integer> codigos = new ArrayList<>();
            for (Component comp : panelItemsDisponibles.getComponents()) {
                JCheckBox check = (JCheckBox) comp;
                if (check.isSelected()) {
                    codigos.add(Integer.parseInt(check.getName()));
                }
            }
            if (codigos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione al menos un ítem para agregar.");
                return;
            }
            control.agregarItemsPrestamo(codigos, numPrestamo);
            JOptionPane.showMessageDialog(this, "Ítem(s) agregado(s) correctamente.");
            cargarItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            DetallePrestamo dialog = new DetallePrestamo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DetallePrestamo() {
        setTitle("Detalle de Préstamo");
        setModal(true);
        setBounds(100, 100, 460, 420);
        getContentPane().setLayout(null);

        contentPanel = new JPanel();
        contentPanel.setBounds(0, 0, 446, 380);
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel);

        JLabel lblItems = new JLabel("Ítems del préstamo:");
        lblItems.setBounds(10, 11, 126, 14);
        contentPanel.add(lblItems);

        JButton btnRetornar = new JButton("Retornar seleccionados");
        btnRetornar.setBounds(140, 7, 190, 23);
        btnRetornar.addActionListener(e -> retornarItemsSeleccionados());
        contentPanel.add(btnRetornar);

        panelItems = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollItems = new JScrollPane(panelItems);
        scrollItems.setBounds(10, 30, 418, 120);
        contentPanel.add(scrollItems);

        JLabel lblItemsDisponibles = new JLabel("Ítems disponibles:");
        lblItemsDisponibles.setBounds(10, 162, 126, 14);
        contentPanel.add(lblItemsDisponibles);

        JButton btnAgregar = new JButton("Agregar seleccionados");
        btnAgregar.setBounds(140, 158, 190, 23);
        btnAgregar.addActionListener(e -> agregarItemsSeleccionados());
        contentPanel.add(btnAgregar);

        panelItemsDisponibles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollDisponibles = new JScrollPane(panelItemsDisponibles);
        scrollDisponibles.setBounds(10, 185, 418, 120);
        contentPanel.add(scrollDisponibles);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(330, 315, 100, 25);
        btnCerrar.addActionListener(e -> dispose());
        contentPanel.add(btnCerrar);
    }

    public DetallePrestamo(Integer numPrestamo) {
        this();
        this.numPrestamo = numPrestamo;
        cargarItems();
    }

}