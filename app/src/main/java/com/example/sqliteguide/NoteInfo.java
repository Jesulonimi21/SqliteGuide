package com.example.sqliteguide;

public class NoteInfo {
    String mText;
    String mTitle;
    String mCourse;
    int mId;


    public NoteInfo(String mText, String mTitle, String mCourse,int mId) {
        this.mText = mText;
        this.mTitle = mTitle;
        this.mCourse = mCourse;
        this.mId=mId;
    }

    public String getmText() {
        return mText;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCourse() {
        return mCourse;
    }

    public void setmCourse(String mCourse) {
        this.mCourse = mCourse;
    }
}
