package ch.pantherinblack.advancedbasicsensors.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.List;
import java.util.UUID;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.fragment.SensorListItemFragment;
import ch.pantherinblack.advancedbasicsensors.service.SensorService;
import ch.pantherinblack.advancedbasicsensors.service.ServiceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SensorService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    public synchronized void loadSensors() {
        SensorService sensorService = (SensorService) serviceManager;

        List<Sensor> sensors = sensorService.getAllSensors();

        for (Sensor sensor : sensors) {
            float[] data = sensorService.getLatestData(sensor.getType());
            showSensor(sensor.getName(), sensorService.getStringValues(sensor, data));
        }
        unbindService(connection);
    }

    public void showSensor(String name, String[] values) {
        SensorListItemFragment slim = SensorListItemFragment.newInstance(name, values);

        FragmentManager fragMan = getSupportFragmentManager();
        fragMan.beginTransaction()
                .add(findViewById(R.id.scroll).getId(), slim, UUID.randomUUID().toString())
                .commit();
    }

    ServiceManager serviceManager;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServiceManager.ServiceManagerBinder binder = (ServiceManager.ServiceManagerBinder) service;
            serviceManager = binder.getService();
            loadSensors();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {}
    };
}
