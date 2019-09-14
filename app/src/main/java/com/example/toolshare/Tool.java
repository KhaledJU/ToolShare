package com.example.toolshare;

import android.os.Parcel;
import android.os.Parcelable;

public class Tool implements Parcelable {
    private String ownerId;
    private String imgUrl;
    private String toolName;
    private boolean available;

    public Tool(String ownerId, String imgUrl, String toolName, boolean availabel) {
        this.ownerId = ownerId;
        this.imgUrl = imgUrl;
        this.toolName = toolName;
        this.available = availabel;
    }

    protected Tool(Parcel in) {
        ownerId = in.readString();
        imgUrl = in.readString();
        toolName = in.readString();
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

    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getToolName() {
        return toolName;
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
        parcel.writeString(ownerId);
        parcel.writeString(imgUrl);
        parcel.writeString(toolName);
        parcel.writeInt(available?1:0);
    }
}
