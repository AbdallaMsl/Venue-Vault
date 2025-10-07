package com.example.hellofx1;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class DashboardController implements Initializable {
@FXML
private Button report_btn;
@FXML
private Label pending;
    @FXML
    private Button addVenues_btn;

    @FXML
    private TextField addVenues_capacity;

    @FXML
    private TableColumn<venuesData, String> addVenues_col_capacity;

    @FXML
    private TableColumn<venuesData, String> addVenues_col_date;

    @FXML
    private TableColumn<venuesData, String> addVenues_col_location;

    @FXML
    private TableColumn<venuesData, String> addVenues_col_venueName;

    @FXML
    private DatePicker addVenues_date;

    @FXML
    private TextArea addVenues_description;

    @FXML
    private AnchorPane addVenues_form;

    @FXML
    private ImageView addVenues_imageView;

    @FXML
    private TextField addVenues_location;

    @FXML
    private TextField addVenues_search;

    @FXML
    private TableView<venuesData> addVenues_tableView;

    @FXML
    private TextField addVenues_venueName;

    @FXML
    private Button requestedVenues_bookBtn;

    @FXML
    private Button requestedVenues_btn;

    @FXML
    private TextField requestedVenues_capacity;

    @FXML
    private Spinner<Double> requestedVenues_cateringPrice;

    @FXML
    private Button requestedVenues_clearBtn;

    @FXML
    private Label requestedVenues_cname;

    @FXML
    private TableColumn<bookingData, String> requestedVenues_col_cname;

    @FXML
    private TableColumn<bookingData, String> requestedVenues_col_name;

    @FXML
    private TableColumn<bookingData, String> requestedVenues_col_phone;

    @FXML
    private TableColumn<bookingData, String> requestedVenues_col_reqDate;

    @FXML
    private DatePicker requestedVenues_eventDate;

    @FXML
    private AnchorPane requestedVenues_form;

    @FXML
    private Label requestedVenues_imageTitle;

    @FXML
    private ImageView requestedVenues_imageView;

    @FXML
    private Label requestedVenues_name;

    @FXML
    private Spinner<Double> requestedVenues_parkingPrice;

    @FXML
    private Label requestedVenues_reqDate;

    @FXML
    private TableView<bookingData> requestedVenues_tableView;

    @FXML
    private Label requestedVenues_total;

    @FXML
    private Label requestedVenues_avaiCapacity;

    @FXML
    private Button customers_btn;

    @FXML
    private TableColumn<customerData, String> customer_rowCounter;

    @FXML
    private TableColumn<customerData, String> customers_cname;

    @FXML
    private TableColumn<customerData, String> customers_eventDate;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private TableColumn<customerData, String> customers_payAmount;

    @FXML
    private TextField customers_payCash;

    @FXML
    private TableColumn<customerData, String> customers_payDate;

    @FXML
    private TextField customers_search;

    @FXML
    private TableView<customerData> customers_tableView;

    @FXML
    private TableColumn<customerData, String> customers_vname;

    @FXML
    private Label customers_id;

    @FXML
    private Label customers_name;

    @FXML
    private Button dashboard_btn;

    @FXML
    private LineChart<String, Double> dashboard_dailyRev;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_requestedVenues;

    @FXML
    private Label dashboard_totalBooking;

    @FXML
    private Label dashboard_totalEarnToday;

    @FXML
    private Button signout;

    @FXML
    private Label username;

    @FXML
    private AnchorPane topForm;

    private double x = 0;
    private double y = 0;

    // Database stuff
    private Connection connect;
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;

    @FXML Button maximize_btn;
    public void maximize(){
        restoredown.setVisible(true);
        maximize_btn.setVisible(false);
        Stage stage =(Stage)topForm.getScene().getWindow();
        stage.setFullScreen(true);
    }
    @FXML Button restoredown;
    public void restore_down(){
        restoredown.setVisible(false);
        maximize_btn.setVisible(true);
        Stage stage =(Stage)topForm.getScene().getWindow();
        stage.setFullScreen(false);
    }




//    Dashboard Main Page

    //bill

    public void Billprint() {

        // SQL queries for selecting and deleting customer records
        String selectall = "SELECT * FROM payment";
        // Establishing connection
        Connection connect = Database.connectDb();
        try {
            // Prepare and execute the SELECT query
            PreparedStatement prepare = connect.prepareStatement(selectall);
            ResultSet result = prepare.executeQuery();

            // Using try-with-resources to ensure BufferedWriter is closed properly
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\h\\Desktop\\Bills\\Bills.txt"))) {
                // Write the results from the query to the file
                String time = null;
                Date date = null;
                while (result.next()) {
                    String payment_id = result.getString("payment_id");
                    String booking_id = result.getString("booking_id");
                    Double amount = result.getDouble("amount");
                    String payment_date = result.getString("payment_date");
                    // Write the header only once, if it's the first record (optional)
                    if (result.isFirst()) {
                        // Header with increased width
                        writer.write("                                     Venue Booking Services");
                        writer.newLine();
                        writer.write("                                   Tel: 81 643 898 | 81 400 846");
                        writer.newLine();
                        writer.newLine();
                        writer.write("Payment Id                   | Booking Id             | Payment Date           | Amount       ");
                        writer.newLine();
                        writer.write("------------------------------------------------------------------------------------------------------------------------------------");
                        writer.newLine();
                    }

// Write the product details in a formatted manner with wider columns
                    writer.write(String.format(
                            "%-28s | %-22s | %-22s | %-30.2f ",  // Increased width for each column
                            payment_id,
                            booking_id,
                            payment_date,
                            amount
                    ));
                    writer.newLine();  // Add a newline for each product record
                }
                // Optionally,  footer with total
                writer.write("------------------------------------------------------------------------------------------------------------------------------------");
                writer.newLine();

                writer.write("Thank you for visiting  us!");
                writer.newLine();


            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                e.printStackTrace();

            } catch (SQLException e) {
                // Handle SQL errors (e.g., SELECT or DELETE failure)
                System.out.println("SQL Error: " + e.getMessage());
                e.printStackTrace();

            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }


    //    cards
    public void showCardsData(){
        String sql = "SELECT COUNT(*) AS totalBookings FROM payment";
        String sql2 = "SELECT SUM(amount) AS totalRevenue FROM payment";
        String sql3 = "SELECT COUNT(*) AS totalRequests FROM booking";
        connect = Database.connectDb();
        Alert alert;
        try{
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();

            ps2 = connect.prepareStatement(sql2);
            rs2 = ps2.executeQuery();

            ps3 = connect.prepareStatement(sql3);
            rs3 = ps3.executeQuery();

            String bookings = "";
            String revenues = "";
            String requests = "";
            while (rs.next()) {
                bookings  = rs.getString("totalBookings");
            }
            while (rs2.next()) {
                revenues  = rs2.getString("totalRevenue");
            }
            while (rs3.next()) {
                requests  = rs3.getString("totalRequests");
            }
            dashboard_totalBooking.setText(bookings);
            dashboard_totalEarnToday.setText(revenues);
            dashboard_requestedVenues.setText(requests);

        } catch (SQLException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load data :" + e.getMessage());
            alert.showAndWait();
        }




    }


// Create a new series
    XYChart.Series<String, Double> series = new XYChart.Series<>();
    public void showChartData() {

        String sql = "SELECT * FROM payment";
        connect = Database.connectDb();
        Alert alert;

        try{
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();

            // Clear the series before adding new data
            dashboard_dailyRev.getData().clear();
            series.setName("Daily Revenue");
            while(rs.next()){
                String date = rs.getString("payment_date");
                double amount = rs.getDouble("amount");
                series.getData().add(new XYChart.Data<>( date, amount));
            }
            // Add the series to the chart
            dashboard_dailyRev.getData().add(series);

        }catch(SQLException e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load data :" + e.getMessage());
            alert.showAndWait();
        }
    }


//    -------------------------------------End Dashboard Main---------------------------------------------------

    // Customers Page
    int selectedBookingId = 0;


    public void pay()  {

        String sql = "UPDATE payment SET payment_date = ?, amount = ? WHERE booking_id = ?";
        connect = Database.connectDb();
        Alert alert;

        if(selectedBookingId == 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a customer first");
            alert.showAndWait();
        }else{
            try {
                LocalDate date = LocalDate.now();
                ps = connect.prepareStatement(sql);
                ps.setDate(1, Date.valueOf(date));
                ps.setDouble(2, Double.parseDouble(customers_payCash.getText()));
                ps.setInt(3, selectedBookingId);
                ps.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Payment successful");
                alert.showAndWait();

                showCustomerList();
                clearCustomerList();

            }catch(SQLException e){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to connect to the database: " + e.getMessage());
                alert.showAndWait();
            }

        }
    }



    public ObservableList<customerData> customerList() {
        ObservableList<customerData> customerL = FXCollections.observableArrayList();

        String sql = "SELECT c.customer_id, b.booking_id, c.name cname, v.name vname, p.amount, p.payment_date, b.event_date, b.total_price FROM customer c " +
                "JOIN booking b ON c.customer_id = b.customer_Id" +
                " JOIN venue v ON b.venue_id = v.venue_id LEFT JOIN payment p ON b.booking_id = p.booking_id WHERE is_booked = ?";
        Alert alert;
        connect = Database.connectDb();

        try{
            customerData customerD;
            ps = connect.prepareStatement(sql);
            ps.setInt(1, 1);
            rs = ps.executeQuery();
            int counter = 0;
            while(rs.next()){
                counter++;
                double amount = rs.getDouble("amount");
                String pDate = rs.getString("payment_date");

//                LocalDate pDate = LocalDate.now(); // dummy value for now
                customerD = new customerData(
                        counter,
                        rs.getInt("customer_id"),
                        rs.getInt("booking_id"),
                        rs.getString("cname"),
                        rs.getString("vname"),
                        rs.getDate("event_date").toLocalDate(),
                        amount,
                        pDate,
                        rs.getDouble("total_price"));

                customerL.add(customerD);
            }


        }catch(SQLException e){
            System.out.println("Failed to connect to the database: " + e.getMessage());
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to connect to the database: " + e.getMessage());
            alert.showAndWait();
        }
        return customerL;
    }

    private ObservableList<customerData> custList;
    public void showCustomerList() {
        custList = customerList();

        customer_rowCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));
        customers_cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
        customers_vname.setCellValueFactory(new PropertyValueFactory<>("vname"));
        customers_eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        customers_payAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        customers_payDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        customers_tableView.setItems(custList);
    }

    public void selectCustomer(){
        customerData customerD = customers_tableView.getSelectionModel().getSelectedItem();
        int num = customers_tableView.getSelectionModel().getSelectedIndex();

        if(num < 0){
            return;
        }

        selectedBookingId = customerD.getBookingId();
        customers_id.setText(String.valueOf(customerD.getCounter()));
        customers_name.setText(customerD.getCname());
        pending.setText(String.valueOf(customerD.getTotal_price()));
    }



    public void clearCustomerList(){
        selectedBookingId = 0;
        customers_id.setText("");
        customers_name.setText("");
        customers_payCash.setText("");
        pending.setText("");
    }

    public void searchCustomerList(){
        FilteredList<customerData> filter = new FilteredList<>(custList, e -> true);

        // Listen to search field
        customers_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateCustomerData -> {
                // If search text is empty or null, show all records
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert search key to lowercase
                String searchKey = newValue.toLowerCase();
                String cName = String.valueOf(predicateCustomerData.getCname()).toLowerCase();
                String vName = String.valueOf(predicateCustomerData.getVname()).toLowerCase();
                String eventDate = String.valueOf(predicateCustomerData.getEventDate()).toLowerCase();
                String amount = String.valueOf(predicateCustomerData.getAmount()).toLowerCase();
                String paymentDate = String.valueOf(predicateCustomerData.getPaymentDate()).toLowerCase();

                return cName.contains(searchKey)
                        || vName.contains(searchKey)
                        || eventDate.contains(searchKey)
                        || amount.contains(searchKey)
                        || paymentDate.contains(searchKey);
            });
        });

        SortedList<customerData> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(customers_tableView.comparatorProperty());
        customers_tableView.setItems(sortedList);
    }

//    -------------------------------------End Customers---------------------------------------------------

// requested Venues Page
    private SpinnerValueFactory<Double> spinner2;
    private SpinnerValueFactory<Double> spinner3;

    private int capacity = 0;
    private double pPrice = 0;
    private double cPrice = 0;
    private double total = 0;

//



    public void book() {
        String sql = "UPDATE booking SET booking_date = ?, event_date = ?, parking_price = ?, catering_price = ?, total_price = ?, is_booked = ? " +
                "WHERE booking_id = ?";
        String sql2 = "INSERT INTO payment (booking_id) VALUES (?)";
        connect = Database.connectDb();
        Alert alert;

        int capacity = Integer.parseInt(requestedVenues_capacity.getText());
        String bookingDate = LocalDate.now().toString();
        String eventDate = String.valueOf(requestedVenues_eventDate.getValue());
        String parkingPrice = String.valueOf(requestedVenues_parkingPrice.getValue());
        String cateringPrice = String.valueOf(requestedVenues_cateringPrice.getValue());
        String totalPrice = String.valueOf(total);

        if (total == 0 || requestedVenues_eventDate.getValue() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please provide some details before booking");
            alert.showAndWait();
        } else if (capacity > getData.maxCapacity) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Sorry, you have exceeded the maximum capacity");
            alert.showAndWait();
        } else {
            try {
                ps = connect.prepareStatement(sql);
                ps.setString(1, bookingDate);
                ps.setString(2, eventDate);
                ps.setString(3, parkingPrice);
                ps.setString(4, cateringPrice);
                ps.setString(5, totalPrice);
                ps.setInt(6, 1);
                ps.setInt(7, getData.bookId);

                ps.execute();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booked Successfully");
                alert.setHeaderText(null);
                alert.setContentText(getData.vname + " booked on " + eventDate + " for $" + totalPrice);
                alert.showAndWait();

                ps = connect.prepareStatement(sql2);
                ps.setInt(1, getData.bookId);
                ps.execute();

                // إرسال البريد الإلكتروني
                sendEmail(getData.email, getData.vname, eventDate, totalPrice);

                clearRequestedVenues();
                showRequestedVenues();
            } catch (SQLException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void sendEmail(String recipientEmail, String venueName, String eventDate, String totalPrice) {
        String from = "abdallameslmani204@gmail.com";
        String password = "lapd yvok yvrg bjmb";
        String host = "smtp.gmail.com"; // Replace with your SMTP host

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Booking Confirmation");
            message.setText("Dear Customer,\n\n" +
                    "Your booking for " + venueName + " on " + eventDate + " has been successfully confirmed.\n" +
                    "Total Price: $" + totalPrice + "\n\n" +
                    "Thank you for choosing us!");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void deleteRequest(){
        String sql = "DELETE FROM booking WHERE booking_id = ?";
        connect = Database.connectDb();
        Alert alert;
        if(getData.bookId == 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Select booking request first");
            alert.showAndWait();
            return;
        }
        try {
            ps = connect.prepareStatement(sql);
            ps.setInt(1, getData.bookId);
            ps.executeUpdate();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deleted Successfully");
            alert.setHeaderText(null);
            alert.setContentText("Booking request of " + getData.cname + " deleted successfully");
            alert.showAndWait();
            clearRequestedVenues();
            showRequestedVenues();

        }catch (SQLException e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void clearRequestedVenues() {
            getData.bookId = 0;
            getData.venueId = 0;
            showSpinnerValue();
            requestedVenues_total.setText("$ 0.0");
            requestedVenues_imageTitle.setText("");
            requestedVenues_imageView.setImage(null);
    }

    public void showSpinnerValue(){

        int maxCapacity = 0;
        if(getData.bookId == 0){
            requestedVenues_bookBtn.setDisable(true);
            requestedVenues_clearBtn.setDisable(true);
            requestedVenues_eventDate.setDisable(true);
            requestedVenues_parkingPrice.setDisable(true);
            requestedVenues_cateringPrice.setDisable(true);
            requestedVenues_capacity.setDisable(true);
            requestedVenues_avaiCapacity.setText("Maximum Capacity: 0");
        }else {

            String sql = "SELECT capacity FROM venue WHERE venue_id = ?";
            connect = Database.connectDb();
            try {
                ps = connect.prepareStatement(sql);
                ps.setInt(1, getData.venueId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    maxCapacity = rs.getInt("capacity");
                }
                requestedVenues_bookBtn.setDisable(false);
                requestedVenues_clearBtn.setDisable(false);
                requestedVenues_eventDate.setDisable(false);
                requestedVenues_parkingPrice.setDisable(false);
                requestedVenues_cateringPrice.setDisable(false);
                requestedVenues_capacity.setDisable(false);


                requestedVenues_avaiCapacity.setText("Maximum Capacity: " + maxCapacity);
                getData.maxCapacity = maxCapacity;
                spinner2 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 0);
                spinner3 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000, 0);
                requestedVenues_capacity.setText("0");
                requestedVenues_parkingPrice.setValueFactory(spinner2);
                requestedVenues_cateringPrice.setValueFactory(spinner3);

            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }

    }

    public void getSpinnerValue() {

        capacity = parseInt(requestedVenues_capacity.getText());
        pPrice = requestedVenues_parkingPrice.getValue();
        cPrice = requestedVenues_cateringPrice.getValue();

        total = (capacity * pPrice) + (capacity * cPrice);

        requestedVenues_total.setText("$ " + total + "");
    }

    public ObservableList<bookingData> requestedVenuesList() {
        ObservableList<bookingData> listRequestBookings = FXCollections.observableArrayList();

        String sql = "SELECT b.booking_id, v.name vname, c.name cname, c.email, c.phone_number, b.request_date, v.image, v.venue_id, c.customer_id " +
                "FROM venue v JOIN booking b ON v.venue_id = b.venue_id " +
                "JOIN customer c ON b.customer_id = c.customer_id WHERE b.is_booked = 0";

        connect = Database.connectDb();
        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();

            bookingData bookD;

            while (rs.next()) {
                bookD = new bookingData(rs.getInt("booking_id"),
                        rs.getString("vname"),
                        rs.getString("cname"),
                        rs.getString("phone_number"),
                        rs.getDate("request_date").toLocalDate(),
                        rs.getString("image"),
                        rs.getInt("venue_id"),
                        rs.getInt("customer_id"),
                        rs.getString("email")
                        );
                listRequestBookings.add(bookD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRequestBookings;
    }

    private ObservableList<bookingData> requestedVRequestList;
    public void showRequestedVenues(){
        requestedVRequestList = requestedVenuesList();


        requestedVenues_col_name.setCellValueFactory(new PropertyValueFactory<>("vname"));
        requestedVenues_col_cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
        requestedVenues_col_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        requestedVenues_col_reqDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));

        requestedVenues_tableView.setItems(requestedVRequestList);
    }

    public void selectRequestedVenues(){

        bookingData bookD = requestedVenues_tableView.getSelectionModel().getSelectedItem();
        int num = requestedVenues_tableView.getSelectionModel().getSelectedIndex();

        if(num < 0){
            return;
        }


        requestedVenues_name.setText(bookD.getVname());
        requestedVenues_cname.setText(bookD.getCname());
        requestedVenues_reqDate.setText(bookD.getRequestDate().toString());

        getData.bookId = bookD.getBookingId();
        getData.cname = bookD.getCname();
        getData.venueId = bookD.getVenueId();
        getData.customerId = bookD.getCustomerId();
        getData.path = bookD.getImage();
        getData.vname = bookD.getVname();
        getData.email = bookD.getEmail();


        requestedVenues_bookBtn.setDisable(true);
        requestedVenues_clearBtn.setDisable(true);
        requestedVenues_eventDate.setDisable(true);
        requestedVenues_parkingPrice.setDisable(true);
        requestedVenues_cateringPrice.setDisable(true);
        requestedVenues_capacity.setDisable(true);
        requestedVenues_avaiCapacity.setText("requested Capacity: 0");

    }
    public void selectVenue() {

        Alert alert;
        if(getData.bookId <= 0) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a requested venue");
            alert.showAndWait();
            return;
        }

        String uri = "file:src/main/resources/images/" + getData.path;
        Image image = new Image(uri, 135, 166, false, true);
        requestedVenues_imageView.setImage(image);
        requestedVenues_imageTitle.setText(getData.vname);

        // clear text labels
        requestedVenues_name.setText("");
        requestedVenues_cname.setText("");
        requestedVenues_reqDate.setText("");

        showSpinnerValue();
    }




//    -------------------------------------End requested Venues---------------------------------------------------

//    Add Venues Page Functions

    public void searchAddVenues() {
        FilteredList<venuesData> filter = new FilteredList<>(listAddVenues, e -> true);

        // Set up the SortedList once
        SortedList<venuesData> sortedData = new SortedList<>(filter);
        sortedData.comparatorProperty().bind(addVenues_tableView.comparatorProperty());
        addVenues_tableView.setItems(sortedData);

        addVenues_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateVenuesData -> {
                // If the search field is empty, show all items
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String keySearch = newValue.toLowerCase();

                // Match if any field contains the search string
                return predicateVenuesData.getName().toLowerCase().contains(keySearch) ||
                        predicateVenuesData.getLocation().toLowerCase().contains(keySearch) ||
                        String.valueOf(predicateVenuesData.getCapacity()).toLowerCase().contains(keySearch) ||
                        predicateVenuesData.getDate().toString().toLowerCase().contains(keySearch);
            });
        });
    }

    public void importImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));

        Stage stage = (Stage) addVenues_form.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if (file != null) {
            Image img = new Image(file.toURI().toString(), 140, 172, false, true);
            addVenues_imageView.setImage(img);

            getData.path = file.getName();
        }
    }

    public void insertAddVenues() {
        String sql = "INSERT INTO venue (name, location, capacity, image, available_date, description) VALUES (?, ?, ?, ?, ?, ?) ";
        String sql1 = "SELECT * FROM venue WHERE name = ?";
        connect = Database.connectDb();
        Alert alert;

        try {
            if (addVenues_venueName.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter venue name");
                alert.showAndWait();
                return;
            }

            ps = connect.prepareStatement(sql1);
            ps.setString(1, addVenues_venueName.getText());
            rs = ps.executeQuery();

            if (rs.next()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText(addVenues_venueName.getText() + " was already exists!");
                alert.showAndWait();

            } else {

                String uri = getData.path;

                if (getData.path == null
                        || addVenues_location.getText().isEmpty()
                        || addVenues_capacity.getText().isEmpty()
                        || addVenues_date.getValue() == null
                        ||addVenues_description.getText().isEmpty() ) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    if (getData.path == null) {
                        alert.setContentText("Please select an image file");
                    } else if (addVenues_location.getText().isEmpty()) {
                        alert.setContentText("Please enter a location");
                    } else if (addVenues_capacity.getText().isEmpty()) {
                        alert.setContentText("Please enter capacity");
                    } else if (addVenues_date.getValue() == null) {
                        alert.setContentText("Please select a date");
                    } else if (addVenues_description.getText().isEmpty()) {
                        alert.setContentText("Please enter a description");
                    }
                    alert.showAndWait();
                    return;
                }

                ps = connect.prepareStatement(sql);
                ps.setString(1, addVenues_venueName.getText());
                ps.setString(2, addVenues_location.getText());
                ps.setString(3, addVenues_capacity.getText());
                ps.setString(4, uri);
                ps.setString(5, String.valueOf(addVenues_date.getValue()));
                ps.setString(6, addVenues_description.getText());

                ps.execute();

                // Re-calling show function to refresh the table
                showAddVenuesList();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Venue added successfully");
                alert.showAndWait();


                clearAddVenuesList();
            }
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void updateAddVenues() {
        String sql = "UPDATE venue SET name = ? , location = ? , capacity = ? , image = ? , available_date = ?, description = ?" +
                " WHERE venue_id = ?";
        String sql1 = "SELECT * FROM venue WHERE name = ? and venue_id != ?";
        connect = Database.connectDb();
        Alert alert;
        try {
            if (addVenues_venueName.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a venue");
                alert.showAndWait();
                return;
            }

            ps = connect.prepareStatement(sql1);
            ps.setString(1, addVenues_venueName.getText());
            ps.setInt(2, selectedVenueId);
            rs = ps.executeQuery();
            if (rs.next()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText(addVenues_venueName.getText() + " already exists!");
                alert.showAndWait();

            } else {

                String uri = getData.path;

                if (getData.path == null
                        || addVenues_location.getText().isEmpty()
                        || addVenues_capacity.getText().isEmpty()
                        || addVenues_date.getValue() == null
                        || addVenues_description.getText().isEmpty() ) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    if (getData.path == null) {
                        alert.setContentText("Please select an image file");
                    } else if (addVenues_location.getText().isEmpty()) {
                        alert.setContentText("Please enter location");
                    } else if (addVenues_capacity.getText().isEmpty()) {
                        alert.setContentText("Please enter capacity");
                    } else if (addVenues_date.getValue() == null) {
                        alert.setContentText("Please select a date");
                    } else if (addVenues_description.getText().isEmpty()) {
                        alert.setContentText("Please enter a description");
                    }
                    alert.showAndWait();
                    return;
                }

                ps = connect.prepareStatement(sql);
                ps.setString(1, addVenues_venueName.getText());
                ps.setString(2, addVenues_location.getText());
                ps.setString(3, addVenues_capacity.getText());
                ps.setString(4, uri);
                ps.setString(5, String.valueOf(addVenues_date.getValue()));
                ps.setString(6, addVenues_description.getText());
                ps.setInt(7, selectedVenueId);
                ps.executeUpdate();

                // Re-calling show function to refresh the table
                showAddVenuesList();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Venue updated successfully");
                alert.showAndWait();

                clearAddVenuesList();
            }
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }


    }

    public void deleteAddVenues() {
        String sql = "DELETE FROM venue WHERE venue_id = ?";
        String sqlCheck = "SELECT * FROM venue JOIN booking ON venue.venue_id = booking.venue_id";

        connect = Database.connectDb();
        Alert alert;



        if (selectedVenueId == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a venue to delete");
            alert.showAndWait();
            return;
        }else {

            // Check if the venue is booked
            try{
                ps = connect.prepareStatement(sqlCheck);
                rs = ps.executeQuery();
                if(rs.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cannot delete a venue that is booked by at least one customer.");
                    alert.showAndWait();
                    return;
                }
            }
            catch(SQLException e){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Error: " + e.getMessage());
                alert.showAndWait();
            }



            // confirmation dialog shown
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);

            alert.setContentText("Are you sure you want to delete '" + addVenues_venueName.getText() + "' venue ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    ps = connect.prepareStatement(sql);
                    ps.setInt(1, selectedVenueId);
                    ps.executeUpdate();

                    // Re-calling show function to refresh the table
                    showAddVenuesList();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Venue deleted successfully");
                    alert.showAndWait();


                    clearAddVenuesList();
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Error: " + e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    public void clearAddVenuesList() {
        selectedVenueId = 0;
        addVenues_venueName.clear();
        addVenues_location.clear();
        addVenues_capacity.clear();
        addVenues_imageView.setImage(null);
        addVenues_date.setValue(null);
        addVenues_description.clear();


    }

    // Storing data in list of objects and returning it
    public ObservableList<venuesData> addVenuesList() {

        ObservableList<venuesData> addVenuesList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM venue";
        connect = Database.connectDb();


        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            venuesData venD;
            while (rs.next()) {
                LocalDate localDate = rs.getDate("available_date").toLocalDate();

                venD = new venuesData(rs.getInt("venue_id"), rs.getString("name"), rs.getString("location"),
                        rs.getInt("capacity"), rs.getString("image"), localDate, rs.getString("description"));
                addVenuesList.add(venD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addVenuesList;
    }

    // Displaying data in a table
    private ObservableList<venuesData> listAddVenues;

    public void showAddVenuesList() {
        listAddVenues = addVenuesList();
        addVenues_col_venueName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addVenues_col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        addVenues_col_capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        addVenues_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addVenues_tableView.setItems(listAddVenues);

    }

    int selectedVenueId = 0;

    // Clicking a raw in a table for an edit or delete
    public void selectAddVenuesList() {
        venuesData venD = addVenues_tableView.getSelectionModel().getSelectedItem();
        int num = addVenues_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        selectedVenueId = venD.getMovieId();
        addVenues_venueName.setText(venD.getName());
        addVenues_location.setText(venD.getLocation());
        addVenues_capacity.setText(String.valueOf(venD.getCapacity()));
        addVenues_description.setText(venD.getDescription());

        LocalDate localDate = venD.getDate();
        addVenues_date.setValue(localDate);

        getData.path = venD.getImage();
        String uri = "file:src/main/resources/images/" + venD.getImage();
        Image image = new Image(uri, 140, 172, false, true);
        addVenues_imageView.setImage(image);
    }

//    -------------------------------------End Add Venues---------------------------------------------------

//    General Functions

    public void logout() {
        // add a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to sign out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ((Stage) signout.getScene().getWindow()).close();

            try {

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {
                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);
                });
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("Error: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void switchForm(ActionEvent event) {

        dashboard_form.setVisible(false);
        addVenues_form.setVisible(false);
        requestedVenues_form.setVisible(false);
        customers_form.setVisible(false);

        dashboard_btn.setStyle("-fx-background-color:transparent");
        addVenues_btn.setStyle("-fx-background-color:transparent");
        requestedVenues_btn.setStyle("-fx-background-color:transparent");
        customers_btn.setStyle("-fx-background-color:transparent");

        // Switch to the selected form
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            dashboard_btn.setStyle("-fx-background-color:#ae2d3c");

            showChartData();
            showCardsData();
        } else if (event.getSource() == addVenues_btn) {
            addVenues_form.setVisible(true);
            addVenues_btn.setStyle("-fx-background-color:#ae2d3c");

            showAddVenuesList();
            clearAddVenuesList();

        } else if (event.getSource() == requestedVenues_btn) {
            requestedVenues_form.setVisible(true);
            requestedVenues_btn.setStyle("-fx-background-color:#ae2d3c");

            showRequestedVenues();
            showSpinnerValue();

            // clear
            requestedVenues_name.setText("");
            requestedVenues_cname.setText("");
            requestedVenues_reqDate.setText("");
            getData.bookId = 0;

        } else if (event.getSource() == customers_btn) {
            customers_form.setVisible(true);
            customers_btn.setStyle("-fx-background-color:#ae2d3c");

            showCustomerList();
        }
    }

    public void displayUsername() {
        username.setText(getData.username);
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) topForm.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Sidebar
        displayUsername();

        // Dashboard
        showCardsData();
        showChartData();

        // Add Venues page
        showAddVenuesList();

        Pattern validNumberPattern = Pattern.compile("-?\\d*");
        // Create a TextFormatter and apply it to the TextField
        TextFormatter<String> textFormatter1 = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
            if (validNumberPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            }
            return null;
        });
        addVenues_capacity.setTextFormatter(textFormatter1);

        // requested Venues page
        showRequestedVenues();
        showSpinnerValue();

        TextFormatter<String> textFormatter2 = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
            if (validNumberPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            }
            return null;
        });
        requestedVenues_capacity.setTextFormatter(textFormatter2);

     // Customer page
        showCustomerList();

        TextFormatter<String> textFormatter3 = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
            if (validNumberPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            }
            return null;
        });
        customers_payCash.setTextFormatter(textFormatter3);

    }
}



