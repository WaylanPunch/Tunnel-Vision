package com.way.tunnelvision.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pc on 2016/2/20.
 */
public class ChannelModel implements Parcelable {

    private Long id;

    /**
     * Not-null value.
     */
    private String channelGUID;
    private String channelTitle;
    private String channelName;
    private String channelLink;
    private int channelChosen;//Sqlite没有单独的布尔存储类型，它使用INTEGER作为存储类型，0为false，1为true

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelGUID() {
        return channelGUID;
    }

    public void setChannelGUID(String channelGUID) {
        this.channelGUID = channelGUID;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelLink() {
        return channelLink;
    }

    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }

    public int getChannelChosen() {
        return channelChosen;
    }

    public void setChannelChosen(int channelChosen) {
        this.channelChosen = channelChosen;
    }

    public ChannelModel() {

    }

    public ChannelModel(Long id) {
        this.id = id;
    }

    public ChannelModel(Long id, String channelGUID, String channelTitle, String channelName, String channelLink, int channelChosen) {
        this.id = id;
        this.channelGUID = channelGUID;
        this.channelTitle = channelTitle;
        this.channelName = channelName;
        this.channelLink = channelLink;
        this.channelChosen = channelChosen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.channelGUID);
        dest.writeString(this.channelTitle);
        dest.writeString(this.channelName);
        dest.writeString(this.channelLink);
        dest.writeInt(this.channelChosen);
    }

    protected ChannelModel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.channelGUID = in.readString();
        this.channelTitle = in.readString();
        this.channelName = in.readString();
        this.channelLink = in.readString();
        this.channelChosen = in.readInt();
    }

    public static final Parcelable.Creator<ChannelModel> CREATOR = new Parcelable.Creator<ChannelModel>() {
        public ChannelModel createFromParcel(Parcel source) {
            return new ChannelModel(source);
        }

        public ChannelModel[] newArray(int size) {
            return new ChannelModel[size];
        }
    };
}
