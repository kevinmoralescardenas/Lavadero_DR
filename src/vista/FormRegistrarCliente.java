package vista;

import controlador.ControladorCliente;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Cliente;

public class FormRegistrarCliente extends JFrame {

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;

    private JButton btnGuardar;
    private JButton btnCancelar;

    private ControladorCliente controlador;
    private Cliente clienteActual; // Usado para determinar si es edición o registro

    // Constructor para modo Registro
    public FormRegistrarCliente() {
        this.clienteActual = null;
        inicializarVentana("Registrar Cliente");
    }

    // Constructor para modo Modificación
    public FormRegistrarCliente(Cliente cliente) {
        this.clienteActual = cliente;
        inicializarVentana("Modificar Cliente");
        cargarDatosCliente();
    }

    private void inicializarVentana(String titulo) {
        controlador = new ControladorCliente();

        setTitle(titulo);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 4 filas, 2 columnas, con 10px de espacio entre componentes
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtTelefono = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panel.add(new JLabel(" Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel(" Correo:"));
        panel.add(txtCorreo);

        panel.add(new JLabel(" Teléfono:"));
        panel.add(txtTelefono);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(e -> {
            guardarCliente();
        });

        btnCancelar.addActionListener(e -> {
            dispose();
        });
    }

    private void cargarDatosCliente() {
        if (clienteActual != null) {
            txtNombre.setText(clienteActual.getNombre());
            txtCorreo.setText(clienteActual.getCorreo());
            txtTelefono.setText(clienteActual.getTelefono());
        }
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();

        // Validaciones requeridas
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (telefono.length() < 7) {
            JOptionPane.showMessageDialog(this, "El teléfono debe tener al menos 7 caracteres.");
            return;
        }

        if (clienteActual == null) {
            // Lógica de Registro
            Cliente nuevoCliente = new Cliente(0, nombre, correo, telefono);
            boolean exito = controlador.registrar(nuevoCliente);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al registrar el cliente.");
            }
        } else {
            // Lógica de Modificación
            clienteActual.setNombre(nombre);
            clienteActual.setCorreo(correo);
            clienteActual.setTelefono(telefono);

            boolean exito = controlador.modificar(clienteActual);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al actualizar el cliente.");
            }
        }
    }
}