package ch.pantherinblack.advancedbasicsensors.listener;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public abstract class SensorEventListenerAdaption implements SensorEventListener {
    float[] latestValue;
    int sensorType;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // TODO
    }
}
