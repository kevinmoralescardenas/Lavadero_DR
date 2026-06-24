// ControladorCliente.java
package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Cliente;

public class ControladorCliente {

    public boolean registrar(Cliente cliente) {
        String sql = "INSERT INTO cliente "
                + "(nombre, correo, telefono) "
                + "VALUES (?, ?, ?)";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getCorreo());
            pstmt.setString(3, cliente.getTelefono());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public ArrayList<Cliente> obtenerTodos() {
        ArrayList<Cliente> lista =
                new ArrayList<Cliente>();

        String sql = "SELECT * FROM cliente "
                + "ORDER BY id_cliente";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente =
                        new Cliente(
                                rs.getInt("id_cliente"),
                                rs.getString("nombre"),
                                rs.getString("correo"),
                                rs.getString("telefono")
                        );

                lista.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return lista;
    }

    public Cliente buscarPorId(int idCliente) {
        Cliente cliente = null;

        String sql = "SELECT * FROM cliente "
                + "WHERE id_cliente = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return cliente;
    }

    public boolean modificar(Cliente cliente) {
        String sql = "UPDATE cliente "
                + "SET nombre = ?, "
                + "correo = ?, "
                + "telefono = ? "
                + "WHERE id_cliente = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getCorreo());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setInt(4, cliente.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public boolean eliminar(int idCliente) {
        String sql =
                "DELETE FROM cliente WHERE id_cliente = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }
}
