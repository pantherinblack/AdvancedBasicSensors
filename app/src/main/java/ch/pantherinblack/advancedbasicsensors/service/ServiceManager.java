package ch.pantherinblack.advancedbasicsensors.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ServiceManager extends Service {

    public static ServiceManager createService() {
        //TODO
        return null;
    }

    public static void stopService() {
        //TODO
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
