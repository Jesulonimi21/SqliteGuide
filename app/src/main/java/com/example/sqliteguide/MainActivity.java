package com.example.sqliteguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sqliteguide.NoteKeeperDatabaseContract.NoteInfoEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
NoteKeeperOpenHelper mDbOpenHelper;
List<NoteInfo> mNotes;
RecyclerView recyclerView;
    private MyNotesAdapter myNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.notes_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mNotes=new ArrayList<>();
        mNotes.add(new NoteInfo("AM TEXT","Am Title","Am Course",1));
        mNotes.add(new NoteInfo("AM TEXT","Am Title","Am Course",1));
        mNotes.add(new NoteInfo("AM TEXT","Am Title","Am Course",1));
        mNotes.add(new NoteInfo("AM TEXT","Am Title","Am Course",1));
        myNotesAdapter = new MyNotesAdapter(null,this);
        mDbOpenHelper=new NoteKeeperOpenHelper(this);
        recyclerView.setAdapter(myNotesAdapter);


        initializeDatabaseContent();

    }

    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    public void initializeDatabaseContent(){
        DataManager dm=DataManager.getInstance();
        dm.loadAllInfoFromDatabase(mDbOpenHelper);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadNotes();
    }

    public void loadNotes(){
        SQLiteDatabase db=mDbOpenHelper.getReadableDatabase();

        //Table_name1 JOIN Table_Name2 ON Tabke_name1.column_name1=Table_name2.column_name1
        String[] noteColumns = {NoteInfoEntry.COLUMN_COURSE_ID, NoteInfoEntry.COLUMN_NOTE_TEXT, NoteInfoEntry.COLUMN_NOTE_TITLE, NoteInfoEntry._ID};
        String notesOrderBy= NoteInfoEntry.COLUMN_COURSE_ID+","+ NoteInfoEntry.COLUMN_NOTE_TITLE;
        Log.d("lonimi", NoteInfoEntry.SQL_CREATE_TABLE);
        Cursor notesCursor = db.query(NoteInfoEntry.TABLE_NAME, noteColumns, null, null, null, null, notesOrderBy);
            myNotesAdapter.changeCursor(notesCursor);

    }

//    public void buttonClicked(View v){
//        myNotesAdapter.loadMore("ampther tirle","anptrrr vvir");
//    }
//
//    public  void hideLastItem(View v){
//        myNotesAdapter.hide();
//    }
}
