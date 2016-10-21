package com.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuhuiliang on 16/10/19.
 */

public class UserModel implements Parcelable {

    public String userid;
    public String images;
    public String nickname;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userid);
        dest.writeString(this.images);
        dest.writeString(this.nickname);
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.userid = in.readString();
        this.images = in.readString();
        this.nickname = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
