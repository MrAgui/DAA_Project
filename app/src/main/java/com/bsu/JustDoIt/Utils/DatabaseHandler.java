package com.bsu.JustDoIt.Utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bsu.JustDoIt.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Declaring variables
    private static final int VERSION=1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";

    //Declaring query for the database
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE  + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop olderTables when executed
        db.execSQL("DROP TABLE IF EXISTS "+ TODO_TABLE);
        // creates tables again
        onCreate(db);
    }
    // Opens the db to write
    public void openDatabase(){
        db=this.getWritableDatabase();
    }

    //Functions for the Database

    public void insertTask(ToDoModel task){
        ContentValues cv =new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null,cv);
    }

    // @SuppressLint("Range")
    public List<ToDoModel> getAllTasks(){


        List<ToDoModel> taskList = new ArrayList<>();


        Cursor cur = null;
        db.beginTransaction();
        try{
            cur=db.query(TODO_TABLE, null, null, null, null, null, null, null );
            if(cur != null){
                if(cur.moveToFirst()){
                    do {
                        ToDoModel task = new ToDoModel();
                        // recheck repo from previous, changed the
                        //task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        //to cur.getColumnIndexOrThrow method
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTask(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                        taskList.add(task);
                    }while(cur.moveToNext());
                }
            }
        }
        finally {
        db.endTransaction();
        cur.close();
        }
    return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID +  "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID+"=?", new String[] {String.valueOf(id)});
    }



}
