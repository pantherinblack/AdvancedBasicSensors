package ch.pantherinblack.advancedbasicsensors.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceHandler {
    private static ServiceHandler serviceHandler;
    private volatile ServiceManager serviceManager;
    private HashMap<Class<?>, ServiceConnection> connections = new HashMap<>();
    private ServiceHandler() {}

    public static ServiceHandler getInstance() {
        if (serviceHandler == null)
            serviceHandler = new ServiceHandler();
        return serviceHandler;
    }
    public <T extends ServiceManager> T createService(Class<T> sm, AppCompatActivity self) {
        connections.put(sm, createServiceConnection());

        Intent intent = new Intent(self, sm);
        self.bindService(intent,connections.get(sm), Context.BIND_AUTO_CREATE);
        while (serviceManager == null) {

        }
        T service = (T) serviceManager;
        serviceManager = null;
        return (T) service;
    }

    public void stopService(Class<? extends ServiceManager> sm, AppCompatActivity self) {
        self.unbindService(connections.get(sm));
    }


    private ServiceConnection createServiceConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                ServiceManager.ServiceManagerBinder binder = (ServiceManager.ServiceManagerBinder) service;
                serviceManager = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {}
        };
    }
}
