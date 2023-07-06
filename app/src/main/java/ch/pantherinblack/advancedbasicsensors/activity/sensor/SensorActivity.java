package ch.pantherinblack.advancedbasicsensors.activity.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.service.SensorService;

public class SensorActivity extends AppCompatActivity {
    private  int sensorType = Integer.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
    }
    public void showSensor() {
        // TODO
    }
}