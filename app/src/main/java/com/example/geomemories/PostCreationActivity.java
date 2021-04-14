package com.example.geomemories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



public class PostCreationActivity extends Activity implements SensorEventListener {

    // variables
    EditText locDescription, postContent, selectDest;
    PostDatabase db;
    ImageView imageView;

    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Boolean sensorExist;


    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_creation);

        locDescription = (EditText)findViewById(R.id.locD);
        postContent = (EditText)findViewById(R.id.postC);
        selectDest = (EditText)findViewById(R.id.locD);

        db = new PostDatabase(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        imageView = (ImageView)findViewById(R.id.imageView);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorExist = true;
        } else {
            sensorExist = false;
        }
        btnCamera.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        });

    }

    public void addPost (View view){

        // user entries
        String locName = locDescription.getText().toString();
        String post = postContent.getText().toString();

        // debugging to make sure it is added
        Toast.makeText(this, locName + " " + post, Toast.LENGTH_SHORT).show();

        // insert data into table
        long id = db.insertData(locName, post);

        // debugging by checking if data successfully added
        if (id < 0)
        {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }

        locDescription.setText("");
        postContent.setText("");
    }

    public void viewSearchResults(View view) {
        String userInputType = selectDest.getText().toString();
        Intent intent = new Intent(this, RecyclerActivity.class);
        intent.putExtra("Location", userInputType);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "User cancelled the capture", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //temperature value
        float temp = sensorEvent.values[0];
        int type = sensorEvent.sensor.getType();
        if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {

            //background change depending on sensor value
            if (temp < -15) {
//                tempFeel.setText(FREEZING);
                getWindow().getDecorView().setBackgroundColor(Color.parseColor("#91D7FF"));

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

    //register temp sensor
    @Override
    protected void onResume() {
        super.onResume();
        if (sensorExist) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    //unregister temp sensor
    protected void onPause() {
        super.onPause();
        if (sensorExist) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensorEvent, int accuracy) {

    }
}