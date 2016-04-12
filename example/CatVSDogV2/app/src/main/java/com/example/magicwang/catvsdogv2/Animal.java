package com.example.magicwang.catvsdogv2;

/**
 * Created by MagicWang on 2016/2/26.
 */
public class Animal {
    private String aName;
    private String aSpeak;
    private int aIcon;
    private String animal_Content;

    public Animal() {}

    public Animal(String aName, String aSpeak, int aIcon,String animal_Content) {
        this.aName = aName;
        this.aSpeak = aSpeak;
        this.aIcon = aIcon;
        this.animal_Content=animal_Content;
    }

    public String getaName() {
        return aName;
    }

    public String getaSpeak() {
        return aSpeak;
    }

    public int getaIcon() {
        return aIcon;
    }

    public String getAnimalContent(){
        return animal_Content;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public void setaSpeak(String aSpeak) {
        this.aSpeak = aSpeak;
    }

    public void setaIcon(int aIcon) {
        this.aIcon = aIcon;
    }

    public void setAnimalContent(String animal_Content){
        this.animal_Content=animal_Content;
    }
}
