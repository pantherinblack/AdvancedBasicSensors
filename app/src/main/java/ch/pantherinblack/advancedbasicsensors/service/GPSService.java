package ch.pantherinblack.advancedbasicsensors.service;

import android.app.Service;
import android.content.Intent;
import android.location.Geocoder;
import android.os.IBinder;

import java.io.IOException;

public class GPSService extends ServiceManager {
    public String getPostalCode(float longitude, float latitude) {
        try {
            return new Geocoder(this).
                    getFromLocation(longitude, latitude, 1).get(0)
                    .getPostalCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}