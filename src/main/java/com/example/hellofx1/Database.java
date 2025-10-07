package com.example.hellofx1;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connectDb(){
            try {
                String url = "jdbc:mysql://localhost:3306/weddingvenue";
                String user = "root";
                String password = "";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, password);
                return con;

            } catch (SQLException | ClassNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Error connecting to the database. Please make sure MySQL server is running.");
                alert.showAndWait();
                throw new RuntimeException(e);
            }


    }
}
