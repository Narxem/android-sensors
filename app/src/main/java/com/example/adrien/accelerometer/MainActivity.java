package com.example.adrien.accelerometer;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private double lastX;
    private double lastY;
    private double lastZ;
    private boolean initialized;

    private TextView xView;
    private TextView yView;
    private TextView zView;
    private TextView hello;
    private ImageView image;

    private SensorManager sensorManager;

    private SensorEventListener listener = new AccelerometerListener();

    private final double THRESHOLD = .5;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        xView = (TextView) findViewById(R.id.xlabel);
        yView = (TextView) findViewById(R.id.ylabel);
        zView = (TextView) findViewById(R.id.zlabel);
        hello = (TextView) findViewById(R.id.hello);
        image = (ImageView) findViewById(R.id.shake);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        initialized = false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Sensor acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acc == null)
            hello.setText("Vous n'avez pas d'accélèromètre");
        else {
            hello.setText("Valeurs de l'accélèromètre");
            sensorManager.registerListener(listener, acc, SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(listener);
    }


    protected void toGyro(View view) {
        Intent intent = new Intent(this, GyroscopeActivity.class);
        startActivity(intent);
    }


    protected class AccelerometerListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (initialized) {
                lastX = event.values[0];
                lastY = event.values[1];
                lastZ = event.values[2];

                initialized = true;
            }
            else {

                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];

                double dx = Math.abs(x - lastX);
                double dy = Math.abs(y - lastY);
                double dz = Math.abs(z - lastZ);

                dx = (dx > THRESHOLD) ? dx : 0.;
                dy = (dy > THRESHOLD) ? dy : 0.;
                dz = (dz > THRESHOLD) ? dz : 0.;


                xView.setText("X : " + String.valueOf(dx));
                yView.setText("Y : " + String.valueOf(dy));
                zView.setText("Z : " + String.valueOf(dz));

                lastX = x;
                lastY = y;
                lastZ = z;
                if (dx > dy) {
                    image.setVisibility(View.VISIBLE);
                    image.setImageResource(R.drawable.shaker_fig_1);
                } else if (dy > dx) {
                    image.setVisibility(View.VISIBLE);
                    image.setImageResource(R.drawable.shaker_fig_2);
                } else {
                    image.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Nothing to do here
        }
    }

}