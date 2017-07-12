package com.android.pena.david.news4u.model;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david on 06/07/17.
 */

public class Media extends RealmObject{
    @PrimaryKey
    private String url;
    private String format;

    public Media() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
