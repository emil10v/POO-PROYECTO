package interfaz;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Controladora;
import logica.Item;
import logica.Persona;
import logica.Prestamo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerModel;

public class VentanaCrearPrestamo extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel panelItems;
    private JTable tablaPersonas;
    private JCheckBox checkCrearAlerta;
    private JPanel contentPanel;
    private JPanel panelAlerta;
    private DefaultTableModel modeloTabla;
    private JCheckBox checkRecurrente;
    private JSpinner spinnerMinutos;

    private void cargarItems() {
        Controladora control = Controladora.getInstance();
        panelItems.removeAll();
        for (Item i : control.getItemsDisponibles()) {
            JCheckBox check = new JCheckBox(i.getNombre());
            check.setName(String.valueOf(i.getCodigo()));
            panelItems.add(check);
        }
        panelItems.revalidate();
        panelItems.repaint();
    }

    private void cargarPersonas() {
        Controladora control = Controladora.getInstance();
        modeloTabla.setRowCount(0);
        for (Persona p : control.getPersonas()) {
            modeloTabla.addRow(new Object[]{p.getNombre(), p.getTelefono()});
        }
    }

    private void guardarPrestamo() {
        try {
            Controladora control = Controladora.getInstance();
            int filaSeleccionada = tablaPersonas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una persona.");
                return;
            }
            String telefono = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            control.crearPrestamo(telefono);
            List<Prestamo> prestamos = control.getPrestamos();
            int numPrestamo = (prestamos.size());
            List<Integer> codigosSeleccionados = new ArrayList<>();
            for (Component comp : panelItems.getComponents()) {
                JCheckBox check = (JCheckBox) comp;
                if (check.isSelected()) {
                    codigosSeleccionados.add(Integer.parseInt(check.getName()));
                }
            }
            if (!codigosSeleccionados.isEmpty()) {
                control.agregarItemsPrestamo(codigosSeleccionados, numPrestamo);
            }
            if (checkCrearAlerta.isSelected()) {
                boolean recurrente = checkRecurrente.isSelected();
                int minutos = (int) spinnerMinutos.getValue();
                control.crearAlertaPrestamo(numPrestamo, recurrente, minutos);
            }
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            VentanaCrearPrestamo dialog = new VentanaCrearPrestamo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VentanaCrearPrestamo() {
        setBounds(100, 100, 460, 340);
        getContentPane().setLayout(null);

        contentPanel = new JPanel();
        contentPanel.setBounds(0, 0, 446, 300);
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel);

        modeloTabla = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Nombre", "Teléfono"}
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPersonas = new JTable(modeloTabla);
        tablaPersonas.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablaPersonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaPersonas);
        scrollPane.setBounds(10, 11, 420, 62);
        contentPanel.add(scrollPane);

        JLabel labelItems = new JLabel("Items disponibles:");
        labelItems.setBounds(10, 82, 140, 14);
        contentPanel.add(labelItems);

        panelItems = new JPanel();
        panelItems.setBounds(10, 95, 420, 83);
        contentPanel.add(panelItems);

        checkCrearAlerta = new JCheckBox("Crear Alerta");
        checkCrearAlerta.setBounds(6, 185, 110, 22);
        contentPanel.add(checkCrearAlerta);

        panelAlerta = new JPanel();
        panelAlerta.setLayout(null);
        panelAlerta.setBounds(10, 210, 420, 50);
        panelAlerta.setVisible(false);
        contentPanel.add(panelAlerta);

        checkRecurrente = new JCheckBox("Recurrente");
        checkRecurrente.setBounds(10, 7, 100, 22);
        panelAlerta.add(checkRecurrente);

        JLabel lblMinutos = new JLabel("Minutos:");
        lblMinutos.setBounds(120, 10, 60, 14);
        panelAlerta.add(lblMinutos);

        spinnerMinutos = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        spinnerMinutos.setBounds(185, 7, 70, 22);
        panelAlerta.add(spinnerMinutos);

        checkCrearAlerta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelAlerta.setVisible(checkCrearAlerta.isSelected());
            }
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.setBounds(0, 265, 446, 33);
        contentPanel.add(buttonPane);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarPrestamo();
            }
        });
        buttonPane.add(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);

        cargarItems();
        cargarPersonas();
    }
}