package ch.pantherinblack.advancedbasicsensors.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ServiceManager extends Service {
    private final IBinder binder = new ServiceManagerBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class ServiceManagerBinder extends Binder {
        public ServiceManager getService() {
            return ServiceManager.this;
        }
    }
}
