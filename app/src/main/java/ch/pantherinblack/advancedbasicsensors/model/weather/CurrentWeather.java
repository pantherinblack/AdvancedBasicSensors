package ch.pantherinblack.advancedbasicsensors.model.weather;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentWeather {
    long time;
    float temperature;
    int icon;
    int iconV2;

    public void fillFromJSON(JSONObject json) throws JSONException {
        temperature = (float) json.getDouble("temperature");
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIconV2() {
        return iconV2;
    }

    public void setIconV2(int iconV2) {
        this.iconV2 = iconV2;
    }
}
