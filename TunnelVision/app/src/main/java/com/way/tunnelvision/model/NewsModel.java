package com.way.tunnelvision.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by pc on 2016/1/10.
 */
public class NewsModel implements Parcelable {
    private String newsId;
    private String newsIcon;
    private String newsTitle;
    private String newsDescription;
    private int newsType;
    private Date newsTime;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsIcon() {
        return newsIcon;
    }

    public void setNewsIcon(String newsIcon) {
        this.newsIcon = newsIcon;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public Date getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(Date newsTime) {
        this.newsTime = newsTime;
    }

    public NewsModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.newsId);
        dest.writeString(this.newsIcon);
        dest.writeString(this.newsTitle);
        dest.writeString(this.newsDescription);
        dest.writeInt(this.newsType);
        dest.writeLong(newsTime != null ? newsTime.getTime() : -1);
    }

    protected NewsModel(Parcel in) {
        this.newsId = in.readString();
        this.newsIcon = in.readString();
        this.newsTitle = in.readString();
        this.newsDescription = in.readString();
        this.newsType = in.readInt();
        long tmpNewsTime = in.readLong();
        this.newsTime = tmpNewsTime == -1 ? null : new Date(tmpNewsTime);
    }

    public static final Creator<NewsModel> CREATOR = new Creator<NewsModel>() {
        public NewsModel createFromParcel(Parcel source) {
            return new NewsModel(source);
        }

        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };
}
