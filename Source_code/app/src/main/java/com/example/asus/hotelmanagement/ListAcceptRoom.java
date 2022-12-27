package com.example.asus.hotelmanagement;

/**
 * Created by ASUS on 23-Apr-18.
 */

public class ListAcceptRoom {
    private String room_no;
    private String check_in;
    private String check_out;
    private String status;

    public ListAcceptRoom(String room_no, String check_in, String check_out, String status) {
        this.room_no = room_no;
        this.check_in = check_in;
        this.check_out = check_out;
        this.status = status;
    }

    public String getRoom_no() {
        return room_no;
    }

    public String getCheck_in() {
        return check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public String getStatus() {
        return status;
    }
}
