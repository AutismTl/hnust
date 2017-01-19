package com.example.tl.hnust;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by dyk on 2016/3/22.
 */
public class MyOrientationListener implements SensorEventListener {

    private static final String TAG = "BaiduMap";
    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor defaultSensor;
    private float lastX;
    private OrientationSensorListener mOrientationSensorListener;

    public MyOrientationListener(Context context) {
        this.mContext = context;
    }

    public void start() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(this, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mSensorManager.unregisterListener(this, defaultSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG,event.values[0]+"  "+event.values[1]+"  "+event.values[2]);
        if (mOrientationSensorListener != null && (Math.abs(event.values[0] - lastX)) > 1){
            mOrientationSensorListener.getOrientation(event.values[0]);
            lastX = event.values[0];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface OrientationSensorListener{
        void getOrientation(float x);
    }

    public void setOrientationListener(OrientationSensorListener orientationSensorListener){
        this.mOrientationSensorListener = orientationSensorListener;
    }
}
