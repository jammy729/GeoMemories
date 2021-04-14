package com.example.geomemories;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.widget.RelativeLayout;
import android.widget.TextView;


public class TempActivity extends Activity implements SensorEventListener {

    int[] color;
    private TextView tempValue, tempFeel;
    private SensorManager sensorManager;

    private Sensor tempSensor;
    private Boolean sensorExist;


    RelativeLayout layout;


    private final static String NOT_AVAILABLE = "Temperature sensor is not available";
    private final static String FREEZING = "FREEZING";
    private final static String VERY_COLD = "VERY COLD";
    private final static String COLD = "COLD";
    private final static String WARM = "WARM";
    private final static String HOT = "HOT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_activity);

        tempValue = (TextView) findViewById(R.id.sensorTemp);
        tempFeel = (TextView) findViewById(R.id.temp);
        layout = findViewById(R.id.changeView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //check if device have temperature sensor
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorExist = true;
        } else {
            tempValue.setText(NOT_AVAILABLE);
            sensorExist = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //temperature value
        float temp = sensorEvent.values[0];
        int type = sensorEvent.sensor.getType();
        if (type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            tempValue.setText(temp + "°C");

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
//                layout.setBackgroundColor(Color.parseColor("#91D7FF"));
//            } else if (temp > -15 && temp < 0) {
//                tempFeel.setText(VERY_COLD);
//                layout.setBackgroundColor(Color.parseColor("#A6DFFF"));
//            } else if (temp > 0 && temp < 15) {
//                tempFeel.setText(COLD);
//                layout.setBackgroundColor(Color.parseColor("#C6EEFF"));
//            } else if (temp > 15 && temp < 25) {
//                tempFeel.setText(WARM);
//                layout.setBackgroundColor(Color.parseColor("#EFFD5F"));
//            } else if (temp > 25) {
//                tempFeel.setText(HOT);
//                layout.setBackgroundColor(Color.parseColor("#FFA500"));
//            }

        }
    }

    public interface tempChangeListener extends SensorEventListener {
        SensorManager sensorManager = null;
        Sensor tempSensor =sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        void onSensorChanged(SensorEvent sensorEvent);
        void onResume();
        void onPause();
    }

    @Override
    public void onAccuracyChanged(Sensor sensorEvent, int i) {
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

}


