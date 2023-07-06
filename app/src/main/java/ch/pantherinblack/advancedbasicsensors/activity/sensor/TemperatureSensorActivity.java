package ch.pantherinblack.advancedbasicsensors.activity.sensor;

import android.os.Bundle;

import ch.pantherinblack.advancedbasicsensors.R;

public class TemperatureSensorActivity extends SensorActivity {

    public TemperatureSensorActivity(int sensorType) {
        super(sensorType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_sensor);
        //TODO
    }
}