// Usuario.java
package modelo;

public class Usuario extends Persona {

    private String usuario;
    private String password;

    public Usuario(int id, String nombre, String correo,
                   String usuario, String password) {
        super(id, nombre, correo);
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean solicitar(String usuarioIngresado,
                             String passwordIngresado) {
        return this.usuario.equals(usuarioIngresado)
                && this.password.equals(passwordIngresado);
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Usuario: " + usuario);
    }
}
