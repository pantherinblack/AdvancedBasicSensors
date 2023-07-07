package ch.pantherinblack.advancedbasicsensors.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.fragment.SensorListItemFragment;
import ch.pantherinblack.advancedbasicsensors.service.GPSService;
import ch.pantherinblack.advancedbasicsensors.service.SensorService;
import ch.pantherinblack.advancedbasicsensors.service.ServiceManager;

public class MainActivity extends AppCompatActivity {
    List<SensorListItemFragment> fragmentList = new ArrayList<>();
    SensorService sensorService;
    GPSService gpsService;
    ServiceConnection connection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServiceManager.ServiceManagerBinder binder = (ServiceManager.ServiceManagerBinder) service;
            gpsService = (GPSService) binder.getService();

            loadSensors();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServiceManager.ServiceManagerBinder binder = (ServiceManager.ServiceManagerBinder) service;
            sensorService = (SensorService) binder.getService();

            Intent intent = new Intent(MainActivity.this, GPSService.class);
            bindService(intent, connection2, Context.BIND_AUTO_CREATE);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SensorService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        findViewById(R.id.settingsButton).setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent1);
        });
    }

    public synchronized void loadSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            SensorListItemFragment gpsSlim = SensorListItemFragment.newInstance(null, this);
            gpsService.addLocationListener(gpsSlim.getLocationListener(gpsService));
            showSensor(null, gpsSlim);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gpsSlim.update(new String[]{"No GPS Registered."});
                        }
                    });
                }
            });
            thread.start();


        } catch (RuntimeException ignored) {
        }

        for (Sensor sensor : sensors) {
            SensorListItemFragment slim = SensorListItemFragment.newInstance(sensor, this);
            showSensor(sensor, slim);
            sensorService.addSensorEventListener(slim.getEventListener(sensorService), sensor.getType());
        }
    }

    public void showSensor(Sensor sensor, SensorListItemFragment slim) {

        FragmentManager fragMan = getSupportFragmentManager();
        fragMan.beginTransaction()
                .add(findViewById(R.id.scrollList).getId(), slim)
                .commit();
        fragmentList.add(slim);
    }
}
