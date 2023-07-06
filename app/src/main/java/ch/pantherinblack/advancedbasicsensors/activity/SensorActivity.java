package ch.pantherinblack.advancedbasicsensors.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ch.pantherinblack.advancedbasicsensors.R;

public class SensorActivity extends AppCompatActivity {
    private  int sensorType = Integer.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void showSensor() {
        // TODO
    }
}