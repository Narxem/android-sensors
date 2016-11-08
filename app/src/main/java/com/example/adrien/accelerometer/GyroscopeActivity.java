package com.example.adrien.accelerometer;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GyroscopeActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    private ImageView image;
    private TextView gyroFound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        image = (ImageView) findViewById(R.id.canvas);
        gyroFound = (TextView) findViewById(R.id.gyroFound);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // TODO Récupérer le capteur d'orientation, le tester et l'enregistrer.
        // Pour récupérer le capteur d'orientation, utilisez
        // TYPE_ORIENTATION à la place de TYPE_ACCELEROMETER

    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO Désenregistrer le listener
    }

    public void toAccelerometer(View view) {
        finish();
    }


    // TODO Créer votre Listener.


    /**
     * Pivote le creeper d'un certain angle par rapport à sa position de départ
     * @param angle La valeur de l'angle en degrés, entre 0 et 360
     */
    public void rotateCreeper(float angle) {

        image.setPivotX(image.getWidth() / 2);
        image.setPivotY(image.getHeight() / 2);
        image.setRotation(angle);
    }
}
