package vista;

import controlador.ControladorEmpleado;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Empleado;

public class FormRegistrarEmpleado extends JFrame {

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtCargo;
    private JTextField txtSalario;

    private JButton btnGuardar;
    private JButton btnCancelar;

    private ControladorEmpleado controlador;
    private Empleado empleadoActual;

    // Constructor para modo Registro
    public FormRegistrarEmpleado() {
        this.empleadoActual = null;
        inicializarVentana("Registrar Empleado");
    }

    // Constructor para modo Modificación
    public FormRegistrarEmpleado(Empleado empleado) {
        this.empleadoActual = empleado;
        inicializarVentana("Modificar Empleado");
        cargarDatosEmpleado();
    }

    private void inicializarVentana(String titulo) {
        controlador = new ControladorEmpleado();

        setTitle(titulo);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 5 filas, 2 columnas
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtCargo = new JTextField();
        txtSalario = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panel.add(new JLabel(" Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel(" Correo:"));
        panel.add(txtCorreo);

        panel.add(new JLabel(" Cargo:"));
        panel.add(txtCargo);
        
        panel.add(new JLabel(" Salario:"));
        panel.add(txtSalario);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(e -> {
            guardarEmpleado();
        });

        btnCancelar.addActionListener(e -> {
            dispose();
        });
    }

    private void cargarDatosEmpleado() {
        if (empleadoActual != null) {
            txtNombre.setText(empleadoActual.getNombre());
            txtCorreo.setText(empleadoActual.getCorreo());
            txtCargo.setText(empleadoActual.getCargo());
            txtSalario.setText(String.valueOf(empleadoActual.getSalario()));
        }
    }

    private void guardarEmpleado() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String cargo = txtCargo.getText().trim();
        String salarioStr = txtSalario.getText().trim();

        // Validaciones de campos vacíos
        if (nombre.isEmpty() || correo.isEmpty() || cargo.isEmpty() || salarioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        double salario = 0;
        try {
            salario = Double.parseDouble(salarioStr);
            if(salario < 0) {
                JOptionPane.showMessageDialog(this, "El salario no puede ser negativo.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido para el salario (use punto para decimales).");
            return;
        }

        if (empleadoActual == null) {
            // Modo Registro
            Empleado nuevoEmpleado = new Empleado(0, nombre, correo, cargo, salario);
            boolean exito = controlador.registrar(nuevoEmpleado);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Empleado registrado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al registrar el empleado.");
            }
        } else {
            // Modo Modificación
            empleadoActual.setNombre(nombre);
            empleadoActual.setCorreo(correo);
            empleadoActual.setCargo(cargo);
            empleadoActual.setSalario(salario);

            boolean exito = controlador.modificar(empleadoActual);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Empleado actualizado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al actualizar el empleado.");
            }
        }
    }
}