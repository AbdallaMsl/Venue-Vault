package com.example.hellofx1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HelloController implements Initializable {
    @FXML
    private Button signUp_btn;

    @FXML
    private Button signUp_close;

    @FXML
    private TextField signUp_email;

    @FXML
    private Button signUp_minimize;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private TextField signUp_username;

    @FXML
    private Button signin_close;

    @FXML
    private Hyperlink signin_createAccount;

    @FXML
    private AnchorPane signin_form;

    @FXML
    private Button signin_loginbtn;

    @FXML
    private Button signin_minimize;

    @FXML
    private PasswordField signin_password;

    @FXML
    private TextField signin_username;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private Hyperlink signup_loginaccount;

    @FXML
    private ImageView signup_image;

    @FXML
    private ImageView signin_image;

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean validEmail() {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        Matcher match = pattern.matcher(signUp_email.getText());
        Alert alert;
        return match.find() && match.group().equals(signUp_email.getText());

    }

    public void signup(){
        String sql = "INSERT INTO admin (email, username, password) VALUES (?, ? , ?)";

        String sql1 = "SELECT username FROM admin WHERE username = ?";
        String sql2 = "SELECT email FROM admin WHERE email = ?";

        con = Database.connectDb();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, signUp_email.getText());
            ps.setString(2, signUp_username.getText());
            // Hashing the password
            String hashedPassword = PasswordUtil.hashPassword(signUp_password.getText());
            ps.setString(3, hashedPassword);

            // check username
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setString(1, signUp_username.getText());
            ResultSet rs1 = ps1.executeQuery();

            // check email
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, signUp_email.getText());
            ResultSet rs2 = ps2.executeQuery();


            Alert alert;

            if(signUp_email.getText().isEmpty() || signUp_username.getText().isEmpty() || signUp_password.getText().isEmpty() ){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            } else if (!validEmail() || rs2.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or the email address is taken");
                alert.showAndWait();
                
            } else if (rs1.next()) {
                // Username already exists
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(signUp_username.getText() + " already exists.");
                alert.showAndWait();

            } else if (signUp_password.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password must be at least 8 characters long.");
                    alert.showAndWait();
                } else {

                ps.execute();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Registration successful!.");
                alert.showAndWait();

                signUp_username.clear();
                signUp_password.clear();
                signUp_email.clear();

                signin_form.setVisible(true);
                signup_form.setVisible(false);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    private double x = 0;
    private double y = 0;
    public void signin(){
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        con  = Database.connectDb();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, signin_username.getText());

            String hashedPassword = PasswordUtil.hashPassword(signin_password.getText());
            ps.setString(2, hashedPassword);
            rs = ps.executeQuery();

            Alert alert;

            if(signin_username.getText().isEmpty() || signin_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            }else{

                if(rs.next()){

                    getData.username = signin_username.getText();

                    signin_loginbtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("newDashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);


                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    } );

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password.");
                    alert.showAndWait();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void switchForm(ActionEvent event){
        if(event.getSource() == signin_createAccount){
            signin_form.setVisible(false);
            signup_form.setVisible(true);
        }else if(event.getSource() == signup_loginaccount){
            signup_form.setVisible(false);
            signin_form.setVisible(true);
        }
    }



    public void close(){
        System.exit(0);
    }
    public void signIn_minimize(){
        Stage stage = (Stage) signin_form.getScene().getWindow();
        stage.setIconified(true);
    }
    public void signUp_minimize(){
        Stage stage = (Stage) signin_form.getScene().getWindow();
        stage.setIconified(true);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // sign up image
        String uri = "file:src/main/resources/images/edde_sands_hotel_wellness_resort_1.jpg";
        Image image = new Image(uri, 200, 266, false, true);
        signup_image.setImage(image);

        //sign in image
        String uri2 = "file:src/main/resources/images/phoenicia_hotel.jpg";
        Image image2 = new Image(uri2, 200, 266, false, true);
        signin_image.setImage(image2);
    }
}