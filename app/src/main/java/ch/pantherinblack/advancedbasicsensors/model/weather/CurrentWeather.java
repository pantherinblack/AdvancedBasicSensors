package ch.pantherinblack.advancedbasicsensors.model.weather;

import org.json.JSONObject;

public class CurrentWeather {
    long time;
    float temperature;
    int icon;
    int iconV2;

    public void fillFromJSON(JSONObject json) {
        //TODO
    }
}
