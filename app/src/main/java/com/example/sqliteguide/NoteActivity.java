package com.example.sqliteguide;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import static com.example.sqliteguide.NoteKeeperDatabaseContract.*;

public class NoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    public static final int LOADER_NOTES = 1;
    private NoteKeeperOpenHelper mNoteKeeperOpenHelper;
    private Cursor noteCursor;
    private int courseIdPos;
    private int noteTextPos;
    private int noteTitlePos;
    private int mNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mNoteKeeperOpenHelper = new NoteKeeperOpenHelper(this);


       getSupportLoaderManager().initLoader(LOADER_NOTES,null,  this);


    }

    @Override
    protected void onDestroy() {
        mNoteKeeperOpenHelper.close();
        super.onDestroy();
    }

    public void loadNotesData(){
        SQLiteDatabase db=mNoteKeeperOpenHelper.getReadableDatabase();
        String courseId="android_intent";
        String titleStart="dynamic";

//        String selection= NoteInfoEntry.COLUMN_COURSE_ID +" = ? AND "
//                    + NoteInfoEntry.COLUMN_NOTE_TITLE+ " LIKE ?";
//        String[] selectionArgs={courseId,titleStart+"%"};
        String selection= NoteInfoEntry._ID + " = ? ";

        String[] selectionArgs={Integer.toString(1)};

        String[] noteColumns={
            NoteInfoEntry.COLUMN_NOTE_TITLE,
            NoteInfoEntry.COLUMN_NOTE_TEXT,
            NoteInfoEntry.COLUMN_COURSE_ID
        };

        noteCursor = db.query(NoteInfoEntry.TABLE_NAME,noteColumns,selection,selectionArgs,null,null,null);
        courseIdPos = noteCursor.getColumnIndex(NoteInfoEntry.COLUMN_COURSE_ID);
        noteTextPos = noteCursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TEXT);
        noteTitlePos = noteCursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TITLE);

        noteCursor.moveToNext();
        displayNotes();

    }

    public void displayNotes(){
        String courseId=noteCursor.getString(courseIdPos);
        String noteText=noteCursor.getString(noteTextPos);
        String noteTitle=noteCursor.getString(noteTitlePos);
    }



    private CursorLoader createNewCursorLoader() {
        return new CursorLoader(this){
            @Override
            public Cursor loadInBackground() {
                SQLiteDatabase db=mNoteKeeperOpenHelper.getReadableDatabase();
                String courseId="android_intent";
                String titleStart="dynamic";

//        String selection= NoteInfoEntry.COLUMN_COURSE_ID +" = ? AND "
//                    + NoteInfoEntry.COLUMN_NOTE_TITLE+ " LIKE ?";
//        String[] selectionArgs={courseId,titleStart+"%"};
                String selection= NoteInfoEntry._ID + " = ? ";

                String[] selectionArgs={Integer.toString(1)};

                String[] noteColumns={
                        NoteInfoEntry.COLUMN_NOTE_TITLE,
                        NoteInfoEntry.COLUMN_NOTE_TEXT,
                        NoteInfoEntry.COLUMN_COURSE_ID
                };

               return db.query(NoteInfoEntry.TABLE_NAME,noteColumns,selection,selectionArgs,null,
                       null,null);

            }
        };
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader cursorLoader=null;
        if(id== LOADER_NOTES){
            cursorLoader=createNewCursorLoader();
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    if(loader.getId()== LOADER_NOTES){
        loadFinishedNotes(data);
    }
    }

    private void loadFinishedNotes(Cursor data) {
        noteCursor=data;
        courseIdPos = noteCursor.getColumnIndex(NoteInfoEntry.COLUMN_COURSE_ID);
        noteTextPos = noteCursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TEXT);
        noteTitlePos = noteCursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TITLE);

        noteCursor.moveToNext();
        displayNotes();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            if(loader.getId()==LOADER_NOTES){
                if(noteCursor!=null){
                    noteCursor.close();
                }
            }
    }

    public void saveNoteToDatabase(int _id,String noteTitle,String noteText){
        String selection=NoteInfoEntry._ID +"=?";
        String selectionArgs[]={Integer.toString(_id)};

        ContentValues values=new ContentValues();
        values.put(NoteInfoEntry.COLUMN_NOTE_TITLE,noteTitle);
        values.put(NoteInfoEntry.COLUMN_NOTE_TEXT,noteText);

        SQLiteDatabase db=mNoteKeeperOpenHelper.getWritableDatabase();
        db.update(NoteInfoEntry.TABLE_NAME,values,selection,selectionArgs);

    }
    private void createNewNote(String noteTitle,String noteText, String CourseId){
        ContentValues values=new ContentValues();
        values.put(NoteInfoEntry.COLUMN_NOTE_TITLE,noteTitle);
        values.put(NoteInfoEntry.COLUMN_NOTE_TEXT,noteText);
        values.put(NoteInfoEntry.COLUMN_COURSE_ID,CourseId);

        SQLiteDatabase db=mNoteKeeperOpenHelper.getWritableDatabase();
        mNoteId = (int)  db.insert(NoteInfoEntry.TABLE_NAME,null,values);
    }

    private void deleteNoteFromDatabase(){
        String selection=NoteInfoEntry._ID+" = ?";
        String[] selectionArgs={Integer.toString(mNoteId)};

        SQLiteDatabase db=mNoteKeeperOpenHelper.getWritableDatabase();
        db.delete(NoteInfoEntry.TABLE_NAME,selection,selectionArgs);
    }
}
