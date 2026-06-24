package vista;

import controlador.ControladorAutenticacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import modelo.Usuario;

public class FormLogin extends JFrame
        implements ActionListener {

    private JLabel lblUsuario;
    private JLabel lblPassword;

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    private JButton btnIngresar;
    private JButton btnSalir;

    public FormLogin() {
        setTitle("Sistema Lavadero DR - Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        iniciarComponentes();

        setVisible(true);
    }

    private void iniciarComponentes() {

        lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(50, 50, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(150, 50, 180, 25);
        add(txtUsuario);

        lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(50, 90, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 90, 180, 25);
        add(txtPassword);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(70, 150, 110, 30);
        btnIngresar.addActionListener(this);
        add(btnIngresar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(210, 150, 110, 30);
        btnSalir.addActionListener(this);
        add(btnSalir);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnIngresar) {

            String usuario =
                    txtUsuario.getText().trim();

            String password =
                    String.valueOf(
                            txtPassword.getPassword());

            ControladorAutenticacion controlador =
                    new ControladorAutenticacion();

            Usuario u =
                    controlador.autenticar(
                            usuario,
                            password);

            if (u != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Bienvenido "
                        + u.getNombre());

                dispose();

                FormPrincipal principal =
                        new FormPrincipal();

                principal.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuario o contraseña incorrectos.");
            }
        }

        if (e.getSource() == btnSalir) {
            System.exit(0);
        }
    }
}
