package com.example.bookingapp;

/*Model class for bookings*/

public class BookingClass {

    private String dateTime, clientName, clientAddress;

    public BookingClass() {

    }

    public BookingClass(String dateTime, String clientName, String clientAddress) {
        this.dateTime = dateTime;

        this.clientName = clientName;
        this.clientAddress = clientAddress;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }
}
