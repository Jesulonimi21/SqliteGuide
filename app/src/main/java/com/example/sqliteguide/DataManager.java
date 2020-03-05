package com.example.sqliteguide;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.sqliteguide.NoteKeeperDatabaseContract.*;

public final class DataManager {

    public static DataManager ourInstance=null;
    List<CourseInfo> mCourses=new ArrayList<>();
    List<NoteInfo> mNotes=new ArrayList<>();

    public static DataManager getInstance(){
        if(ourInstance==null){
            ourInstance=new DataManager();
        }
        return ourInstance;
    }
    public static void loadAllInfoFromDatabase(NoteKeeperOpenHelper noteKeeperOpenHelper) {
        SQLiteDatabase db = noteKeeperOpenHelper.getReadableDatabase();
        String[] courseColumns = { CourseInfoEntry.COLUMN_COURSE_ID,CourseInfoEntry.COLUMN_COURSE_TITLE};
        Cursor courseCursor = db.query(CourseInfoEntry.TABLE_NAME, courseColumns, null, null, null, null, CourseInfoEntry.COLUMN_COURSE_TITLE+" DESC");
        loadCoursesFromDatabase(courseCursor);
        String[] noteColumns = {NoteInfoEntry.COLUMN_COURSE_ID, NoteInfoEntry.COLUMN_NOTE_TEXT, NoteInfoEntry.COLUMN_NOTE_TITLE,NoteInfoEntry._ID};
        String notesOrderBy=NoteInfoEntry.COLUMN_COURSE_ID+","+NoteInfoEntry.COLUMN_NOTE_TITLE;
       Log.d("lonimi",NoteInfoEntry.SQL_CREATE_TABLE);
        Cursor notesCursor = db.query(NoteInfoEntry.TABLE_NAME, noteColumns, null, null, null, null, notesOrderBy);
        loadNotesFromDatabase(notesCursor);
    }

    private static void loadNotesFromDatabase(Cursor cursor) {
        int noteTitlePos=cursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TITLE);
        int noteTextPos=cursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TEXT);
        int noteCourseIdPos=cursor.getColumnIndex(NoteInfoEntry.COLUMN_COURSE_ID);

        DataManager dm=getInstance();
        dm.mNotes.clear();
        while(cursor.moveToNext()){
            String noteTitle=cursor.getString(noteTitlePos);
            String noteText=cursor.getString(noteTextPos);
            String noteCourseId=cursor.getString(noteCourseIdPos);
            int noteId=cursor.getInt(cursor.getColumnIndex(NoteInfoEntry._ID));

            NoteInfo noteInfo=new NoteInfo(noteText,noteTitle,noteCourseId,noteId);
            dm.mNotes.add(noteInfo);
        }
        cursor.close();
    }

    private static void loadCoursesFromDatabase(Cursor cursor) {
        int courseIdPos=cursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_ID);
        int courseTitlePos=cursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_TITLE);


        DataManager dm=getInstance();
        dm.mCourses.clear();
        while(cursor.moveToNext()){
            String courseId=cursor.getString(courseIdPos);
            String courseTitle=cursor.getString(courseTitlePos);
            CourseInfo courseInfo=new CourseInfo(courseId,courseTitle);

            dm.mCourses.add(courseInfo);
        }
        cursor.close();
    }
}