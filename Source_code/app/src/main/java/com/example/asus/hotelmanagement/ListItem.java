package com.example.asus.hotelmanagement;

/**
 * Created by ASUS on 21-Apr-18.
 */

public class ListItem {
    private String room_no;
    private String room_type;
    private String price;
    private String description;


    // Constructor
    public ListItem(String room_no, String room_type, String price,String description) {
        this.room_no = room_no;
        this.room_type = room_type;
        this.price = price;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
