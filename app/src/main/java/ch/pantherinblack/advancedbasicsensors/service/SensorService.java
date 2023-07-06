package ch.pantherinblack.advancedbasicsensors.service;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

import ch.pantherinblack.advancedbasicsensors.listener.SensorEventListenerAdaption;

public class SensorService extends ServiceManager {
    List<SensorEventListenerAdaption> selaList;
    List<Integer> accelerometer = new ArrayList<>();
    List<Integer> rotation = new ArrayList<>();

    public SensorService() {
        accelerometer.add(Sensor.TYPE_ACCELEROMETER);
        accelerometer.add(Sensor.TYPE_GRAVITY);

        rotation.add(Sensor.TYPE_HEADING);
        rotation.add(Sensor.TYPE_GAME_ROTATION_VECTOR);
        rotation.add(Sensor.TYPE_ROTATION_VECTOR);
        rotation.add(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
    }

    public void addSensorEventListenerAdaption(SensorEventListenerAdaption sel, int sensorType) {
        if (!selaList.contains(sel)) {
            sel.setSensorType(sensorType);
            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensorManager.registerListener(sel,
                    sensorManager.getDefaultSensor(sensorType),
                    SensorManager.SENSOR_DELAY_NORMAL);
            selaList.add(sel);
        } else {
            throw new RuntimeException("SensorEventListenerAdaption is already in use.");
        }
    }

    public List<Sensor> getAllSensors() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        return sensorManager.getSensorList(Sensor.TYPE_ALL);
    }

    public float[] getLatestData(int sensorType) {
        for (SensorEventListenerAdaption sela : selaList) {
            if (sela.getSensorType() == sensorType)
                return sela.getLatestValue();
        }
        return null;
    }

    public String[] getStringValues(Sensor sensor, float[] data) {


        List<String> strings = new ArrayList<>();
        if (accelerometer.contains(sensor.getType())) {
            strings.add("x: " + data[0] + "m/s");
            strings.add("y: " + data[1] + "m/s");
            strings.add("z: " + data[2] + "m/s");
        } else if (rotation.contains(sensor.getType())) {
            strings.add("x-rot: " + data[0] + "°");
            strings.add("y-rot: " + data[1] + "°");
            strings.add("z-rot: " + data[2] + "°");

        } else {
            switch (sensor.getType()) {
                case Sensor.TYPE_GYROSCOPE:
                    strings.add("x-rot: " + data[0] + "°/s");
                    strings.add("y-rot: " + data[1] + "°/s");
                    strings.add("z-rot: " + data[2] + "°/s");
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    strings.add("Temp: " + data[0] + "°C");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    strings.add("x: " + data[0] + "uT, +/- "+ data[4] + "uT");
                    strings.add("y: " + data[1] + "uT, +/- "+ data[5] + "uT");
                    strings.add("z: " + data[2] + "uT, +/- "+ data[6] + "uT");
                    break;
                case Sensor.TYPE_HEART_RATE:
                    strings.add("Rate: " + data[0] + "bpm");
                    break;
                case Sensor.TYPE_LIGHT:
                    strings.add("Intensity: " + data[0] + "lux");
                    break;
                case Sensor.TYPE_PRESSURE:
                    strings.add("Pressure: " + data[0] + "hPa");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    strings.add("Distance: " + data[0] + "cm");
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    strings.add("Humidity: " + data[0] + "%");
                    break;
                case Sensor.TYPE_STEP_COUNTER:
                    strings.add("Steps: " + Math.round(data[0]) + " since starting the App");
                    break;
                case Sensor.TYPE_HINGE_ANGLE:
                    strings.add("Turn: "+ data[0] + "°");
                    break;
            }
        }

        return (String[]) strings.toArray(new String[0]);
    }

}