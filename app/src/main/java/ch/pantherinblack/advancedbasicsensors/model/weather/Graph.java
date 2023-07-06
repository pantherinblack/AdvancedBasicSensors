package ch.pantherinblack.advancedbasicsensors.model.weather;

import org.json.JSONObject;

import java.util.List;

public class Graph {
    long start;
    long startLowResolution;
    List<Float> precipitation10m;
    List<Float> precipitationMin10m;
    List<Float> precipitationMax10m;
    List<Float> precipitation1h;
    List<Float> precipitationMin1h;
    List<Float> precipitationMax1h;
    List<Integer> weatherDirection3h;
    List<Float> windSpeed3h;
    List<Float> temperatureMin1h;
    List<Float> temperatureMax1h;
    List<Float> temperatureMean1h;
    List<Long> sunrise;
    List<Long> sunset;
    List<Integer> weatherIcon3h;
    List<Integer> weatherIcon3hV2;

    public void fillFromJSON(JSONObject json) {
        // TODO
    }
}
