package ch.pantherinblack.advancedbasicsensors.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import ch.pantherinblack.advancedbasicsensors.model.weather.Weather;

public class GPSService extends ServiceManager {
    public void addLocationListener(LocationListener locationListener) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Generated
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Could not grant location permission");
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
    }

    public String getPostalCode(float longitude, float latitude) {
        try {
            return new Geocoder(this).
                    getFromLocation(longitude, latitude, 1).get(0)
                    .getPostalCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Weather getWeather(String postalCode) {
        Weather weather = null;
        Thread thread = new Thread(() -> {
            try {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("https://httpbin.org/post");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);


                    String text = "";
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        text = response.toString();
                    }

                    // TODO create weather object

                    notify();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    assert urlConnection != null;
                    urlConnection.disconnect();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return weather;
    }
}