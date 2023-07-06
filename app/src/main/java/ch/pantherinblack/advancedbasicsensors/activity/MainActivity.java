package ch.pantherinblack.advancedbasicsensors.activity;

import android.hardware.Sensor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.List;
import java.util.UUID;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.fragment.SensorListItemFragment;
import ch.pantherinblack.advancedbasicsensors.service.SensorService;
import ch.pantherinblack.advancedbasicsensors.service.ServiceHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSensors();
    }

    public void loadSensors() {
        SensorService sensorService = ServiceHandler.getInstance().createService(SensorService.class, this);
        List<Sensor> sensors = sensorService.getAllSensors();

        for (Sensor sensor : sensors) {
            float[] data = sensorService.getLatestData(sensor.getType());
            showSensor(sensor.getName(), sensorService.getStringValues(sensor, data));
        }
        ServiceHandler.getInstance().stopService(sensorService.getClass(), this);
    }

    public void showSensor(String name, String[] values) {
        SensorListItemFragment slim = SensorListItemFragment.newInstance(name, values);

        FragmentManager fragMan = getSupportFragmentManager();
        fragMan.beginTransaction()
                .add(findViewById(R.id.scroll).getId(), slim, UUID.randomUUID().toString())
                .commit();
    }


}