package com.way.tunnelvision.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pc on 2016/1/10.
 */
public class NewsModel implements Parcelable {
    private Long id;

    /**
     * docid
     */
    private String docid;
    /**
     * 标题
     */
    private String title;
    /**
     * 小内容
     */
    private String digest;
    /**
     * 图片地址
     */
    private String imgsrc;
    /**
     * 来源
     */
    private String source;
    /**
     * 时间
     */
    private String ptime;
    /**
     * TAG
     */
    private String tag;

    private int isCollection;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIsCollection() {
        return this.isCollection;
    }

    public void setIsCollection(int collection) {
        this.isCollection = collection;
    }

    public NewsModel() {
    }

    public NewsModel(Long id) {
        this.id = id;
    }

    public NewsModel(Long id, String docid, String title, String digest, String imgsrc, String source, String ptime, String tag, int isCollection) {
        this.id = id;
        this.docid = docid;
        this.title = title;
        this.digest = digest;
        this.imgsrc = imgsrc;
        this.source = source;
        this.ptime = ptime;
        this.tag = tag;
        this.isCollection = isCollection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.docid);
        dest.writeString(this.title);
        dest.writeString(this.digest);
        dest.writeString(this.imgsrc);
        dest.writeString(this.source);
        dest.writeString(this.ptime);
        dest.writeString(this.tag);
        dest.writeInt(this.isCollection);
    }

    protected NewsModel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.docid = in.readString();
        this.title = in.readString();
        this.digest = in.readString();
        this.imgsrc = in.readString();
        this.source = in.readString();
        this.ptime = in.readString();
        this.tag = in.readString();
        this.isCollection = in.readInt();
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
