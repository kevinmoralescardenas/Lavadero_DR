package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FormPrincipal extends JFrame implements ActionListener {

    private JMenuBar barra;

    private JMenu menuServicios;
    private JMenu menuUsuarios;
    private JMenu menuEmpleados;
    private JMenu menuClientes;
    private JMenu menuDeudas;
    private JMenu menuSalir;

    private JMenuItem itemListarServicios;
    private JMenuItem itemRegistrarServicio;
    
    private JMenuItem itemListarUsuarios;
    private JMenuItem itemRegistrarUsuario;
    
    private JMenuItem itemListarEmpleados;
    private JMenuItem itemRegistrarEmpleado;
    
    private JMenuItem itemListarClientes;
    private JMenuItem itemRegistrarCliente;
    
    private JMenuItem itemListarDeudas;
    private JMenuItem itemRegistrarDeuda;
    
    private JMenuItem itemSalir;

    public FormPrincipal() {
        setTitle("Sistema Lavadero DR");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crearMenu();
    }

    private void crearMenu() {

        barra = new JMenuBar();

        menuServicios = new JMenu("Servicios");
        menuUsuarios = new JMenu("Usuarios");
        menuEmpleados = new JMenu("Empleados");
        menuClientes = new JMenu("Clientes");
        menuDeudas = new JMenu("Deudas");
        menuSalir = new JMenu("Salir");

        itemListarServicios = new JMenuItem("Listar Servicios");
        itemRegistrarServicio = new JMenuItem("Registrar Servicio");
        
        itemListarUsuarios = new JMenuItem("Listar Usuarios");
        itemRegistrarUsuario = new JMenuItem("Registrar Usuario");
        
        itemListarEmpleados = new JMenuItem("Listar Empleados");
        itemRegistrarEmpleado = new JMenuItem("Registrar Empleado");
        
        itemListarClientes = new JMenuItem("Listar Clientes");
        itemRegistrarCliente = new JMenuItem("Registrar Cliente");

        itemListarDeudas = new JMenuItem("Listar Deudas");
        itemRegistrarDeuda = new JMenuItem("Registrar Deuda");
        
        itemSalir = new JMenuItem("Salir del Sistema");

        itemListarServicios.addActionListener(this);
        itemRegistrarServicio.addActionListener(this);
        
        itemListarUsuarios.addActionListener(this);
        itemRegistrarUsuario.addActionListener(this);
        
        itemListarEmpleados.addActionListener(this);
        itemRegistrarEmpleado.addActionListener(this);
        
        itemListarClientes.addActionListener(this);
        itemRegistrarCliente.addActionListener(this);

        itemListarDeudas.addActionListener(this);
        itemRegistrarDeuda.addActionListener(this);
        
        itemSalir.addActionListener(this);

        menuServicios.add(itemListarServicios);
        menuServicios.add(itemRegistrarServicio);
        
        menuUsuarios.add(itemListarUsuarios);
        menuUsuarios.add(itemRegistrarUsuario);
        
        menuEmpleados.add(itemListarEmpleados);
        menuEmpleados.add(itemRegistrarEmpleado);
        
        menuClientes.add(itemListarClientes);
        menuClientes.add(itemRegistrarCliente);

        menuDeudas.add(itemListarDeudas);
        menuDeudas.add(itemRegistrarDeuda);

        menuSalir.add(itemSalir);

        barra.add(menuServicios);
        barra.add(menuUsuarios);
        barra.add(menuEmpleados);
        barra.add(menuClientes);
        barra.add(menuDeudas);
        barra.add(menuSalir);

        setJMenuBar(barra);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == itemListarServicios) {
            new FormListarServicios().setVisible(true);
        }
        
        if (e.getSource() == itemRegistrarServicio) {
            new FormRegistrarServicio().setVisible(true);
        }
        
        if (e.getSource() == itemListarUsuarios) {
            new FormListarUsuarios().setVisible(true);
        }
        
        if (e.getSource() == itemRegistrarUsuario) {
            new FormRegistrarUsuario().setVisible(true);
        }
        
        if (e.getSource() == itemListarEmpleados) {
            new FormListarEmpleados().setVisible(true);
        }
        
        if (e.getSource() == itemRegistrarEmpleado) {
            new FormRegistrarEmpleado().setVisible(true);
        }
        
        if (e.getSource() == itemListarClientes) {
            new FormListarClientes().setVisible(true);
        }
        
        if (e.getSource() == itemRegistrarCliente) {
            new FormRegistrarCliente().setVisible(true);
        }

        if (e.getSource() == itemListarDeudas) {
            FormListarDeudas formulario = new FormListarDeudas();
            formulario.setVisible(true);
        }

        if (e.getSource() == itemRegistrarDeuda) {
            FormRegistrarDeuda formulario = new FormRegistrarDeuda();
            formulario.setVisible(true);
        }

        if (e.getSource() == itemSalir) {
            System.exit(0);
        }
    }
}
