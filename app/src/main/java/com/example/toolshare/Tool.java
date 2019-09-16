package com.example.toolshare;

import android.os.Parcel;
import android.os.Parcelable;

public class Tool implements Parcelable {
    private String id;
    private String ownerId;
    private String imgUrl;
    private String name;
    private boolean available;

    public Tool(){

    }

    public Tool(String id, String ownerId, String imgUrl, String toolName, boolean availabel) {
        this.id = id;
        this.ownerId = ownerId;
        this.imgUrl = imgUrl;
        this.name = toolName;
        this.available = availabel;
    }

    protected Tool(Parcel in) {
        id = in.readString();
        ownerId = in.readString();
        imgUrl = in.readString();
        name = in.readString();
        available = in.readByte() != 0;
    }

    public static final Creator<Tool> CREATOR = new Creator<Tool>() {
        @Override
        public Tool createFromParcel(Parcel in) {
            return new Tool(in);
        }

        @Override
        public Tool[] newArray(int size) {
            return new Tool[size];
        }
    };

    public void setId(String id){
        this.id = id;
    }

    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String toolName) {
        this.name = toolName;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(ownerId);
        parcel.writeString(imgUrl);
        parcel.writeString(name);
        parcel.writeInt(available?1:0);
    }
}
