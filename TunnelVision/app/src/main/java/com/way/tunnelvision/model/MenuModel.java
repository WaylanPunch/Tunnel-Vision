package com.way.tunnelvision.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.way.tunnelvision.util.StringUtil;

/**
 * Entity mapped to table "MenuModel".
 *
 * Created by pc on 2016/1/10.
 */
public class MenuModel implements Parcelable {
    private Long id;

    /**
     * Not-null value.
     */
    private String menuGUID;
    private String menuTitle;
    private String menuLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuGUID() {
        return menuGUID;
    }

    public void setMenuGUID(String menuGUID) {
        this.menuGUID = menuGUID;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuInitial() {
        return StringUtil.getInitialOfString(this.menuTitle);
    }

    /*
    public void setMenuInitial(String menuInitial) {
        this.menuInitial = menuInitial;
    }
    */

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public MenuModel() {
    }

    public MenuModel(Long id) {
        this.id = id;
    }

    public MenuModel(Long id, String menuGUID, String menuTitle, String menuLink) {
        this.id = id;
        this.menuGUID = menuGUID;
        this.menuTitle = menuTitle;
        this.menuLink = menuLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.menuGUID);
        dest.writeString(this.menuTitle);
        dest.writeString(this.menuLink);
    }

    protected MenuModel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.menuGUID = in.readString();
        this.menuTitle = in.readString();
        this.menuLink = in.readString();
    }

    public static final Creator<MenuModel> CREATOR = new Creator<MenuModel>() {
        public MenuModel createFromParcel(Parcel source) {
            return new MenuModel(source);
        }

        public MenuModel[] newArray(int size) {
            return new MenuModel[size];
        }
    };
}
