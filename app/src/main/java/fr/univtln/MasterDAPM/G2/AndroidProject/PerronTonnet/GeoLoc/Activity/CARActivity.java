package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Camera.CCameraView;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.sqrt;

/**
 * classe qui permet d'activer la caméra de l'appareil
 */

public class CARActivity extends AppCompatActivity implements SensorEventListener {

    private Camera mCamera2 = null;
    private CCameraView mCCameraView = null;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView makeText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        try{
            mCamera2 = Camera.open();
        } catch (Exception e){
        }

        if(mCamera2 != null) {
            mCCameraView = new CCameraView(this, mCamera2);
            FrameLayout camera_view = (FrameLayout)findViewById(R.id.camera_view);
            camera_view.addView(mCCameraView);
        }


        ImageButton imgClose = (ImageButton)findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        makeText = (TextView) findViewById(R.id.sensor);
        mCamera2.setDisplayOrientation(0);

    }





    // Create a constant to convert nanoseconds to seconds.
    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;

    /**
     * réagit aux changement de position de l'appareil
     * @param event
     */

    public void onSensorChanged(SensorEvent event) {
        // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            // Calculate the angular speed of the sample
            float omegaMagnitude = (float) sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            if (omegaMagnitude > 2) {
                axisX /= omegaMagnitude;
                axisY /= omegaMagnitude;
                axisZ /= omegaMagnitude;
            }

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
            float thetaOverTwo = omegaMagnitude * dT / 2.0f;
            float sinThetaOverTwo = (float) sin(thetaOverTwo);
            float cosThetaOverTwo = (float) cos(thetaOverTwo);
            deltaRotationVector[0] = sinThetaOverTwo * axisX;
            deltaRotationVector[1] = sinThetaOverTwo * axisY;
            deltaRotationVector[2] = sinThetaOverTwo * axisZ;
            deltaRotationVector[3] = cosThetaOverTwo;
        }
        timestamp = event.timestamp;
        float[] deltaRotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        // rotationCurrent = rotationCurrent * deltaRotationMatrix;

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mSensorManager.registerListener((SensorEventListener) mSensorManager, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
 //       mSensorManager.unregisterListener((SensorEventListener) mSensorManager, mSensor);
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        makeText.setText(String.valueOf(timestamp));
    }
}
