package ch.pantherinblack.advancedbasicsensors.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.model.weather.Weather;
import ch.pantherinblack.advancedbasicsensors.service.GPSService;
import ch.pantherinblack.advancedbasicsensors.service.SensorService;
import ch.pantherinblack.advancedbasicsensors.service.ServiceManager;

public class SensorActivity extends AppCompatActivity {
    private  int sensorType = Integer.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Bundle extras = getIntent().getExtras();

        sensorType = (int) extras.get("type");
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.settingsButton).setOnClickListener(view -> {
            Intent intent = new Intent(SensorActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        Intent intent = new Intent(SensorActivity.this, SensorService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }
    public void showSensor() {
        sensorService.addSensorEventListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                ((TextView)findViewById(R.id.sensorActivityName)).setText(sensorEvent.sensor.getName());
                Sensor sensor = sensorEvent.sensor;
                String name = "Name: " + sensor.getName();
                String type = "Type: " + sensor.getStringType();
                String vendor = "Vendor: " + sensor.getVendor();
                String resolution = "Resolution: " + sensor.getResolution();
                String range = "Range: " + sensor.getMaximumRange();
                String[] values = sensorService.getStringValues(sensor, sensorEvent.values);

                List<String> allValues = new ArrayList<>();
                allValues.add(name);
                allValues.add(type);
                allValues.add(vendor);
                allValues.add(resolution);
                allValues.add(range);
                allValues.add("");
                allValues.addAll(Arrays.asList(values));

                updateValues(allValues.toArray(new String[0]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {}
        }, sensorType);
    }


    public void updateValues(String[] values) {
        LinearLayout linearLayout = findViewById(R.id.sensorActivityList);

        for (int i = 0; i < values.length; i++) {

            if (linearLayout.getChildCount() <= i) {
                TextView tv = new TextView(this);

                tv.setGravity(Gravity.CENTER);
                tv.setText(values[i]);
                linearLayout.addView(tv);
            } else {
                ((TextView) linearLayout.getChildAt(i)).setText(values[i]);
            }
        }
        linearLayout.invalidate();
    }

    public void showWeather(GPSService gpsService) {
        gpsService.addLocationListener(new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                new Thread(() -> {
                    gpsService.getWeather(
                            gpsService.getPostalCode(
                                    location.getLongitude(),
                                    location.getLatitude()),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Weather weather = new Weather();

                                    try {
                                        weather.fillFromJSON(new JSONObject(response));
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }

                                    String[] values = {"Location: " + gpsService.getCityName(
                                            location.getLongitude(), location.getLatitude()),
                                            "Temperature: " + weather.getCurrentWeather().getTemperature()};
                                    updateWeather(values);
                                }
                            });


                }).start();

            }
        });
    }

    public void updateWeather(String[] values) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout linearLayout = findViewById(R.id.weatherActivityList);

                for (int i = 0; i < values.length; i++) {

                    if (linearLayout.getChildCount() <= i) {
                        TextView tv = new TextView(getApplicationContext());

                        tv.setGravity(Gravity.CENTER);
                        tv.setText(values[i]);
                        linearLayout.addView(tv);
                    } else {
                        ((TextView) linearLayout.getChildAt(i)).setText(values[i]);
                    }
                }
                linearLayout.invalidate();
            }
        });
    }




    SensorService sensorService;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServiceManager.ServiceManagerBinder binder = (ServiceManager.ServiceManagerBinder) service;
            sensorService = (SensorService) binder.getService();

            Intent intent = new Intent(SensorActivity.this, GPSService.class);
            bindService(intent,connection2, Context.BIND_AUTO_CREATE);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {}
    };

    GPSService gpsService;

    ServiceConnection connection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServiceManager.ServiceManagerBinder binder = (ServiceManager.ServiceManagerBinder) service;
            gpsService = (GPSService) binder.getService();

            showSensor();
            if (sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE)
                showWeather(gpsService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {}
    };
}