package vista;

import controlador.ControladorCliente;
import controlador.ControladorDeuda;
import controlador.ControladorServicio;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.Cliente;
import modelo.Deuda;
import modelo.Servicio;





public class FormRegistrarDeuda extends JFrame {

    private JComboBox<Object> cbCliente;
    private JComboBox<Object> cbServicio;

    private JTextField txtMonto;
    private JTextArea txtDescripcion;

    private JButton btnRegistrar;
    private JButton btnCancelar;

    private ControladorCliente controladorCliente;
    private ControladorServicio controladorServicio;
    private ControladorDeuda controladorDeuda;

    public FormRegistrarDeuda() {

        controladorCliente =
                new ControladorCliente();

        controladorServicio =
                new ControladorServicio();

        controladorDeuda =
                new ControladorDeuda();

        setTitle("Registrar Deuda");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarClientes();
        cargarServicios();

        setVisible(true);
    }

    private void inicializarComponentes() {

        JPanel panel =
                new JPanel(new GridLayout(5, 2, 10, 10));

        cbCliente = new JComboBox<Object>();
        cbServicio = new JComboBox<Object>();

        txtMonto = new JTextField();
        txtDescripcion = new JTextArea(4, 20);

        btnRegistrar =
                new JButton("Registrar");

        btnCancelar =
                new JButton("Cancelar");

        panel.add(new JLabel("Cliente:"));
        panel.add(cbCliente);

        panel.add(new JLabel("Servicio:"));
        panel.add(cbServicio);

        panel.add(new JLabel("Monto:"));
        panel.add(txtMonto);

        panel.add(new JLabel("Descripción:"));
        panel.add(new JScrollPane(txtDescripcion));

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);

        btnRegistrar.addActionListener(e -> {
            registrarDeuda();
        });

        btnCancelar.addActionListener(e -> {
            dispose();
        });
    }

    private void cargarClientes() {

        cbCliente.removeAllItems();

        ArrayList<Cliente> lista =
                controladorCliente.obtenerTodos();

        for (Cliente cliente : lista) {
            cbCliente.addItem(cliente);
        }
    }

    private void cargarServicios() {

        cbServicio.removeAllItems();

        cbServicio.addItem("Ninguno");

        ArrayList<Servicio> lista =
                controladorServicio.obtenerTodos();

        for (Servicio servicio : lista) {
            cbServicio.addItem(servicio);
        }
    }

    private void registrarDeuda() {

        try {

            if (cbCliente.getSelectedItem()
                    == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un cliente."
                );

                return;
            }

            Cliente cliente =
                    (Cliente)
                            cbCliente.getSelectedItem();

            Servicio servicio = null;

            Object itemServicio =
                    cbServicio.getSelectedItem();

            if (itemServicio instanceof Servicio) {
                servicio =
                        (Servicio) itemServicio;
            }

            double monto =
                    Double.parseDouble(
                            txtMonto.getText().trim());

            String descripcion =
                    txtDescripcion.getText().trim();
            
//posible error lo comente
            //String fecha = LocalDate.now().toString();
// solucion

            String fecha = java.time.LocalDateTime.now().toString();

            Deuda deuda =
                    new Deuda(
                            0,
                            cliente,
                            servicio,
                            monto,
                            descripcion,
                            fecha,
                            "pendiente"
                    );

            controladorDeuda
                    .registrarDeuda(deuda);

            JOptionPane.showMessageDialog(
                    this,
                    "Deuda registrada correctamente."
            );

            dispose();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un monto válido."
            );
        }
    }
}
