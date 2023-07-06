package ch.pantherinblack.advancedbasicsensors.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import ch.pantherinblack.advancedbasicsensors.model.weather.Weather;

public class WeatherService extends ServiceManager {

    public Weather getWeather(String postalCode) {
        Thread thread = new Thread(() -> {
            try {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("https://httpbin.org/post");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
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
        return null;
    }

}