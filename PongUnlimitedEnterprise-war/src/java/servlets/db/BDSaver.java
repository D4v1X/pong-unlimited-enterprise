/**
 *
 */
package servlets.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import servlets.SceneSaver;

/**
 * @author David Santiago Barrera
 * @data 14/10/2012
 *
 */
public class BDSaver implements SceneSaver {

    // Database
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DB_URL = "jdbc:mysql://localhost/pelotas";
    // Database credentials
    private String USER = "root";
    private String PASS = "";

    @Override
    public Object load(String name) {

        Connection connection;
        Statement statement;
        ResultSet resultset;
        String sql;
        ObjectInputStream objectIn = null;
        Object obj = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            statement = connection.createStatement();

            sql = "Select * from usuarios where usuario = \"" + name + "\"";
            resultset = statement.executeQuery(sql);

            // Extract data from result set

            while (resultset.next()) {
                byte[] buf = resultset.getBytes("objeto");
                if (buf != null) {
                    objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                }
                obj = objectIn.readObject();
                System.out.println(obj);
            }

            // Clean-up environment
            resultset.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error Exception");
            System.out.println(e.getMessage());
        }
        return obj;
    }

    @Override
    public Boolean write(String name, Object obj) {

        Connection connection;
        PreparedStatement ps;
        String sql;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            System.out.println(obj);
            sql = "UPDATE usuarios set objeto = ? WHERE usuario = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, obj);
            ps.setString(2, name);
            if (0 == ps.executeUpdate()) {
                sql = "INSERT into usuarios (usuario, objeto) VALUES (?,?)";
                ps = connection.prepareStatement(sql);
                ps.setString(1, name);
                ps.setObject(2, obj);
                ps.executeUpdate();
            }
            System.out.println(obj);
            // Clean-up environment
            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error SQLException");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado");
            System.out.println(e.getMessage());
        }

        return true;
    }
}
