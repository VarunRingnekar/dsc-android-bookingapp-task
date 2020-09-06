package com.example.bookingapp;

/*Model class for clients*/

public class ClientClass {

    private String clientName, clientAddress;

    public ClientClass(){

    }

    public ClientClass(String clientName, String clientAddress) {
        this.clientName = clientName;
        this.clientAddress = clientAddress;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }
}
