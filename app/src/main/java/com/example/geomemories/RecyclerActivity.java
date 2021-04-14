package com.example.geomemories;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.sql.RowSet;

public class RecyclerActivity extends Activity implements AdapterView.OnItemClickListener {

    // variables
    RecyclerView myRecycler;
    PostDatabase db;
    PostAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        myRecycler = (RecyclerView) findViewById(R.id.recycler);

        Intent i = getIntent();
        String type = i.getStringExtra("Description");

        db = new PostDatabase(this);

        Cursor cursor = db.getData(type);

        int index1 = cursor.getColumnIndex(Constants.LOC_NAME);
        int index2 = cursor.getColumnIndex(Constants.POST_TEXT);

        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String locName = cursor.getString(index1);
            String postText = cursor.getString(index2);

            // store data as string list
            String s = locName + "," + postText;
            mArrayList.add(s);
            cursor.moveToNext();
        }
        myAdapter = new PostAdapter(mArrayList);
        myRecycler.setAdapter(myAdapter);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;
        TextView locNameTextView = (TextView) view.findViewById(R.id.descriptionView);
        TextView postTextView = (TextView) view.findViewById(R.id.postView);
        Toast.makeText(this, "row " + (1+position) + ":  " + locNameTextView + " " + postTextView, Toast.LENGTH_LONG).show(); // debugging
    }
}
