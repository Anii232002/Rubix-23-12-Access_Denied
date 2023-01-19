package com.example.csiapp.Model;

public class PostModel {

    String pImage, pTitle, pDescription;

    public PostModel(String pImage, String pTitle, String pDescription) {
        this.pImage = pImage;
        this.pTitle = pTitle;
        this.pDescription = pDescription;
    }

    public PostModel() {
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

}
