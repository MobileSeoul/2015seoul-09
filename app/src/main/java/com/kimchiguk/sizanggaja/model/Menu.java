package com.kimchiguk.sizanggaja.model;

/**
 * Created by MinJae on 2015-10-28.
 */
public class Menu {
    String name;
    int price;
    String url;


    public Menu(String name, int price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public String getURL() { return url;}
    public String getName() {return name;}
    public int getPrice() {return price;}

}
