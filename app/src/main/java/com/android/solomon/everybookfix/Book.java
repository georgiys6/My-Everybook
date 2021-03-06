package com.android.solomon.everybookfix;




import java.util.Date;
import java.util.UUID;

public class Book {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mTime;
    private boolean mSolved;
    private String mSuspect;

    public Book(){
        this(UUID.randomUUID());

    }
    public Book(UUID id){
        mId=id;
        mDate=new Date();

    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }



    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getPhotoFilename(){
        return "IMG_"+getId().toString()+".jpg";
    }
}