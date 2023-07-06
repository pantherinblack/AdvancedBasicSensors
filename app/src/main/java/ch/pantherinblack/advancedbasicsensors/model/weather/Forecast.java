package ch.pantherinblack.advancedbasicsensors.model.weather;

import org.json.JSONObject;

public class Forecast {
    String dayDate;
    int iconDay;
    int iconDayV2;
    float temperatureMax;
    float temperatureMin;
    float precipitation;

    public void fillFromJSON(JSONObject json) {
        // TODO
    }
}
