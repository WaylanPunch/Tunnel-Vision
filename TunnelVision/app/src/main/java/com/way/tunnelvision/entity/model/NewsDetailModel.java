package com.way.tunnelvision.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pc on 2016/2/28.
 */
public class NewsDetailModel implements Parcelable {

    private Long id;


    /**
     * docid
     */
    private String docid;
    /**
     * title
     */
    private String title;
    /**
     * source
     */
    private String source;
    /**
     * body
     */
    private String body;
    /**
     * ptime
     */
    private String ptime;
    /**
     * cover
     */
    private String cover;
    /**
     * 图片列表
     */
    private List<String> imgList;

    private String imgArrayString;


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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getImgArrayString() {
        return this.imgArrayString;
    }

    public void setImgArrayString(String imgArrayString) {
        this.imgArrayString = imgArrayString;
    }

    public NewsDetailModel() {
    }

    public NewsDetailModel(Long id) {
        this.id = id;
    }

    public NewsDetailModel(Long id, String docid, String title, String source, String body, String ptime, String cover, String imgArrayString) {
        this.id = id;
        this.docid = docid;
        this.title = title;
        this.source = source;
        this.body = body;
        this.ptime = ptime;
        this.cover = cover;
        this.imgArrayString = imgArrayString;
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
        dest.writeString(this.source);
        dest.writeString(this.body);
        dest.writeString(this.ptime);
        dest.writeString(this.cover);
        dest.writeStringList(this.imgList);
        dest.writeString(this.imgArrayString);
    }

    protected NewsDetailModel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.docid = in.readString();
        this.title = in.readString();
        this.source = in.readString();
        this.body = in.readString();
        this.ptime = in.readString();
        this.cover = in.readString();
        this.imgList = in.createStringArrayList();
        this.imgArrayString = in.readString();
    }

    public static final Creator<NewsDetailModel> CREATOR = new Creator<NewsDetailModel>() {
        public NewsDetailModel createFromParcel(Parcel source) {
            return new NewsDetailModel(source);
        }

        public NewsDetailModel[] newArray(int size) {
            return new NewsDetailModel[size];
        }
    };
}
