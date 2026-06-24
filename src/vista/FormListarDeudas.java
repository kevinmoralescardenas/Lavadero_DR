package vista;

import controlador.ControladorDeuda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Deuda;

public class FormListarDeudas extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnPagar;
    private JButton btnEliminar;
    private JButton btnRefrescar;

    private ControladorDeuda controlador;

    public FormListarDeudas() {

        controlador = new ControladorDeuda();

        setTitle("Listado de Deudas");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("Servicio");
        modelo.addColumn("Monto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Fecha");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla),
                BorderLayout.CENTER);

        JPanel panelBotones =
                new JPanel(new FlowLayout());

        btnPagar = new JButton("Pagar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");

        panelBotones.add(btnPagar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        add(panelBotones,
                BorderLayout.SOUTH);

        cargarDatos();

        btnRefrescar.addActionListener(e -> {
            cargarDatos();
        });

        btnPagar.addActionListener(e -> {
            pagarDeuda();
        });

        btnEliminar.addActionListener(e -> {
            eliminarDeuda();
        });
    }

    private void cargarDatos() {

        modelo.setRowCount(0);

        ArrayList<Deuda> lista =
                controlador.obtenerDeudas();

        for (Deuda deuda : lista) {

            String servicio = "N/A";

            if (deuda.getServicio() != null) {
                servicio =
                        deuda.getServicio().getNombre();
            }

            Object[] fila = {
                deuda.getId(),
                deuda.getCliente().getNombre(),
                servicio,
                deuda.getMonto(),
                deuda.getDescripcion(),
                deuda.getFechaRegistro(),
                deuda.getEstado()
            };

            modelo.addRow(fila);
        }
    }

    private void pagarDeuda() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una deuda."
            );
            return;
        }

        int id =
                Integer.parseInt(
                        tabla.getValueAt(fila, 0)
                                .toString());

        boolean resultado =
                controlador.pagarDeuda(id);

        if (resultado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Deuda pagada correctamente."
            );
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible pagar la deuda."
            );
        }
    }

    private void eliminarDeuda() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una deuda."
            );
            return;
        }

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea eliminar la deuda?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        int id =
                Integer.parseInt(
                        tabla.getValueAt(fila, 0)
                                .toString());

        boolean resultado =
                controlador.eliminarDeuda(id);

        if (resultado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Deuda eliminada correctamente."
            );
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible eliminar la deuda."
            );
        }
    }
}
