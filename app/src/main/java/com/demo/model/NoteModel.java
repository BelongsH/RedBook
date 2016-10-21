package com.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liuhuiliang on 16/10/14.
 */

public class NoteModel implements Parcelable, HomeModel {

    public String title;

    public String desc;

    @SerializedName("images_list")
    public List<PictureModel> pictures;

    public UserModel user;

    public int likes;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeTypedList(pictures);
    }

    public NoteModel() {
    }

    protected NoteModel(Parcel in) {
        this.title = in.readString();
        this.desc = in.readString();
        this.pictures = in.createTypedArrayList(PictureModel.CREATOR);
    }

    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel source) {
            return new NoteModel(source);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };

    @Override
    public String toString() {
        return "NoteModel{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", pictures=" + pictures +
                '}';
    }
}
