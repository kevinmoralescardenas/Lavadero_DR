// Deuda.java
package modelo;

public class Deuda {

    private int id;
    private Cliente cliente;
    private Servicio servicio;
    private double monto;
    private String descripcion;
    private String fechaRegistro;
    private String estado;

    public Deuda(int id, Cliente cliente, Servicio servicio,
                 double monto, String descripcion,
                 String fechaRegistro, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.servicio = servicio;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void mostrarInformacion() {
        System.out.println("ID Deuda: " + id);
        System.out.println("Cliente: " + cliente.getNombre());

        if (servicio != null) {
            System.out.println("Servicio: " + servicio.getNombre());
        }

        System.out.println("Monto: " + monto);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Fecha: " + fechaRegistro);
        System.out.println("Estado: " + estado);
    }
}