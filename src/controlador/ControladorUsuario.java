// ControladorUsuario.java
package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Usuario;

public class ControladorUsuario {

    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuario "
                + "(nombre, correo, usuario, password) "
                + "VALUES (?, ?, ?, ?)";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getUsuario());
            pstmt.setString(4, usuario.getPassword());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public ArrayList<Usuario> obtenerTodos() {
        ArrayList<Usuario> lista =
                new ArrayList<Usuario>();

        String sql = "SELECT * FROM usuario "
                + "ORDER BY id_usuario";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("usuario"),
                        rs.getString("password")
                );

                lista.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return lista;
    }

    public Usuario buscarPorId(int idUsuario) {
        Usuario usuario = null;

        String sql = "SELECT * FROM usuario "
                + "WHERE id_usuario = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("usuario"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return usuario;
    }

    public boolean modificar(Usuario usuario) {
        String sql = "UPDATE usuario "
                + "SET nombre = ?, "
                + "correo = ?, "
                + "usuario = ?, "
                + "password = ? "
                + "WHERE id_usuario = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getUsuario());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setInt(5, usuario.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public boolean eliminar(int idUsuario) {
        String sql =
                "DELETE FROM usuario WHERE id_usuario = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }
}