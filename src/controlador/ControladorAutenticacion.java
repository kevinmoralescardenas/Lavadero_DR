// ControladorAutenticacion.java
package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

public class ControladorAutenticacion {

    private Usuario usuarioActual;

    public Usuario autenticar(String user, String pass) {
        Usuario usuario = null;

        String sql = "SELECT id_usuario, nombre, correo, "
                + "usuario, password "
                + "FROM usuario "
                + "WHERE usuario = ? AND password = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, user);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("usuario"),
                        rs.getString("password")
                );

                usuarioActual = usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return usuario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}
