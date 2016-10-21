package com.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuhuiliang on 16/10/21.
 */

public class CommentModel implements HomeModel, Parcelable {


    public String content;
    public int like_count;
    public String time;
    public UserModel user;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeInt(this.like_count);
        dest.writeString(this.time);
        dest.writeParcelable(this.user, flags);
    }

    public CommentModel() {
    }

    protected CommentModel(Parcel in) {
        this.content = in.readString();
        this.like_count = in.readInt();
        this.time = in.readString();
        this.user = in.readParcelable(UserModel.class.getClassLoader());
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel source) {
            return new CommentModel(source);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };
}
