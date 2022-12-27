package com.example.asus.hotelmanagement;

/**
 * Created by ASUS on 22-Apr-18.
 */

public class ListReqServices {
    private String room_no;
    private String service_no;

    // Constructor
    public ListReqServices(String room_no, String service_no) {
        this.room_no = room_no;
        this.service_no = service_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public String getService_no() {
        return service_no;
    }
}
