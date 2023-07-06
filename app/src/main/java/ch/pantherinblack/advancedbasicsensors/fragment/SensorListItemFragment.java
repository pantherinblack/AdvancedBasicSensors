package ch.pantherinblack.advancedbasicsensors.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.pantherinblack.advancedbasicsensors.R;
import ch.pantherinblack.advancedbasicsensors.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorListItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorListItemFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME = "sensorName";
    private static final String LIST = "valueLinearLayout";

    public SensorListItemFragment() {
        // Required empty public constructor
    }

    public static SensorListItemFragment newInstance(String name, String[] values) {
        SensorListItemFragment fragment = new SensorListItemFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);

        LinearLayout linearLayout = fragment.getView().findViewById(R.id.valueLinearLayout);

        for (String value : values) {
            TextView tv = new TextView(fragment.getContext());
            tv.setText(value);
            linearLayout.addView(tv);
        }


        fragment.setArguments(args);
        return fragment;
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
}