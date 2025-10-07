module com.example.hellofx1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;
    requires java.sql;
    requires mysql.connector;
    requires java.mail;


    opens com.example.hellofx1 to javafx.fxml;
    exports com.example.hellofx1;
}