package com.kimchiguk.sizanggaja.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MinJae on 2015-10-26.
 */
public class StoreForm implements Parcelable{
    private String id;
    private String name;
    private String phone_number;
    private String latitude;
    private String longitude;
    private String blog_url;
    private String main_img;
    private String main_sale;

    public StoreForm(Parcel in) {
        readFromParcel(in);
    }

    public StoreForm(String id, String name, String phone_number,
                     String latitude, String longitude, String blog_url,
                     String main_img, String main_sale) {
        this.id=id;
        this.name=name;
        this.phone_number=phone_number;
        this.latitude=latitude;
        this.longitude=longitude;
        this.blog_url=blog_url;
        this.main_img=main_img;
        this.main_sale=main_sale;

    }
    public String getId() {return id;}
    public String getName() {return name;}
    public String getPhone_number() {return phone_number;}
    public String getLatitude() {return latitude;}
    public String getLongitude() {return longitude;}
    public String getBlog_url() {return blog_url;}
    public String getMain_img() {return main_img;}
    public String getMain_sale() {return main_sale;}

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone_number);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(blog_url);
        dest.writeString(main_img);
        dest.writeString(main_sale);
    }

    public void readFromParcel(Parcel in) {
        id = in.readString();
        name=in.readString();
        phone_number=in.readString();
        latitude=in.readString();
        longitude=in.readString();
        blog_url=in.readString();
        main_img=in.readString();
        main_sale=in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StoreForm createFromParcel(Parcel in) {
            return new StoreForm(in);
        }

        public StoreForm[] newArray(int size) {
            return new StoreForm[size];
        }

    };
    public int describeContents() {
        return 0;
    }


}
