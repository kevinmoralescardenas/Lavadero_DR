package vista;

import controlador.ControladorServicio;
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
import modelo.Servicio;

public class FormListarServicios extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnRefrescar;

    private ControladorServicio controlador;

    public FormListarServicios() {
        controlador = new ControladorServicio();

        setTitle("Listado de Servicios");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("productos usados");

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarDatos();

        btnRefrescar.addActionListener(e -> {
            cargarDatos();
        });

        btnAgregar.addActionListener(e -> {
            agregarServicio();
        });

        btnModificar.addActionListener(e -> {
            modificarServicio();
        });

        btnEliminar.addActionListener(e -> {
            eliminarServicio();
        });
    }

    private void cargarDatos() {
        try {
            modelo.setRowCount(0);

            ArrayList<Servicio> lista = controlador.obtenerTodos();

            for (Servicio s : lista) {
                // Formateamos el precio a 2 decimales
                String precioFormateado = String.format(java.util.Locale.US, "%.2f", s.getPrecio());
                
                Object[] fila = {
                    s.getCodigo(),
                    s.getNombre(),
                    precioFormateado,
                    s.getStock()
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }

    private void agregarServicio() {
        new FormRegistrarServicio().setVisible(true);
    }

    private void modificarServicio() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la lista.");
            return;
        }

        int codigo = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
        Servicio servicio = controlador.buscarPorId(codigo);

        if (servicio != null) {
            new FormRegistrarServicio(servicio).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el servicio en la base de datos.");
        }
    }

    private void eliminarServicio() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la lista.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar este servicio?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            int codigo = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

            boolean resultado = controlador.eliminar(codigo);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Servicio eliminado correctamente.");
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar el servicio.");
            }
        }
    }
}