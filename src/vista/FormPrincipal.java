package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FormPrincipal extends JFrame
        implements ActionListener {

    private JMenuBar barra;

    private JMenu menuServicios;
    private JMenu menuUsuarios;
    private JMenu menuEmpleados;
    private JMenu menuClientes;
    private JMenu menuDeudas;
    private JMenu menuSalir;

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

        itemListarDeudas =
                new JMenuItem("Listar Deudas");
        itemRegistrarDeuda =
                new JMenuItem("Registrar Deuda");
        itemSalir =
                new JMenuItem("Salir del Sistema");

        itemListarDeudas.addActionListener(this);
        itemRegistrarDeuda.addActionListener(this);
        itemSalir.addActionListener(this);

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

        if (e.getSource() == itemListarDeudas) {
            FormListarDeudas formulario =
                    new FormListarDeudas();

            formulario.setVisible(true);
        }

        if (e.getSource() == itemRegistrarDeuda) {
            FormRegistrarDeuda formulario =
                    new FormRegistrarDeuda();

            formulario.setVisible(true);
        }

        if (e.getSource() == itemSalir) {
            System.exit(0);
        }
    }
}
