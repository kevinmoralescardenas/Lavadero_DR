// Conexion.java
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/lavadero_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin123";

    private Connection conexion;

    public Connection conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            System.out.println(
                    "Conexión exitosa a PostgreSQL.");
        } catch (ClassNotFoundException e) {
            System.err.println(
                    "Driver de PostgreSQL no encontrado: "
                    + e.getMessage());
        } catch (SQLException e) {
            System.err.println(
                    "Error de conexión: "
                    + e.getMessage());
            e.printStackTrace();
        }

        return conexion;
    }

    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println(
                        "Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println(
                        "Error al cerrar conexión: "
                        + e.getMessage());
            }
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}