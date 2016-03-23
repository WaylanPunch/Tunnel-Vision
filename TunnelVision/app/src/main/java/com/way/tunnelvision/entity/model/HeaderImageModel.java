package com.way.tunnelvision.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pc on 2016/1/10.
 */
public class HeaderImageModel implements Parcelable {
    private Long id;

    /**
     * 标题
     */
    private String title;
    /**
     * 原文链接
     */
    private String link;
    /**
     * 描述
     */
    private String description;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 图片大小
     */
    private Long length;
    /**
     * 图片类型
     */
    private String type;
    /**
     * 图片唯一码
     */
    private String guid;
    /**
     * 发布日期
     */
    private String pubDate;

    public Long getId() {
        return id;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public HeaderImageModel() {
    }

    public HeaderImageModel(Long id) {
        this.id = id;
    }

    public HeaderImageModel(Long id, String title, String link, String description, String url, Long length, String type, String guid, String pubDate) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.url = url;
        this.length = length;
        this.type = type;
        this.guid = guid;
        this.pubDate = pubDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeValue(this.length);
        dest.writeString(this.type);
        dest.writeString(this.guid);
        dest.writeString(this.pubDate);
    }

    protected HeaderImageModel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.link = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.length = (Long) in.readValue(Long.class.getClassLoader());
        this.type = in.readString();
        this.guid = in.readString();
        this.pubDate = in.readString();
    }

    public static final Parcelable.Creator<HeaderImageModel> CREATOR = new Parcelable.Creator<HeaderImageModel>() {
        public HeaderImageModel createFromParcel(Parcel source) {
            return new HeaderImageModel(source);
        }

        public HeaderImageModel[] newArray(int size) {
            return new HeaderImageModel[size];
        }
    };
}
