package com.way.tunnelvision.entity;

/**
 * Created by pc on 2015/12/7.
 */
public class Post {
    private String userId;
    private String username;
    private String displayName;
    private String avatarResourceId;
    private int gender;
    private String iconResourceId;
    private String content;
    private int repeatsCount;
    private int commentsCount;
    private int likesCount;
    private String createdDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarResourceId() {
        return avatarResourceId;
    }

    public void setAvatarResourceId(String avatarResourceId) {
        this.avatarResourceId = avatarResourceId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

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
