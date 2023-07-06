package ch.pantherinblack.advancedbasicsensors.model.weather;

import org.json.JSONObject;

import java.util.List;

public class Weather {
    CurrentWeather currentWeather;
    List<Forecast> forecasts;
    Graph graph;

    public void fillFromJSON(JSONObject json) {
        //TODO
    }
}
