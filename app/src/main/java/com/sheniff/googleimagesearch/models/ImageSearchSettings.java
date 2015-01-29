package com.sheniff.googleimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sheniff on 1/29/15.
 */
public class ImageSearchSettings implements Parcelable {
    private String size;
    private String color;
    private String type;
    private String site;

    public ImageSearchSettings() {
        this.size = "";
        this.color = "";
        this.type = "";
        this.site = "";
    }

    public ImageSearchSettings(Parcel in) {
        this.size = in.readString();
        this.color = in.readString();
        this.type = in.readString();
        this.site = in.readString();
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(size);
        dest.writeString(color);
        dest.writeString(type);
        dest.writeString(site);
    }

    public static final Creator<ImageSearchSettings> CREATOR = new Creator<ImageSearchSettings>() {

        @Override
        public ImageSearchSettings createFromParcel(Parcel source) {
            return new ImageSearchSettings(source);
        }

        @Override
        public ImageSearchSettings[] newArray(int size) {
            return new ImageSearchSettings[0];
        }
    };
}
