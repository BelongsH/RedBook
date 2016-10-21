package com.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuhuiliang on 16/10/20.
 */

public  class SizeModel implements Parcelable {

    private float scaleX;

    public SizeModel caculateScale(PictureModel nowModel, PictureModel offsetModel, float screenWidth) {
        if (offsetModel == null) return new SizeModel();
        float nowZoom = (screenWidth + 0f) / nowModel.width;
        float nextZoom = (screenWidth + 0f) / offsetModel.width;
        float nowZoomHeight = (nowModel.height * nowZoom);
        float nextZoomHeight = (offsetModel.height * nextZoom);
        this.scaleX = nowZoomHeight / nextZoomHeight;
        return this;
    }

    @Override
    public String toString() {
        return "SizeModel{" +
                "scaleX=" + scaleX +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.scaleX);
    }

    public SizeModel() {
    }

    protected SizeModel(Parcel in) {
        this.scaleX = in.readFloat();
    }

    public static final Creator<SizeModel> CREATOR = new Creator<SizeModel>() {
        @Override
        public SizeModel createFromParcel(Parcel source) {
            return new SizeModel(source);
        }

        @Override
        public SizeModel[] newArray(int size) {
            return new SizeModel[size];
        }
    };
}
