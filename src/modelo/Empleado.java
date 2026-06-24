// Empleado.java
package modelo;

import java.util.ArrayList;

public class Empleado extends Persona {

    private String cargo;
    private double salario;
    private ArrayList<Servicio> serviciosAsignados;

    public Empleado(int id, String nombre, String correo,
                    String cargo, double salario) {
        super(id, nombre, correo);
        this.cargo = cargo;
        this.salario = salario;
        this.serviciosAsignados = new ArrayList<Servicio>();
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void agregarServicio(Servicio servicio) {
        serviciosAsignados.add(servicio);
    }

    public boolean eliminarServicio(Servicio servicio) {
        return serviciosAsignados.remove(servicio);
    }

    public ArrayList<Servicio> obtenerServicios() {
        return serviciosAsignados;
    }

    public void evaluar() {
        System.out.println("Evaluación de empleado: "
                + nombre + " (" + cargo + ")");

        if (serviciosAsignados.isEmpty()) {
            System.out.println("No tiene servicios asignados.");
        } else {
            System.out.println("Total de servicios: "
                    + serviciosAsignados.size());

            for (Servicio s : serviciosAsignados) {
                System.out.println("- "
                        + s.getCodigo() + " "
                        + s.getNombre());
            }
        }
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Cargo: " + cargo);
        System.out.println("Salario: " + salario);
        System.out.println("Servicios realizados: "
                + serviciosAsignados.size());
    }
}
