package com.demo.utils;

/**
 * Created by liuhuiliang on 16/10/21.
 */

public class PictureSize {
    private int originalHeight;
    private int originalWidth;
    private float scale;
    private int scaleHeight;
    private int scaleWidth;


    public static PictureSize caculatePictureSize(int originalHeight, int originalWidth, int screenWidth) {
        PictureSize pictureSize = new PictureSize();
        pictureSize.originalHeight = originalHeight;
        pictureSize.originalWidth = originalWidth;
        pictureSize.scale = (screenWidth + 0f) / originalWidth;
        pictureSize.scaleHeight = (int) (originalHeight * pictureSize.scale);
        pictureSize.scaleWidth = screenWidth;
        return pictureSize;
    }


    public int getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(int originalHeight) {
        this.originalHeight = originalHeight;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(int originalWidth) {
        this.originalWidth = originalWidth;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getScaleHeight() {
        return scaleHeight;
    }

    public void setScaleHeight(int scaleHeight) {
        this.scaleHeight = scaleHeight;
    }

    public int getScaleWidth() {
        return scaleWidth;
    }

    public void setScaleWidth(int scaleWidth) {
        this.scaleWidth = scaleWidth;
    }
}