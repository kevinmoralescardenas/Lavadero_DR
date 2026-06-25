package vista;

import controlador.ControladorUsuario;
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
import modelo.Usuario;

public class FormListarUsuarios extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnRefrescar;

    private ControladorUsuario controlador;

    public FormListarUsuarios() {
        controlador = new ControladorUsuario();

        setTitle("Listado de Usuarios");
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
        modelo.addColumn("Usuario");

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
            agregarUsuario();
        });

        btnModificar.addActionListener(e -> {
            modificarUsuario();
        });

        btnEliminar.addActionListener(e -> {
            eliminarUsuario();
        });
    }

    private void cargarDatos() {
        try {
            modelo.setRowCount(0);

            ArrayList<Usuario> lista = controlador.obtenerTodos();

            for (Usuario u : lista) {
                Object[] fila = {
                    u.getId(),
                    u.getNombre(),
                    u.getCorreo(),
                    u.getUsuario()
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }

    private void agregarUsuario() {
        new FormRegistrarUsuario().setVisible(true);
    }

    private void modificarUsuario() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la lista.");
            return;
        }

        int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
        Usuario usuario = controlador.buscarPorId(id);

        if (usuario != null) {
            new FormRegistrarUsuario(usuario).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario en la base de datos.");
        }
    }

    private void eliminarUsuario() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la lista.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar este usuario?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

            boolean resultado = controlador.eliminar(id);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar el usuario.");
            }
        }
    }
}