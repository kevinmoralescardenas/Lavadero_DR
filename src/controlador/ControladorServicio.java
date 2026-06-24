// ControladorServicio.java
package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Servicio;

public class ControladorServicio {

    public void registrarServicio(Servicio servicio) {
        String sql = "INSERT INTO servicio "
                + "(nombre, precio, stock) "
                + "VALUES (?, ?, ?)";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, servicio.getNombre());
            pstmt.setDouble(2, servicio.getPrecio());
            pstmt.setInt(3, servicio.getStock());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }
    }

    public ArrayList<Servicio> obtenerTodos() {
        ArrayList<Servicio> lista =
                new ArrayList<Servicio>();

        String sql = "SELECT codigo, nombre, "
                + "precio, stock "
                + "FROM servicio "
                + "ORDER BY codigo";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Servicio servicio =
                        new Servicio(
                                rs.getInt("codigo"),
                                rs.getString("nombre"),
                                rs.getDouble("precio"),
                                rs.getInt("stock")
                        );

                lista.add(servicio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return lista;
    }

    public Servicio buscarPorId(int codigo) {
        Servicio servicio = null;

        String sql = "SELECT codigo, nombre, "
                + "precio, stock "
                + "FROM servicio "
                + "WHERE codigo = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, codigo);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                servicio = new Servicio(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return servicio;
    }

    public boolean modificar(Servicio servicio) {
        String sql = "UPDATE servicio "
                + "SET nombre = ?, "
                + "precio = ?, "
                + "stock = ? "
                + "WHERE codigo = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, servicio.getNombre());
            pstmt.setDouble(2, servicio.getPrecio());
            pstmt.setInt(3, servicio.getStock());
            pstmt.setInt(4, servicio.getCodigo());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public boolean eliminar(int codigo) {
        String sql =
                "DELETE FROM servicio WHERE codigo = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, codigo);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }
}