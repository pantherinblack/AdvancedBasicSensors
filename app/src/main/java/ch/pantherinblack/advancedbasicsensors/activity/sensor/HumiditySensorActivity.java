package ch.pantherinblack.advancedbasicsensors.activity.sensor;

import android.os.Bundle;

import ch.pantherinblack.advancedbasicsensors.R;

public class HumiditySensorActivity extends SensorActivity {

    public HumiditySensorActivity(int sensorType) {
        super(sensorType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_sensor);
        //TODO
    }
}