package com.way.tunnelvision.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pc on 2016/3/12.
 */
public class ImageModel implements Parcelable {
    private Long id;
    private String title;
    private String thumburl;
    private String sourceurl;
    private int height;
    private int width;
    private int isCollection;//1 Yes, 0 No

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getIsCollection() {
        return this.isCollection;
    }

    public void setIsCollection(int collection) {
        this.isCollection = collection;
    }
    public ImageModel() {
    }

    public ImageModel(Long id) {
        this.id = id;
    }

    public ImageModel(Long id, String title, String thumburl, String sourceurl, int height, int width, int isCollection) {
        this.id = id;
        this.title = title;
        this.thumburl = thumburl;
        this.sourceurl = sourceurl;
        this.height = height;
        this.width = width;
        this.isCollection = isCollection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.thumburl);
        dest.writeString(this.sourceurl);
        dest.writeInt(this.height);
        dest.writeInt(this.width);
        dest.writeInt(this.isCollection);
    }

    protected ImageModel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.thumburl = in.readString();
        this.sourceurl = in.readString();
        this.height = in.readInt();
        this.width = in.readInt();
        this.isCollection = in.readInt();
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        public ImageModel createFromParcel(Parcel source) {
            return new ImageModel(source);
        }

        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };
}
