package ch.pantherinblack.advancedbasicsensors.activity.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SensorActivity extends AppCompatActivity {
    private  int sensorType = Integer.MAX_VALUE;
    public SensorActivity(int sensorType) {
        this.sensorType = sensorType;
    }

    public void showSensor() {
        // TODO
    }
}