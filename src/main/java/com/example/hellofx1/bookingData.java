package com.example.hellofx1;

import java.time.LocalDate;

public class bookingData {
    private int bookingId;
    private String vname;
    private String cname;
    private String phoneNumber;
    private String image;
    private LocalDate requestDate;
    private int venueId;
    private int customerId;
    private String email;

    public bookingData(int bookingId, String vname, String cname, String phoneNumber, LocalDate requestDate, String image, int venueId, int customerId, String email) {
        this.bookingId = bookingId;
        this.vname = vname;
        this.cname = cname;
        this.phoneNumber = phoneNumber;
        this.requestDate = requestDate;
        this.image = image;
        this.venueId = venueId;
        this.customerId = customerId;
        this.email = email;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getVname() {
        return vname;
    }

    public String getCname() {
        return cname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public String getImage() {
        return image;
    }

    public int getVenueId() {
        return venueId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }
}
