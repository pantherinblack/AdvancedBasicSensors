package ch.pantherinblack.advancedbasicsensors.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
    }

    public String getPostalCode(double longitude, double latitude) {
        try {
            return new Geocoder(this).
                    getFromLocation(latitude,longitude, 1).get(0)
                    .getPostalCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCityName(double longitude, double latitude) {
        try {
            return new Geocoder(this).
                    getFromLocation(latitude,longitude, 1).get(0)
                    .getAdminArea();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getWeather(String postalCode, Response.Listener<String> resList) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://app-prod-ws.meteoswiss-app.ch/v1/plzDetail?plz=" + postalCode + "00";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, resList, error -> {});
        queue.add(stringRequest);
    }
}