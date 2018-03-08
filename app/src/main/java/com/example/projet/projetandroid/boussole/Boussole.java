package com.example.projet.projetandroid.boussole;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;


class Boussole implements SensorEventListener {
    private static final String TAG = "boussole";
    private final SensorManager sensorManager;
    private final Sensor        gsensor;
    private final Sensor        msensor;
    private final Sensor        test_sensor;
    private final float[] mGravity     = new float[3];
    private final float[] mGeomagnetic = new float[3];
    //initialisés dans BoussoleActivity
    ImageView arrowView        = null;
    TextView  textview_azimuth = null;
    private float azimuth        = 0f;
    private float currentAzimuth = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    Boussole(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        test_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


    }

    void start() {
        if (gsensor != null) {
            sensorManager.registerListener(this, gsensor, SensorManager.SENSOR_DELAY_GAME);
        }
        if (msensor != null) {
            sensorManager.registerListener(this, msensor, SensorManager.SENSOR_DELAY_GAME);
        }
        if (test_sensor != null) {
            sensorManager.registerListener(this, test_sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    void stop() {
        sensorManager.unregisterListener(this);
    }

    private void adjustArrow() {
        //TODO pourquoi la je dois inverser l'angle ?
        String azimuth_str = String.format(Locale.CANADA_FRENCH, "%d°", Math.round(azimuth));
        textview_azimuth.setText(azimuth_str);
        if (arrowView == null) {
            Log.i(TAG, "arrow view is not set");
            return;
        }

        Animation an = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentAzimuth = azimuth;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);


        arrowView.startAnimation(an);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.97f;
        if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {

            float[] mMatrixR    = new float[9];
            float[] orientation = new float[3];
            SensorManager.getRotationMatrixFromVector(mMatrixR, event.values);
            SensorManager.getOrientation(mMatrixR, orientation);

            //on convertit en degrés.
            azimuth = (float) Math.toDegrees(orientation[0]);
            azimuth = (azimuth + 360) % 360;
            adjustArrow();

        } else {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //raw values
                //mGravity = event.values;

                //on supprime le bruit parasite du à la haute sensibilité des capteurs
                mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
                mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
                mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];

            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                // raw values
                //mGeomagnetic = event.values;

                //improve values with mathematic genius from stackOverflow
                mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha) * event.values[0];
                mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha) * event.values[1];
                mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha) * event.values[2];
            }

        }


        float R[] = new float[9];
        float I[] = new float[9];
        if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {
            float orientation[] = new float[3];
            SensorManager.getOrientation(R, orientation);
            azimuth = (float) Math.toDegrees(orientation[0]); // orientation
            azimuth = (azimuth + 360) % 360;

            adjustArrow();
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {/*do nothing */ }
}