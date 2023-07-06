package ch.pantherinblack.advancedbasicsensors.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.IBinder;

import java.util.List;

import ch.pantherinblack.advancedbasicsensors.listener.SensorEventListenerAdaption;

public class SensorService extends ServiceManager {
    List<SensorEventListenerAdaption> selaList;

    public void addSensorEventListenerAdaption(SensorEventListenerAdaption sel, int sensorType) {
        // TODO
    }

    public List<Sensor> getAllSensors() {
        // TODO
        return null;
    }

    public float getLatestData(int sensorType) {
        // TODO
        return 0;
    }

}