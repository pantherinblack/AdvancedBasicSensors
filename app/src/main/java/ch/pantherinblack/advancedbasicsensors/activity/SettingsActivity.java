package ch.pantherinblack.advancedbasicsensors.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ch.pantherinblack.advancedbasicsensors.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.backButton).setOnClickListener(view -> finish());
        //TODO
    }
}