package ch.pantherinblack.advancedbasicsensors.model.weather;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather {
    CurrentWeather currentWeather;

    public void fillFromJSON(JSONObject json) {
        currentWeather = new CurrentWeather();
        try {
            currentWeather.fillFromJSON(json.getJSONObject("currentWeather"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

}
