package ch.pantherinblack.advancedbasicsensors.fragment;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.activity.SensorActivity;
import ch.pantherinblack.advancedbasicsensors.service.SensorService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorListItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorListItemFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME = "sensorName";
    boolean started = false;
    private String name;
    AppCompatActivity parent;
    Sensor sensor;

    public SensorListItemFragment() {
        // Required empty public constructor
    }

    public static SensorListItemFragment newInstance(Sensor sensor, AppCompatActivity parent) {
        SensorListItemFragment fragment = new SensorListItemFragment();
        Bundle args = new Bundle();
        args.putString(NAME, sensor.getName());

        fragment.sensor = sensor;
        fragment.parent = parent;
        fragment.name = sensor.getStringType();
        fragment.setArguments(args);
        return fragment;
    }

    public SensorEventListener getEventListener(SensorService sensorService) {
        return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                update(sensorService.getStringValues(sensorEvent.sensor, sensorEvent.values));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {}
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensor_list_item, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        LinearLayout linearLayout = getView().findViewById(R.id.valueLinearLayout);
        TextView textView = getView().findViewById(R.id.sensorName);
        textView.setText(name);
        linearLayout.setGravity(Gravity.CENTER);
        started = true;

        getView().findViewById(R.id.fragmentLayout).setOnClickListener(view -> {
            Intent intent = new Intent(parent, SensorActivity.class);
            intent.putExtra("type",sensor.getType());
            startActivity(intent);
        });
    }

    public void update(String[] values) {
        if (started) {
            LinearLayout linearLayout = getView().findViewById(R.id.valueLinearLayout);

            for (int i = 0; i < values.length; i++) {

                if (linearLayout.getChildCount() <= i) {
                    TextView tv = new TextView(getContext());

                    tv.setGravity(Gravity.CENTER);
                    tv.setText(values[i]);
                    linearLayout.addView(tv);
                } else {
                    ((TextView) linearLayout.getChildAt(i)).setText(values[i]);
                }
            }
            getView().invalidate();
            linearLayout.invalidate();
        }
    }
}