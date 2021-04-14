package com.example.geomemories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // variables

    // GoogleMap myMap;
    private EditText mapLocationEntry;
    private final static String DEBUG_TAG = "Start"; // debugging variable
    TextView nameTextView;
    private TempActivity.tempChangeListener temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // debugging
        Log.i(DEBUG_TAG, "in the MainActivity page");

        // this variable relates to information from locationNameEditText
        //mapLocationEntry = (EditText)findViewById(R.id.locationEditText);
        temp =  new TempActivity.tempChangeListener() {


            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //temperature value
                float temp = sensorEvent.values[0];
                int type = sensorEvent.sensor.getType();
                if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {

                    //background change depending on sensor value
                    if (temp < -15) {
                        Toast.makeText(MainActivity.this, "-15 MOTHERFUCKER", Toast.LENGTH_SHORT).show();
                        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#91D7FF"));
//                layout.setBackgroundColor(Color.parseColor("#91D7FF"));
                    } else if (temp > -15 && temp < 0) {
                        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#A6DFFF"));
                    } else if (temp > 0 && temp < 15) {
                        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#C6EEFF"));
                    } else if (temp > 15 && temp < 25) {
                        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#EFFD5F"));
                    } else if (temp > 25) {
                        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#FFA500"));
                    }

                }
            }

            @Override
            public void onResume() {
                SensorManager sensorManager = null;
                Sensor tempSensor = null;
                sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }

            @Override
            public void onPause() {
                SensorManager sensorManager = null;
                Sensor tempSensor = null;
                sensorManager.unregisterListener(this);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };
    }




    public void viewAllPosts(View view) {
        Intent i = new Intent (this, RecyclerActivity.class);
        startActivity(i);
    }


    public void createPost(View view) {
        Intent i = new Intent(this, PostCreationActivity.class);
        startActivity(i);
    }

    public void login(View view) {
        Intent i = new Intent (this, LoginActivity.class);
        startActivity(i);
    }

    public void utility(View view) {
        Intent i = new Intent (this, List.class);
        startActivity(i);
    }
}