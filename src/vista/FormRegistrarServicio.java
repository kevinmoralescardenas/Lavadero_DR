package vista;

import controlador.ControladorServicio;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Servicio;

public class FormRegistrarServicio extends JFrame {

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtStock;

    private JButton btnGuardar;
    private JButton btnCancelar;

    private ControladorServicio controlador;
    private Servicio servicioActual;

    // Constructor para modo Registro
    public FormRegistrarServicio() {
        this.servicioActual = null;
        inicializarVentana("Registrar Servicio");
    }

    // Constructor para modo Modificación
    public FormRegistrarServicio(Servicio servicio) {
        this.servicioActual = servicio;
        inicializarVentana("Modificar Servicio");
        cargarDatosServicio();
    }

    private void inicializarVentana(String titulo) {
        controlador = new ControladorServicio();

        setTitle(titulo);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 4 filas, 2 columnas
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panel.add(new JLabel(" Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel(" Precio:"));
        panel.add(txtPrecio);

        panel.add(new JLabel(" Stock:"));
        panel.add(txtStock);

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(e -> {
            guardarServicio();
        });

        btnCancelar.addActionListener(e -> {
            dispose();
        });
    }

    private void cargarDatosServicio() {
        if (servicioActual != null) {
            txtNombre.setText(servicioActual.getNombre());
            txtPrecio.setText(String.valueOf(servicioActual.getPrecio()));
            txtStock.setText(String.valueOf(servicioActual.getStock()));
        }
    }

    private void guardarServicio() {
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr = txtStock.getText().trim();

        // Validaciones de campos vacíos
        if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        double precio = 0;
        int stock = 0;

        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor que 0.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido para el precio (use punto para decimales).");
            return;
        }

        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock debe ser mayor o igual a 0.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número entero válido para el stock.");
            return;
        }

        if (servicioActual == null) {
            // Modo Registro
            Servicio nuevoServicio = new Servicio(0, nombre, precio, stock);
            // Nota: En ControladorServicio, el método se llama registrarServicio
            controlador.registrarServicio(nuevoServicio);
            
            JOptionPane.showMessageDialog(this, "Servicio registrado exitosamente.");
            dispose();
        } else {
            // Modo Modificación
            servicioActual.setNombre(nombre);
            servicioActual.setPrecio(precio);
            servicioActual.setStock(stock);

            boolean exito = controlador.modificar(servicioActual);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Servicio actualizado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al actualizar el servicio.");
            }
        }
    }
}