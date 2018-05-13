package com.example.android.miwok;

/**
 * Created by karolgrzesiak on 17.07.17.
 */

public class Word {
    private String miwokTranslation;
    private String englishTranslation;
    private int imageResourceId;
    private static final int noImage = 0;
    private int musicResourceId;

    public Word(String miwok, String english,int music){
        miwokTranslation = miwok;
        englishTranslation = english;
        imageResourceId = noImage;
        musicResourceId = music;
    }

    public Word(String miwok, String english, int image, int music){
        miwokTranslation = miwok;
        englishTranslation = english;
        imageResourceId = image;
        musicResourceId = music;
    }
    public int getImageResourceId(){
        return imageResourceId;
    }
    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public boolean hasImage(){
       return imageResourceId!=noImage;
    }

    public int getMusicResourceId(){
        return musicResourceId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "miwokTranslation='" + miwokTranslation + '\'' +
                ", englishTranslation='" + englishTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", musicResourceId=" + musicResourceId +
                '}';
    }
}
