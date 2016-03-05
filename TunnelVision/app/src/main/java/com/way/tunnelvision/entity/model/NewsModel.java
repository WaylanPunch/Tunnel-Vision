package com.way.tunnelvision.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pc on 2016/1/10.
 */
public class NewsModel implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.docid);
        dest.writeString(this.title);
        dest.writeString(this.digest);
        dest.writeString(this.imgsrc);
        dest.writeString(this.source);
        dest.writeString(this.ptime);
        dest.writeString(this.tag);
    }

    public NewsModel() {
    }

    protected NewsModel(Parcel in) {
        this.docid = in.readString();
        this.title = in.readString();
        this.digest = in.readString();
        this.imgsrc = in.readString();
        this.source = in.readString();
        this.ptime = in.readString();
        this.tag = in.readString();
    }

    public static final Parcelable.Creator<NewsModel> CREATOR = new Parcelable.Creator<NewsModel>() {
        public NewsModel createFromParcel(Parcel source) {
            return new NewsModel(source);
        }

        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };
}
