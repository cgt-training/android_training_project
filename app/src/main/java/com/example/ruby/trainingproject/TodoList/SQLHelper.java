package com.example.ruby.trainingproject.TodoList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ruby on 25/6/18.
 */

public class SQLHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "ToDo List";
    private static final int DB_VER = 1;
    public static final String DB_TABLE = "Task";
    public static final String DB_COL = "TaskName";


    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);"
                ,DB_TABLE,DB_COL);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXIST %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COL,task);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Vidit","Working Working");
        db.delete(DB_TABLE,DB_COL+" = ?",new String[]{task});
        Log.d("Vidit","After Working Working");
        db.close();
    }

    public ArrayList<String> getTaskList(){
            ArrayList<String> taskList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COL},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COL);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }

}
