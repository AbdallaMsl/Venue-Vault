package com.example.hellofx1;

import java.time.LocalDate;

public class customerData {

    private int counter;
    private int id;
    private int booking_id;
    private String cname;
    private String vname;
    private LocalDate eventDate;
    private double amount;
    private String paymentDate;
    private double total_price;


    public customerData( int counter, int id, int booking_id, String cname, String vname, LocalDate eventDate, double amount, String paymentDate, double total_price) {
        this.counter = counter;
        this.id = id;
        this.booking_id = booking_id;
        this.cname = cname;
        this.vname = vname;
        this.eventDate = eventDate;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.total_price = total_price;
    }

    public int getCounter() {
        return counter;
    }

    public int getId() {
        return id;
    }

    public int getBookingId() {
        return booking_id;
    }

    public String getCname() {
        return cname;
    }

    public String getVname() {
        return vname;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public double getTotal_price() {
        return total_price;
    }
}