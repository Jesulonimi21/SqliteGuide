package com.example.sqliteguide;

public class CourseInfo {

    String mCourseId;
    String mTitle;

    public CourseInfo(String mCourseId, String mTitle) {
        this.mCourseId = mCourseId;
        this.mTitle = mTitle;
    }

    public String getmCourseId() {
        return mCourseId;
    }

    public void setmCourseId(String mCourseId) {
        this.mCourseId = mCourseId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
