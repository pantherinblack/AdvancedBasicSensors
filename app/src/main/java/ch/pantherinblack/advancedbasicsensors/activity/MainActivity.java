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

import java.util.ArrayList;
import java.util.List;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.fragment.SensorListItemFragment;
import ch.pantherinblack.advancedbasicsensors.service.SensorService;
import ch.pantherinblack.advancedbasicsensors.service.ServiceManager;

public class MainActivity extends AppCompatActivity {
    List<SensorListItemFragment> fragmentList = new ArrayList<>();

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

            SensorListItemFragment slim = SensorListItemFragment.newInstance(sensor, this);
            showSensor(sensor, slim);
            sensorService.addSensorEventListener(slim.getEventListener(sensorService), sensor.getType());
        }
    }

    public void showSensor(Sensor sensor, SensorListItemFragment slim) {

        FragmentManager fragMan = getSupportFragmentManager();
        fragMan.beginTransaction()
                .add(findViewById(R.id.scrollList).getId(), slim, sensor.getName())
                .commit();
        fragmentList.add(slim);
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
