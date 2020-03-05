package com.example.sqliteguide;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.sqliteguide.NoteKeeperDatabaseContract.*;

class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.MyNotesViewHolder>{
Cursor mCursor;
Context myContext;
    private int courseIdPos;
    private int mIdPOs;
    private int mCourseTitle;

    public MyNotesAdapter(Cursor cursor, Context c) {
        this.mCursor = cursor;
        this.myContext = c;

        populateColumnsPosition();
    }

    public void populateColumnsPosition(){
        if(mCursor==null){
            return;
        }
        courseIdPos = mCursor.getColumnIndex(NoteInfoEntry.COLUMN_COURSE_ID);
        mIdPOs = mCursor.getColumnIndex(NoteInfoEntry._ID);
        mCourseTitle = mCursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TITLE);
    }

    public void changeCursor(Cursor cursor){
        if(mCursor!=null){
            mCursor.close();
        }

        mCursor=cursor;
        populateColumnsPosition();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_layout,parent,false);
        View av=LayoutInflater.from(parent.getContext()).inflate(R.layout.another_layout,parent,false);
            if(viewType==1){
                return new MyNotesViewHolder(v);
            }else{
                return new MyNotesViewHolder(av);
            }


    }

    @Override
    public void onBindViewHolder(@NonNull MyNotesViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String course=mCursor.getString(courseIdPos);
        String title=mCursor.getString(mCourseTitle);
        int id=mCursor.getInt(mIdPOs);


        if(getItemViewType(position)==1){
        holder.noteTitle.setText(title);
        holder.noteDescription.setText(course);
        }
    }

    @Override
    public int getItemCount() {

      if(mCursor==null){
            return  0;
        }else{
          return  mCursor.getCount();
      }
    }

    @Override
    public int getItemViewType(int position) {
        if(mCursor==null){
            return 1;
        }

       mCursor.moveToPosition(position);
       String title=mCursor.getString(mCourseTitle);
        if(title.equals("ampther tirle")){
            return  3;
        }else{
            return  1;
        }
    }

    class MyNotesViewHolder extends  RecyclerView.ViewHolder{
        TextView noteTitle;
        TextView noteDescription;
        public MyNotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle=itemView.findViewById(R.id.note_item_title);
            noteDescription=itemView.findViewById(R.id.note_item_description);
        }
    }
//    public void loadMore(String title,String description){
//        mNotes.add(new NoteInfo(description,title,"course",1));
//       notifyDataSetChanged();
//    }
//    public void hide(){
//        mNotes.remove(mNotes.size()-1);
//        mNotes.add(new NoteInfo("AM TEXT","Am Title","Am Course",1));
//        notifyDataSetChanged();
//    }
}
