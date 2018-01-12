package com.byjw.rxjava;

import android.graphics.drawable.Drawable;

/**
 * Created by jungwoon on 2018. 1. 5..
 */

public class RecyclerItem {
    private Drawable image;
    private String title;

    public RecyclerItem(Drawable image, String title) {
        this.image = image;
        this.title = title;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
