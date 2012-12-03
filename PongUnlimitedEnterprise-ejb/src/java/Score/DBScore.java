/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Score;

import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import nodrawable.Ranking;
import nodrawable.Score;

/**
 *
 * @author davidsantiagobarrera
 */
public class DBScore implements ScoreManager {

    // Database
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DB_URL = "jdbc:mysql://localhost/PongUnlimited";
    // Database credentials
    private String USER = "root";
    private String PASS = "";

    @Override
    public Ranking loadRanking() {
        Connection connection;
        Statement statement;
        ResultSet resultset;
        String sql;
        ObjectInputStream objectIn = null;

        Ranking ranking = new Ranking();
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            statement = connection.createStatement();

            sql = "SELECT * FROM ranking ORDER BY time DESC";
            resultset = statement.executeQuery(sql);

            // Extract data from result set
            while (resultset.next()) {
                Score obj = new Score();
                obj.setId(resultset.getString("name"));
                obj.setTime(resultset.getDouble("time"));
                obj.setModo(resultset.getString("modo"));
                ranking.setScores(obj);
                System.out.println(obj);
            }

            // Clean-up environment
            resultset.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.getSQLState();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado");
            System.out.println(e.getMessage());
        }
        return ranking;
    }

    @Override
    public void saveScore(Score scoresave) {
        Connection connection;
        PreparedStatement ps;
        String sql;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            System.out.println(scoresave);
            //------
            sql = "UPDATE ranking set time = ? WHERE name = ? AND time < ? AND modo = ?";
            ps = connection.prepareStatement(sql);
            ps.setDouble(1, scoresave.getTime());
            ps.setString(2, scoresave.getId());
            ps.setDouble(3, scoresave.getTime());
            ps.setString(4, scoresave.getModo());
            if (0 == ps.executeUpdate()) {
                sql = "UPDATE ranking set name = ? WHERE name = ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, scoresave.getId());
                ps.setString(2, scoresave.getId());
                if (0 == ps.executeUpdate()) {
                    sql = "INSERT into ranking (name,time,modo) VALUES (?,?,?)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, scoresave.getId());
                    ps.setDouble(2, scoresave.getTime());
                    ps.setString(3, scoresave.getModo());
                    ps.executeUpdate();
                }
            }
            //------
//            sql = "INSERT into ranking (name,time,modo) VALUES (?,?,?)";
//            ps = connection.prepareStatement(sql);
//            ps.setString(1, scoresave.getId());
//            ps.setDouble(2, scoresave.getTime());
//            ps.setString(3, scoresave.getModo());
//            ps.executeUpdate();

            System.out.println(scoresave);
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
    }
}
