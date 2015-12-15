package com.way.tunnelvision.entity;

/**
 * Created by pc on 2015/12/13.
 */
public class Friend {
    private String iconResourceId;
    private String userId;
    private String username;
    private String signature;
    private String displayName;
    private int gender;

    public String getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(String iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Friend() {
        setGender(0);
    }

}
