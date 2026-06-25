package vista;

import controlador.ControladorEmpleado;
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
import modelo.Empleado;

public class FormListarEmpleados extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnRefrescar;

    private ControladorEmpleado controlador;

    public FormListarEmpleados() {
        controlador = new ControladorEmpleado();

        setTitle("Listado de Empleados");
        setSize(850, 400);
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
        modelo.addColumn("Cargo");
        modelo.addColumn("Salario");

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
            agregarEmpleado();
        });

        btnModificar.addActionListener(e -> {
            modificarEmpleado();
        });

        btnEliminar.addActionListener(e -> {
            eliminarEmpleado();
        });
    }

    private void cargarDatos() {
        try {
            modelo.setRowCount(0);

            ArrayList<Empleado> lista = controlador.obtenerTodos();

            for (Empleado emp : lista) {
                // Formateamos el salario a 2 decimales
                String salarioFormateado = String.format(java.util.Locale.US, "%.2f", emp.getSalario());
                
                Object[] fila = {
                    emp.getId(),
                    emp.getNombre(),
                    emp.getCorreo(),
                    emp.getCargo(),
                    salarioFormateado
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }
    }

    private void agregarEmpleado() {
        new FormRegistrarEmpleado().setVisible(true);
    }

    private void modificarEmpleado() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la lista.");
            return;
        }

        int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
        Empleado empleado = controlador.buscarPorId(id);

        if (empleado != null) {
            new FormRegistrarEmpleado(empleado).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el empleado en la base de datos.");
        }
    }

    private void eliminarEmpleado() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la lista.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar este empleado?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

            boolean resultado = controlador.eliminar(id);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar el empleado.");
            }
        }
    }
}