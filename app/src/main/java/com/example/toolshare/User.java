package com.example.toolshare;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String city;
    private String imgUrl;

    public User(){

    }

    public User(String id, String fullName, String email, String phoneNumber, String city) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.imgUrl = "default";
    }

    public User(String id, String fullName, String email, String imgUrl) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    protected User(Parcel in) {
        id = in.readString();
        fullName = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        city = in.readString();
        imgUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setId(String id){
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(fullName);
        parcel.writeString(email);
        parcel.writeString(phoneNumber);
        parcel.writeString(city);
        parcel.writeString(imgUrl);
    }
}
