package com.way.tunnelvision.entity;

/**
 * Created by pc on 2015/12/11.
 */
public class LatestMessage {
    private String userID;
    private String userName;
    private String displayName;
    private String latestContent;
    private String latestDateTime;
    private String iconResourceId;

    public String getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(String iconresourceId) {
        this.iconResourceId = iconresourceId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLatestContent() {
        return latestContent;
    }

    public void setLatestContent(String latestMessage) {
        this.latestContent = latestMessage;
    }

    public String getLatestDateTime() {
        return latestDateTime;
    }

    public void setLatestDateTime(String latestDateTime) {
        this.latestDateTime = latestDateTime;
    }
}
