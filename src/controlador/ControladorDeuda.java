// ControladorDeuda.java
package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import modelo.Cliente;
import modelo.Deuda;
import modelo.Servicio;

public class ControladorDeuda {

    public void registrarDeuda(Deuda deuda) {

        String sql = "INSERT INTO deuda "
                + "(id_cliente, id_servicio, monto, "
                + "descripcion, fecha_registro, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1,
                    deuda.getCliente().getId());

            if (deuda.getServicio() != null) {
                pstmt.setInt(
                        2,
                        deuda.getServicio().getCodigo()
                );
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }

            pstmt.setDouble(3, deuda.getMonto());
            pstmt.setString(4,
                    deuda.getDescripcion());
            pstmt.setString(5,
                    deuda.getFechaRegistro());
            pstmt.setString(6,
                    deuda.getEstado());

            pstmt.executeUpdate();

            ResultSet rs =
                    pstmt.getGeneratedKeys();

            if (rs.next()) {
                deuda.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }
    }

    public ArrayList<Deuda> obtenerDeudas() {

        ArrayList<Deuda> lista =
                new ArrayList<Deuda>();

        String sql =
                "SELECT d.id_deuda, d.monto, "
                + "d.descripcion, d.fecha_registro, "
                + "d.estado, "
                + "c.id_cliente, c.nombre, "
                + "c.correo, c.telefono, "
                + "s.codigo, s.nombre AS nombre_servicio, "
                + "s.precio, s.stock "
                + "FROM deuda d "
                + "INNER JOIN cliente c "
                + "ON d.id_cliente = c.id_cliente "
                + "LEFT JOIN servicio s "
                + "ON d.id_servicio = s.codigo "
                + "ORDER BY d.id_deuda";

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

                Servicio servicio = null;

                if (rs.getObject("codigo")
                        != null) {

                    servicio =
                            new Servicio(
                                    rs.getInt("codigo"),
                                    rs.getString(
                                            "nombre_servicio"),
                                    rs.getDouble("precio"),
                                    rs.getInt("stock")
                            );
                }

                Deuda deuda =
                        new Deuda(
                                rs.getInt("id_deuda"),
                                cliente,
                                servicio,
                                rs.getDouble("monto"),
                                rs.getString(
                                        "descripcion"),
                                rs.getString(
                                        "fecha_registro"),
                                rs.getString("estado")
                        );

                lista.add(deuda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return lista;
    }

    public boolean pagarDeuda(int idDeuda) {

        String sql = "UPDATE deuda "
                + "SET estado = 'pagado' "
                + "WHERE id_deuda = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDeuda);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }

    public boolean eliminarDeuda(int idDeuda) {

        String sql =
                "DELETE FROM deuda "
                + "WHERE id_deuda = ?";

        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, idDeuda);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }
}
