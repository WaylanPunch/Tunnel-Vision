package com.way.tunnelvision.entity.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.way.tunnelvision.util.StringUtil;

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
    private int channelType;//频道类型
    private int channelChosen;//0默认, 1选择, 2未选择

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

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public void setChannelChosen(int channelChosen) {
        this.channelChosen = channelChosen;
    }

    public String getChannelTitleInitial() {
        return StringUtil.getInitialOfString(this.channelTitle);
    }

    public ChannelModel() {

    }

    public ChannelModel(Long id) {
        this.id = id;
    }

    public ChannelModel(Long id, String channelGUID, String channelTitle, String channelName, String channelLink, int channelType, int channelChosen) {
        this.id = id;
        this.channelGUID = channelGUID;
        this.channelTitle = channelTitle;
        this.channelName = channelName;
        this.channelLink = channelLink;
        this.channelType = channelType;
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
        dest.writeInt(this.channelType);
        dest.writeInt(this.channelChosen);
    }

    protected ChannelModel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.channelGUID = in.readString();
        this.channelTitle = in.readString();
        this.channelName = in.readString();
        this.channelLink = in.readString();
        this.channelType = in.readInt();
        this.channelChosen = in.readInt();
    }

    public static final Creator<ChannelModel> CREATOR = new Creator<ChannelModel>() {
        public ChannelModel createFromParcel(Parcel source) {
            return new ChannelModel(source);
        }

        public ChannelModel[] newArray(int size) {
            return new ChannelModel[size];
        }
    };
}
