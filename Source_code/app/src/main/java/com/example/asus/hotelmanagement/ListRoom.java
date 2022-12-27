package com.example.asus.hotelmanagement;

/**
 * Created by ASUS on 13-May-18.
 */

public class ListRoom {
    private String room_no;
    private String room_type;
    private String price;


    // Constructor
    public ListRoom(String room_no, String room_type, String price) {
        this.room_no = room_no;
        this.room_type = room_type;
        this.price = price;
    }

    public String getRoom_no() {
        return room_no;
    }

    public String getRoom_type() {
        return room_type;
    }

    public String getPrice() {
        return price;
    }
}
