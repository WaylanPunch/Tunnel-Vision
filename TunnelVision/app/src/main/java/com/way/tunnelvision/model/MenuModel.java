package com.way.tunnelvision.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pc on 2016/1/10.
 */
public class MenuModel implements Parcelable {
    private String menuId;
    private String menuTitle;
    private String menuInitial;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuInitial() {
        return menuInitial;
    }

    public void setMenuInitial(String menuInitial) {
        this.menuInitial = menuInitial;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.menuId);
        dest.writeString(this.menuTitle);
        dest.writeString(this.menuInitial);
    }

    public MenuModel() {
    }

    protected MenuModel(Parcel in) {
        this.menuId = in.readString();
        this.menuTitle = in.readString();
        this.menuInitial = in.readString();
    }

    public static final Parcelable.Creator<MenuModel> CREATOR = new Parcelable.Creator<MenuModel>() {
        public MenuModel createFromParcel(Parcel source) {
            return new MenuModel(source);
        }

        public MenuModel[] newArray(int size) {
            return new MenuModel[size];
        }
    };
}
