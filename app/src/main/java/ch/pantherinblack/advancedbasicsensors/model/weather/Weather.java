package ch.pantherinblack.advancedbasicsensors.model.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Weather {
    CurrentWeather currentWeather;
    List<Forecast> forecasts;
    Graph graph;

    public void fillFromJSON(JSONObject json) {
        currentWeather = new CurrentWeather();
        graph = new Graph();
        try {
            currentWeather.fillFromJSON(json.getJSONObject("currentWeather"));
            graph.fillFromJSON(json.getJSONObject("currentWeather"));
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

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
