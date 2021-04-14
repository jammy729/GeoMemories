package com.example.geomemories;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;

public class PostDatabase {

    // variables
    private SQLiteDatabase db;
    private Context context;
    private final PostHelper helper;


    public PostDatabase(Context c) {
        context = c;
        helper = new PostHelper(context); // helper class initiates database
    }

    // use same column name to add the value
    public long insertData(String locName, String postText) {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.LOC_NAME, locName);
        contentValues.put(Constants.POST_TEXT, postText);

        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    public Cursor getData(String userTypeInput) {

        SQLiteDatabase db = helper.getWritableDatabase();
        String selection = null;
        if (userTypeInput != null) {
            selection = Constants.POST_TEXT + "='" + userTypeInput + "'";  // look for user input
        }

        String[] columns = {Constants.UID, Constants.LOC_NAME, Constants.POST_TEXT};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null, null, null, null, null); // null is selecting all data with no constraints
        return cursor;
    }

    public String getSelectedData(String type) {

        // select post from database
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.LOC_NAME, Constants.POST_TEXT};

        String selection = Constants.POST_TEXT + "='" + type + "'";
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.LOC_NAME);
            int index2 = cursor.getColumnIndex(Constants.POST_TEXT);

            String locName = cursor.getString(index1);
            String postText = cursor.getString(index2);

            buffer.append(locName + " " + postText);
        }
        return buffer.toString();
    }



    public int deleteRow() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {"Description"};
        int count = db.delete(Constants.TABLE_NAME, Constants.LOC_NAME + "=?", whereArgs);
        return count;
    }




}

