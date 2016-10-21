package com.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuhuiliang on 16/10/14.
 */

public class PictureModel implements Parcelable {


    public String url;
    public String original;
    public int height;
    public int width;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.original);
        dest.writeInt(this.height);
        dest.writeInt(this.width);
    }

    public PictureModel() {
    }

    protected PictureModel(Parcel in) {
        this.url = in.readString();
        this.original = in.readString();
        this.height = in.readInt();
        this.width = in.readInt();
    }

    public static final Creator<PictureModel> CREATOR = new Creator<PictureModel>() {
        @Override
        public PictureModel createFromParcel(Parcel source) {
            return new PictureModel(source);
        }

        @Override
        public PictureModel[] newArray(int size) {
            return new PictureModel[size];
        }
    };

    @Override
    public String toString() {
        return "PictureModel{" +
                "url='" + url + '\'' +
                ", original='" + original + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}

