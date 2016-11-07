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

    private SensorEventListener listener = new GyroscopeListener();


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
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (gyro == null) {
            gyroFound.setText("Aucun capteur d'oriantation");
        }
        else {
            gyroFound.setText("Capteur d'orientation trouvé");
            sensorManager.registerListener(listener, gyro, SensorManager.SENSOR_DELAY_GAME);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(listener);
    }

    public void toAccelerometer(View view) {
        finish();
    }


    protected class GyroscopeListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            rotateCreeper(-event.values[0]);
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Nothing to do here
        }



    }

    /**
     * Effectue une rotation sur le creeper
     * @param angle La valeur de l'angle en degrés, entre 0 et 360
     */
    public void rotateCreeper(float angle) {
/*        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),  matrix, true);
        image.setImageBitmap(resizedBitmap);*/
        image.setPivotX(image.getWidth() / 2);
        image.setPivotY(image.getHeight() / 2);
        image.setRotation(angle);
    }
}
