package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;


public class CCameraView extends SurfaceView implements SurfaceHolder.Callback{

    private  Camera mCamera;
    private SurfaceHolder mHolder;

    private TextView makeText;

    /**
     * permet l'affichage de la camera
     * @param context
     * @param camera
     */

    public CCameraView(Context context, Camera camera){
        super(context);

        mCamera=camera;
        mCamera.setDisplayOrientation(90);
        //get the holder and set this class as the callback, so we can get camera data here
        mHolder=getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);


        }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        try{
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        }catch(IOException e){
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder,int i,int i2,int i3){
        //before changing the application orientation, you need to stop the preview, rotate and then start it again
        if(mHolder.getSurface()==null)//check if the surface is ready to receive camera data
            return;

        try{
            mCamera.stopPreview();
        }catch(Exception e){
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try{
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }catch(IOException e){
            Log.d("ERROR","Camera error on surfaceChanged "+e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder){
        //our app has only one screen, so we'll destroy the camera in the surface
        //if you are unsing with more screens, please move this code your activity
        mCamera.stopPreview();
        mCamera.release();
    }
}