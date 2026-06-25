package interfaz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import control.Controladora;
import logica.Categoria;
import logica.Item;
import logica.Persona;
import logica.Tipo;

public class VentanaReportes extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextArea areaReporte;

    private void mostrarReportePersonas() {
        Controladora control = Controladora.getInstance();
        StringBuilder sb = new StringBuilder();
        for (Persona p : control.getPersonas()) {
            try {
                sb.append(control.generarReportePersona(p.getTelefono()));
                sb.append("\n");
            } catch (Exception e) {
                sb.append("Error: ").append(e.getMessage()).append("\n");
            }
        }
        areaReporte.setText(sb.toString());
    }

    private void mostrarReporteItems() {
        Controladora control = Controladora.getInstance();
        StringBuilder sb = new StringBuilder();
        for (Item i : control.getItems()) {
            try {
                sb.append(control.generarReporteItem(i.getCodigo()));
                sb.append("\n");
            } catch (Exception e) {
                sb.append("Error: ").append(e.getMessage()).append("\n");
            }
        }
        areaReporte.setText(sb.toString());
    }

    private void mostrarReporteCategorias() {
        Controladora control = Controladora.getInstance();
        StringBuilder sb = new StringBuilder();
        for (Categoria c : control.getCategorias()) {
            try {
                sb.append(control.generarReporteCategoria(c.getNombre()));
                sb.append("\n");
            } catch (Exception e) {
                sb.append("Error: ").append(e.getMessage()).append("\n");
            }
        }
        areaReporte.setText(sb.toString());
    }

    private void mostrarReporteTipos() {
        Controladora control = Controladora.getInstance();
        StringBuilder sb = new StringBuilder();
        for (Tipo t : control.getTipos()) {
            try {
                sb.append(control.generarReporteTipo(t.getNombre()));
                sb.append("\n");
            } catch (Exception e) {
                sb.append("Error: ").append(e.getMessage()).append("\n");
            }
        }
        areaReporte.setText(sb.toString());
    }

    public VentanaReportes() {
        setTitle("Reportes");
        setModal(true);
        setBounds(100, 100, 500, 450);
        getContentPane().setLayout(null);

        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(0, 0, 484, 410);
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel);

        JButton btnPersonas = new JButton("Personas");
        btnPersonas.setBounds(10, 5, 105, 25);
        btnPersonas.addActionListener(e -> mostrarReportePersonas());
        contentPanel.add(btnPersonas);

        JButton btnItems = new JButton("Ítems");
        btnItems.setBounds(120, 5, 105, 25);
        btnItems.addActionListener(e -> mostrarReporteItems());
        contentPanel.add(btnItems);

        JButton btnCategorias = new JButton("Categorías");
        btnCategorias.setBounds(230, 5, 105, 25);
        btnCategorias.addActionListener(e -> mostrarReporteCategorias());
        contentPanel.add(btnCategorias);

        JButton btnTipos = new JButton("Tipos");
        btnTipos.setBounds(340, 5, 105, 25);
        btnTipos.addActionListener(e -> mostrarReporteTipos());
        contentPanel.add(btnTipos);

        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaReporte);
        scroll.setBounds(10, 40, 460, 330);
        contentPanel.add(scroll);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(380, 378, 90, 23);
        btnCerrar.addActionListener(e -> dispose());
        contentPanel.add(btnCerrar);
    }
}