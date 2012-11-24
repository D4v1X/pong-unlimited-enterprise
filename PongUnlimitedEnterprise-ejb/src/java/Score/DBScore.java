/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Score;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
        Score obj = null;
        Ranking ranking = new Ranking();
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            statement = connection.createStatement();

            sql = "Select * from ranking";
            resultset = statement.executeQuery(sql);

            // Extract data from result set
            while (resultset.next()) {
                byte[] buf = resultset.getBytes("objeto");
                if (buf != null) {
                    objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                }
                obj = (Score) objectIn.readObject();
                ranking.setScores(obj);
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
            sql = "INSERT into ranking (objeto) VALUES (?)";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, scoresave);
            ps.executeUpdate();

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
