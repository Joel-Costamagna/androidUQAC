package com.example.projet.projetandroid.boussole;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * affiche une boussole en utilisant les différents capteurs dispo.
 * afiche également la localisation GPS avec l'altitude.
 * <p>
 * Attention : ce genre de capteurs fonctionne trés mal en intérieur, il faut se déplacer à 'exterieur pour tester.
 *
 * @author joelcostamagna
 */
class Boussole implements SensorEventListener {
    /**
     * pour les log
     */
    private static final String TAG = "boussole";
    /**
     * gère les appels aux différents capteurs.
     */
    private final SensorManager sensorManager;
    /**
     * geogrpahic north (acceléromètre)
     */
    private final Sensor        gsensor;
    /**
     * magnetic north (magnétomètre)
     */
    private final Sensor        msensor;
    /**
     * capteur logiciel hybride
     */
    private final Sensor        test_sensor;

    /**
     * Stocke les résultats du giroscope et acceleromètre.
     */
    private final float[] mGravity     = new float[3];
    /**
     * Stocke les résultats du magnétomètre.
     */
    private final float[] mGeomagnetic = new float[3];

    /**
     * Vue pour afficher les résultats.
     *
     * @see BoussoleActivity dans le constructeur;
     */
    ImageView dialView;
    /**
     * Vue pour afficher les résultats.
     *
     * @see BoussoleActivity dans le constructeur;
     */
    TextView  textview_azimuth;

    private float azimuth;
    private float currentAzimuth;

    /**
     * constructeur
     * <p>
     * Initialise les capteurs.
     *
     * @param context l'activité qui appele
     */
    @RequiresApi(api = VERSION_CODES.KITKAT) Boussole(final Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        test_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    /**
     * Démarre l'écoute des capteurs.
     */
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

    /**
     * Arrête l'écoute des capteurs.
     */
    void stop() {
        sensorManager.unregisterListener(this);
    }

    /**
     * fait tourner le cadran pour afficher l'orientation magnétique.
     * affiche également l'azimut pointé en degré.
     */
    private void adjustArrow() {
        //TODO pourquoi la je dois inverser l'angle ?
        final int    azimuth_degree = Math.round(azimuth);

        String az_orientation;
        if (azimuth_degree < 45) az_orientation = "N";
        else if (azimuth_degree < 90) az_orientation = "NE";
        else if (azimuth_degree < 135) az_orientation = "E";
        else if (azimuth_degree < 180) az_orientation = "SE";
        else if (azimuth_degree < 225) az_orientation = "S";
        else if (azimuth_degree < 270) az_orientation = "SW";
        else if (azimuth_degree < 315) az_orientation = "W";
        else az_orientation = "NW";

        final String azimuth_str = String.format(Locale.CANADA_FRENCH, "%d° %s", azimuth_degree, az_orientation);
        textview_azimuth.setText(azimuth_str);
        if (dialView == null) {
            Log.i(TAG, "arrow view is not set");
            return;
        }

        /*
         *tourne l'écran de azimuth degré, par rapport au centre du cadran.
         * pivotXValue et PivotYvalue pointent vers le centre de l'image.
         */
        final Animation an = new RotateAnimation(-currentAzimuth,
                                                 -azimuth,
                                                 Animation.RELATIVE_TO_SELF,
                                                 0.5f,
                                                 Animation.RELATIVE_TO_SELF,
                                                 0.5f);
        currentAzimuth = azimuth;


        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true); // on revient pas au point de départ à la fin de l'animation.

        dialView.startAnimation(an); //on fait bouger le cadran
    }

    @Override public void onSensorChanged(final SensorEvent event) {
        final float[] orientation = new float[3];
        if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {

            final float[] mMatrixR = new float[9];

            SensorManager.getRotationMatrixFromVector(mMatrixR, event.values);
            SensorManager.getOrientation(mMatrixR, orientation);
        }

        /*
         * si on a pas accès au capteur logiciel, on traite manuellement les données.
         * cf https://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-accel
         * Note: You can use many different techniques to filter sensor data.
         * The code sample above uses a simple filter constant (alpha) to create a low-pass filter.
         * This filter constant is derived from a time constant (t), which is a rough representation of the latency that the filter adds
         * to the sensor events,
         * and the sensor's event delivery rate (dt).
         */
        final float alpha = 0.97f;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity[0] = (alpha * mGravity[0]) + ((1 - alpha) * event.values[0]);
            mGravity[1] = (alpha * mGravity[1]) + ((1 - alpha) * event.values[1]);
            mGravity[2] = (alpha * mGravity[2]) + ((1 - alpha) * event.values[2]);
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic[0] = (alpha * mGeomagnetic[0]) + ((1 - alpha) * event.values[0]);
            mGeomagnetic[1] = (alpha * mGeomagnetic[1]) + ((1 - alpha) * event.values[1]);
            mGeomagnetic[2] = (alpha * mGeomagnetic[2]) + ((1 - alpha) * event.values[2]);
        }
        final float[] R = new float[9];
        final float[] I = new float[9];
        if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {
            SensorManager.getOrientation(R, orientation);
        }


        azimuth = (float) Math.toDegrees(orientation[0]);
        azimuth = (azimuth + 360) % 360;
        adjustArrow();
    }

    @Override public void onAccuracyChanged(final Sensor sensor, final int accuracy) {/*do nothing */ }
}