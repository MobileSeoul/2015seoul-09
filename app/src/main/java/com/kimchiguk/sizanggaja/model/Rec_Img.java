package com.kimchiguk.sizanggaja.model;

/**
 * Created by MinJae on 2015-10-18.
 */
public class Rec_Img {
    private String id;
    private String url;
    private String inforStr;
    private String mainsale;

    public Rec_Img(String id,String url,String inforStr,String mainsale) {
        this.id = id;
        this.url = url;
        this.inforStr = inforStr;
        this.mainsale = mainsale;
    }

    public String getId() {return id;}
    public String getURL() {
        return url;
    }
    public String getInforStr () {
        return inforStr;
    }
    public String getMainsale() {
        return mainsale;
    }


}
