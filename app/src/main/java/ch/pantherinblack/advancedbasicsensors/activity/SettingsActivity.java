package ch.pantherinblack.advancedbasicsensors.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.service.SensorService;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPref = getSharedPreferences("settings", MODE_PRIVATE);
        RadioGroup distanceRadioGroup = findViewById(R.id.distanceRadioGroup);
        RadioGroup temperatureRadioGroup = findViewById(R.id.temperatureRadioGroup);

        switch (sharedPref.getInt("distance",0 )) {
            case SensorService.DISTANCE_METER:
                distanceRadioGroup.check(R.id.metricRadio);
                break;
            case SensorService.DISTANCE_IMPERIAL:
                distanceRadioGroup.check(R.id.imperialRadio);
                break;
        }

        switch (sharedPref.getInt("temperature",0 )) {
            case SensorService.TEMPERATURE_CELSIUS:
                temperatureRadioGroup.check(R.id.celsiusRadio);
                break;
            case SensorService.TEMPERATURE_KELVIN:
                temperatureRadioGroup.check(R.id.kelvinRadio);
                break;
            case SensorService.TEMPERATURE_FAHRENHEIT:
                temperatureRadioGroup.check(R.id.fahrenheitRadio);
                break;
        }

        findViewById(R.id.backButton).setOnClickListener(view -> {
            int distance = SensorService.DISTANCE_METER;
            int temperature = SensorService.TEMPERATURE_CELSIUS;

            RadioButton distanceRadioButton = findViewById(distanceRadioGroup.getCheckedRadioButtonId());
            String distanceText = distanceRadioButton.getText().toString();

            if (distanceText.equals("Imperial"))
                distance = SensorService.DISTANCE_IMPERIAL;

            RadioButton temperatureRadioButton = findViewById(temperatureRadioGroup.getCheckedRadioButtonId());
            String temperatureText = temperatureRadioButton.getText().toString();

            if (temperatureText.equals("Kelvin"))
                temperature = SensorService.TEMPERATURE_KELVIN;
            else if (temperatureText.equals("Fahrenheit"))
                temperature = SensorService.TEMPERATURE_FAHRENHEIT;



            sharedPref.edit()
                    .putInt("distance",distance)
                    .putInt("temperature",temperature)
                    .apply();

            finish();
        });
    }
}