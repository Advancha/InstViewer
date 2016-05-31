package com.example.afanasenko.instviewer;

/**
 * Created by Afanasenko on 31.05.2016.
 */
public class ImageData {
    public String media;
    public String  link;

    public ImageData(String media, String link) {
        this.media = media;
        this.link = link;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
