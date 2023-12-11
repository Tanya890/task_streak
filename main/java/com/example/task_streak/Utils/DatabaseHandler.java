package com.example.task_streak.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.task_streak.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    private static final String USER_TABLE = "user";
    private static final String USER_ID = "user_id";
    private static final String USER_PHONE_NUMBER = "phone_number";
    private static final String USER_PASSWORD = "password";
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_PHONE_NUMBER + " TEXT, "
            + USER_PASSWORD + " TEXT)";

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //drop the existing table
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        //create table again
        onCreate(db);
    }

    // User registration
    public void registerUser(String phoneNumber, String password) {
        try {
            openDatabase();

            ContentValues cv = new ContentValues();
            cv.put(USER_PHONE_NUMBER, phoneNumber);
            cv.put(USER_PASSWORD, password);

            Log.d("DatabaseHandler", "Registering user with phone number: " + phoneNumber);

            long result = db.insert(USER_TABLE, null, cv);

            if (result == -1) {
                // Insertion failed, handle the error (log, throw exception, etc.)
                Log.e("DatabaseHandler", "Failed to register user");
            } else {
                Log.d("DatabaseHandler", "User registered successfully");
            }
        } finally {
            closeDatabase();
        }
    }

    public boolean checkUserCredentials(String phoneNumber, String password) {
        openDatabase();
        String[] columns = {USER_ID};
        String selection = USER_PHONE_NUMBER + "=? AND " + USER_PASSWORD + "=?";
        String[] selectionArgs = {phoneNumber, password};

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);

        boolean userExists = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // User exists
                userExists = true;
            }
            cursor.close();
        }

        closeDatabase();
        // User does not exist or invalid credentials
        return userExists;
    }
    public void openDatabase(){
        Log.d("DatabaseHandler", "Opening database");
        db = this.getWritableDatabase();
    }

    public void closeDatabase(){
        if(db != null && db.isOpen()){
            db.close();
            Log.d("DatabaseHandler", "Closing database");
        }
    }
    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        Log.d("DatabaseHandler", "Inserting task: " + task.getTask());
        db.insert(TODO_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks(){
        Log.d("DatabaseHandler", "Getting all tasks");
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()){
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }while (cur.moveToNext());
                }
            }
        }
        finally {
            if(cur != null){
            db.endTransaction();
            cur.close();
            }
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
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }
}