package com.way.tunnelvision.entity;

/**
 * Created by pc on 2015/12/7.
 */
public class Post {
    private String iconResourceId;
    private String content;
    private int repeatsCount;
    private int commentsCount;
    private int likesCount;
    private String createdDate;

    public int getRepeatsCount() {
        return repeatsCount;
    }

    public void setRepeatsCount(int repeatsCount) {
        this.repeatsCount = repeatsCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(String iconResourceId) {
        this.iconResourceId = iconResourceId;
    }
}
