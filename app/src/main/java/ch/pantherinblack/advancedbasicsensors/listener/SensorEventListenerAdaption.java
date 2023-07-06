package ch.pantherinblack.advancedbasicsensors.listener;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public abstract class SensorEventListenerAdaption implements SensorEventListener {
    float[] latestValue;
    int sensorType;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        setLatestValue(sensorEvent.values);
    }

    public float[] getLatestValue() {
        return latestValue;
    }

    public void setLatestValue(float[] latestValue) {
        this.latestValue = latestValue;
    }

    public int getSensorType() {
        return sensorType;
    }

    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }
}
