package com.way.tunnelvision.model.FeedEntity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/1/18.
 */
public class RSSFeed implements Parcelable {
    private String title;
    private String language;
    private String pubDate;
    private String generator;
    private String description;
    private String link;
    private List<RSSFeedItem> RSSFeedItems = new ArrayList<RSSFeedItem>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<RSSFeedItem> getRSSFeedItems() {
        return RSSFeedItems;
    }

    public void setRSSFeedItems(List<RSSFeedItem> RSSFeedItems) {
        this.RSSFeedItems = RSSFeedItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.language);
        dest.writeString(this.pubDate);
        dest.writeString(this.generator);
        dest.writeString(this.description);
        dest.writeString(this.link);
        dest.writeList(this.RSSFeedItems);
    }

    public RSSFeed() {
    }

    protected RSSFeed(Parcel in) {
        this.title = in.readString();
        this.language = in.readString();
        this.pubDate = in.readString();
        this.generator = in.readString();
        this.description = in.readString();
        this.link = in.readString();
        this.RSSFeedItems = new ArrayList<RSSFeedItem>();
        in.readList(this.RSSFeedItems, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<RSSFeed> CREATOR = new Parcelable.Creator<RSSFeed>() {
        public RSSFeed createFromParcel(Parcel source) {
            return new RSSFeed(source);
        }

        public RSSFeed[] newArray(int size) {
            return new RSSFeed[size];
        }
    };
}
