package vista;

import controlador.ControladorUsuario;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import modelo.Usuario;

public class FormRegistrarUsuario extends JFrame {

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    private JButton btnGuardar;
    private JButton btnCancelar;

    private ControladorUsuario controlador;
    private Usuario usuarioActual; // Usado para saber si es edición o registro

    // Constructor para modo Registro
    public FormRegistrarUsuario() {
        this.usuarioActual = null;
        inicializarVentana("Registrar Usuario");
    }

    // Constructor para modo Modificación
    public FormRegistrarUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        inicializarVentana("Modificar Usuario");
        cargarDatosUsuario();
    }

    private void inicializarVentana(String titulo) {
        controlador = new ControladorUsuario();

        setTitle(titulo);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panel.add(new JLabel(" Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel(" Correo:"));
        panel.add(txtCorreo);

        panel.add(new JLabel(" Usuario:"));
        panel.add(txtUsuario);

        panel.add(new JLabel(" Contraseña:"));
        panel.add(txtPassword);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(e -> {
            guardarUsuario();
        });

        btnCancelar.addActionListener(e -> {
            dispose();
        });
    }

    private void cargarDatosUsuario() {
        if (usuarioActual != null) {
            txtNombre.setText(usuarioActual.getNombre());
            txtCorreo.setText(usuarioActual.getCorreo());
            txtUsuario.setText(usuarioActual.getUsuario());
            txtPassword.setText(usuarioActual.getPassword());
        }
    }

    private void guardarUsuario() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String usr = txtUsuario.getText().trim();
        String pwd = new String(txtPassword.getPassword()).trim();

        // Validaciones requeridas
        if (nombre.isEmpty() || correo.isEmpty() || usr.isEmpty() || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (pwd.length() < 4) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.");
            return;
        }

        if (usuarioActual == null) {
            // Lógica de Registro
            Usuario nuevoUsuario = new Usuario(0, nombre, correo, usr, pwd);
            boolean exito = controlador.registrar(nuevoUsuario);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al registrar el usuario.");
            }
        } else {
            // Lógica de Modificación
            usuarioActual.setNombre(nombre);
            usuarioActual.setCorreo(correo);
            usuarioActual.setUsuario(usr);
            usuarioActual.setPassword(pwd);

            boolean exito = controlador.modificar(usuarioActual);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al actualizar el usuario.");
            }
        }
    }
}
