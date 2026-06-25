package vista;

import controlador.ControladorCliente;
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
import modelo.Cliente;

public class FormListarClientes extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnRefrescar;

    private ControladorCliente controlador;

    public FormListarClientes() {
        controlador = new ControladorCliente();

        setTitle("Listado de Clientes");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo");
        modelo.addColumn("Teléfono");

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
            agregarCliente();
        });

        btnModificar.addActionListener(e -> {
            modificarCliente();
        });

        btnEliminar.addActionListener(e -> {
            eliminarCliente();
        });
    }

    private void cargarDatos() {
        try {
            modelo.setRowCount(0);

            ArrayList<Cliente> lista = controlador.obtenerTodos();

            for (Cliente c : lista) {
                Object[] fila = {
                    c.getId(),
                    c.getNombre(),
                    c.getCorreo(),
                    c.getTelefono()
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }

    private void agregarCliente() {
        new FormRegistrarCliente().setVisible(true);
    }

    private void modificarCliente() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista.");
            return;
        }

        int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
        Cliente cliente = controlador.buscarPorId(id);

        if (cliente != null) {
            new FormRegistrarCliente(cliente).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el cliente en la base de datos.");
        }
    }

    private void eliminarCliente() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar este cliente?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

            boolean resultado = controlador.eliminar(id);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar el cliente.");
            }
        }
    }
}