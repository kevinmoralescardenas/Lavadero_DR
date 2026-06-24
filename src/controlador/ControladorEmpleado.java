// ControladorEmpleado.java
package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Empleado;
import modelo.Servicio;

public class ControladorEmpleado {

    public boolean registrar(Empleado empleado) {
        String sql = "INSERT INTO empleado "
                + "(nombre, correo, cargo, salario) "
                + "VALUES (?, ?, ?, ?)";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getCorreo());
            pstmt.setString(3, empleado.getCargo());
            pstmt.setDouble(4, empleado.getSalario());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public ArrayList<Empleado> obtenerTodos() {
        ArrayList<Empleado> lista =
                new ArrayList<Empleado>();

        String sql = "SELECT * FROM empleado "
                + "ORDER BY id_empleado";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Empleado empleado =
                        new Empleado(
                                rs.getInt("id_empleado"),
                                rs.getString("nombre"),
                                rs.getString("correo"),
                                rs.getString("cargo"),
                                rs.getDouble("salario")
                        );

                lista.add(empleado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return lista;
    }

    public Empleado buscarPorId(int idEmpleado) {
        Empleado empleado = null;

        String sql = "SELECT * FROM empleado "
                + "WHERE id_empleado = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEmpleado);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empleado = new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("cargo"),
                        rs.getDouble("salario")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return empleado;
    }

    public boolean modificar(Empleado empleado) {
        String sql = "UPDATE empleado "
                + "SET nombre = ?, "
                + "correo = ?, "
                + "cargo = ?, "
                + "salario = ? "
                + "WHERE id_empleado = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getCorreo());
            pstmt.setString(3, empleado.getCargo());
            pstmt.setDouble(4, empleado.getSalario());
            pstmt.setInt(5, empleado.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public boolean eliminar(int idEmpleado) {
        String sql =
                "DELETE FROM empleado WHERE id_empleado = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEmpleado);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public boolean asignarServicio(
            int idEmpleado,
            int codigoServicio) {

        String sql = "INSERT INTO empleado_servicio "
                + "(id_empleado, codigo_servicio) "
                + "VALUES (?, ?)";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEmpleado);
            pstmt.setInt(2, codigoServicio);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public ArrayList<Servicio> obtenerServiciosDeEmpleado(
            int idEmpleado) {

        ArrayList<Servicio> lista =
                new ArrayList<Servicio>();

        String sql = "SELECT s.codigo, s.nombre, "
                + "s.precio, s.stock "
                + "FROM servicio s "
                + "INNER JOIN empleado_servicio es "
                + "ON s.codigo = es.codigo_servicio "
                + "WHERE es.id_empleado = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEmpleado);

            ResultSet rs = pstmt.executeQuery();

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
}
