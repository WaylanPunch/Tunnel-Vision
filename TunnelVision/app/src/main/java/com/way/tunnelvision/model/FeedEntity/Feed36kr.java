package com.way.tunnelvision.model.FeedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/1/18.
 */
public class Feed36kr {
    private String title;
    private String language;
    private String pubDate;
    private String generator;
    private String description;
    private String link;
    private List<Feed36krItem> feed36krItems = new ArrayList<Feed36krItem>();

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

    public List<Feed36krItem> getFeed36krItems() {
        return feed36krItems;
    }

    public void setFeed36krItems(List<Feed36krItem> feed36krItems) {
        this.feed36krItems = feed36krItems;
    }
}
