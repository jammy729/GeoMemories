package com.example.geomemories;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class List extends AppCompatActivity {

    public static final String DEFAULT = "not available";
    TextView usernameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        usernameTextView = findViewById(R.id.username);

        SharedPreferences sp = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String username = sp.getString("username", DEFAULT);
        usernameTextView.setText("Hello " + username);
    }

    public void tempActivity(View view) {
        Intent intent = new Intent (this, TempActivity.class);
        startActivity(intent);
    }

    public void cameraLog(View view) {
        Intent intent = new Intent (this, CameraImplicit.class);
        startActivity(intent);
    }
}
