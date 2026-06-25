//registrar deuda
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.Cliente;
import modelo.Deuda;
import modelo.Servicio;

public class FormRegistrarDeuda extends JFrame {

    private JComboBox<Object> cbCliente;
    private JComboBox<Object> cbServicio;

    // NUEVOS Y MODIFICADOS CAMPOS DE TEXTO
    private JTextField txtPrecioServicio; // Muestra el precio base del servicio (Solo lectura)
    private JTextField txtMontoAdicional; // Campo editable para costos extras (Antes txtMonto)
    private JTextField txtMontoTotal;     // Muestra el cálculo final automático (Solo lectura)
    private JTextArea txtDescripcion;

    private JButton btnRegistrar;
    private JButton btnCancelar;

    private ControladorCliente controladorCliente;
    private ControladorServicio controladorServicio;
    private ControladorDeuda controladorDeuda;

    public FormRegistrarDeuda() {

        controladorCliente = new ControladorCliente();
        controladorServicio = new ControladorServicio();
        controladorDeuda = new ControladorDeuda();

        setTitle("Registrar Deuda");
        // Aumentamos el alto de la ventana a 450 para que el layout de 7 filas no se vea apretado
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarClientes();
        cargarServicios();
        
        // Ejecutamos el cálculo inicial por defecto al abrir el formulario
        calcularTotal();

        setVisible(true);
    }

    private void inicializarComponentes() {
        // Modificado a 7 filas para dar espacio a los nuevos campos de visualización de precios
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        cbCliente = new JComboBox<Object>();
        cbServicio = new JComboBox<Object>();

        // Inicialización y configuración de los campos de montos
        txtPrecioServicio = new JTextField("0.00");
        txtPrecioServicio.setEditable(false); // Solo lectura

        txtMontoAdicional = new JTextField("0.00"); // Inicializado en 0.00

        txtMontoTotal = new JTextField("0.00");
        txtMontoTotal.setEditable(false); // Solo lectura

        txtDescripcion = new JTextArea(4, 20);

        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");

        // Fila 1
        panel.add(new JLabel("Cliente:"));
        panel.add(cbCliente);

        // Fila 2
        panel.add(new JLabel("Servicio:"));
        panel.add(cbServicio);

        // Fila 3: Componente Nuevo
        panel.add(new JLabel("Precio Servicio Base:"));
        panel.add(txtPrecioServicio);

        // Fila 4: Campo Modificado
        panel.add(new JLabel("Monto Adicional / Extra:"));
        panel.add(txtMontoAdicional);

        // Fila 5: Componente Nuevo
        panel.add(new JLabel("Monto Total de Deuda:"));
        panel.add(txtMontoTotal);

        // Fila 6
        panel.add(new JLabel("Descripción:"));
        panel.add(new JScrollPane(txtDescripcion));

        // Fila 7
        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);

        // --- SISTEMA DE CÁLCULO ASÍNCRONO Y EN TIEMPO REAL ---

        // Listener 1: Detecta cambios en la selección del JComboBox de servicios
        cbServicio.addActionListener(e -> {
            calcularTotal();
        });

        // Listener 2: Detecta de forma reactiva cada pulsación o borrado en el campo "Monto Adicional"
        txtMontoAdicional.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { calcularTotal(); }
            @Override
            public void removeUpdate(DocumentEvent e) { calcularTotal(); }
            @Override
            public void changedUpdate(DocumentEvent e) { calcularTotal(); }
        });

        btnRegistrar.addActionListener(e -> {
            registrarDeuda();
        });

        btnCancelar.addActionListener(e -> {
            dispose();
        });
    }

    private void cargarClientes() {
        cbCliente.removeAllItems();
        ArrayList<Cliente> lista = controladorCliente.obtenerTodos();
        for (Cliente cliente : lista) {
            cbCliente.addItem(cliente);
        }
    }

    private void cargarServicios() {
        cbServicio.removeAllItems();
        cbServicio.addItem("Ninguno"); // Opción base por si no requiere asociar servicio
        ArrayList<Servicio> lista = controladorServicio.obtenerTodos();
        for (Servicio servicio : lista) {
            cbServicio.addItem(servicio);
        }
    }

    /**
     * Procesa la información del modelo seleccionado y del texto ingresado 
     * para refrescar dinámicamente las cajas de texto de solo lectura.
     */
    private void calcularTotal() {
        double precioServicio = 0.0;
        Object itemServicio = cbServicio.getSelectedItem();

        // Validamos si el objeto seleccionado corresponde efectivamente a la entidad Servicio
        if (itemServicio instanceof Servicio) {
            precioServicio = ((Servicio) itemServicio).getPrecio();
        }

        // Fijamos el precio base usando la región US para evitar incompatibilidad de comas/puntos decimales
        txtPrecioServicio.setText(String.format(java.util.Locale.US, "%.2f", precioServicio));

        double montoAdicional = 0.0;
        String adicionalStr = txtMontoAdicional.getText().trim();

        if (!adicionalStr.isEmpty()) {
            try {
                montoAdicional = Double.parseDouble(adicionalStr);
                // Si colocan momentáneamente un valor negativo, el preview no lo suma como resta
                if (montoAdicional < 0) {
                    montoAdicional = 0.0;
                }
            } catch (NumberFormatException e) {
                // Si el usuario deja letras o borra todo el campo, el valor base de cálculo temporal es 0
                montoAdicional = 0.0;
            }
        }

        double totalCalculado = precioServicio + montoAdicional;
        txtMontoTotal.setText(String.format(java.util.Locale.US, "%.2f", totalCalculado));
    }

    private void registrarDeuda() {
        try {
            if (cbCliente.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
                return;
            }

            Cliente cliente = (Cliente) cbCliente.getSelectedItem();
            Servicio servicio = null;
            Object itemServicio = cbServicio.getSelectedItem();
            double precioServicio = 0.0;

            if (itemServicio instanceof Servicio) {
                servicio = (Servicio) itemServicio;
                precioServicio = servicio.getPrecio();
            }

            String adicionalStr = txtMontoAdicional.getText().trim();
            
            // Si el operario deja el campo en blanco, lo tratamos automáticamente como un 0 adicional
            if (adicionalStr.isEmpty()) {
                adicionalStr = "0";
            }

            double montoAdicional = Double.parseDouble(adicionalStr);

            // Validación de Regla de Negocio: No se permiten deudas con valores extras negativos
            if (montoAdicional < 0) {
                JOptionPane.showMessageDialog(this, "El monto adicional debe ser mayor o igual a 0.");
                return;
            }

            // El costo real de la deuda unificada
            double montoFinal = precioServicio + montoAdicional;

            String descripcion = txtDescripcion.getText().trim();
            String fecha = java.time.LocalDateTime.now().toString();

            // Construcción del objeto modelo Deuda con el monto total calculado
            Deuda deuda = new Deuda(0, cliente, servicio, montoFinal, descripcion, fecha, "pendiente");

            controladorDeuda.registrarDeuda(deuda);

            JOptionPane.showMessageDialog(this, "Deuda registrada correctamente.");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto adicional válido. Use números enteros o decimales con punto (.)");
        }
    }
}