package com.example.asus.hotelmanagement;

/**
 * Created by ASUS on 22-Apr-18.
 */

public class ListService {
    private String service_no;
    private String service_name;
    private String price;

    // Constructor
    public ListService(String service_no, String service_name, String price) {
        this.service_no = service_no;
        this.service_name = service_name;
        this.price = price;
    }

    public String getService_no() {
        return service_no;
    }

    public String getService_name() {
        return service_name;
    }

    public String getPrice() {
        return price;
    }

}
