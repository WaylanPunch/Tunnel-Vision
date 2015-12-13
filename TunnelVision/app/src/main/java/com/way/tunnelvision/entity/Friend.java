package com.way.tunnelvision.entity;

import com.way.tunnelvision.adapter.section.Categorizable;

/**
 * Created by pc on 2015/12/13.
 */
public class Friend implements Categorizable {
    private String iconResourceId;
    private String userId;
    private String username;
    private String signature;
    private String displayName;

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

    public Friend() {
    }

    @Override
    public String getCategory() {
        return this.displayName.substring(0, 1);
    }
}
