package com.example.csiapp.utils;

public class ExerciseModel {

    private String bodyPart;
    private String gifUrl;
    private String equipment;
    private String name;
    private String target;

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "ExerciseModel{" +
                "bodyPart='" + bodyPart + '\'' +
                ", gifUrl='" + gifUrl + '\'' +
                ", equipment='" + equipment + '\'' +
                ", name='" + name + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
