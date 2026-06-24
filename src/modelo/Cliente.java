// Cliente.java
package modelo;

public class Cliente extends Persona {

    private String telefono;

    public Cliente(int id, String nombre,
                   String correo, String telefono) {
        super(id, nombre, correo);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Teléfono: " + telefono);
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}