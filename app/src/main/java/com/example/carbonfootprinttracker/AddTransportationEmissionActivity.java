package com.example.carbonfootprinttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class AddTransportationEmissionActivity extends AppCompatActivity {
    TextView datePickerTxt;
    DatePickerFragment dataPickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transportation_emission);
        dataPickerFragment = new DatePickerFragment((DatePicker view, int year, int month, int day) -> {
            onDateSetOuter(view, year, month, day);
        });

        datePickerTxt = (TextView) findViewById(R.id.datePickerTxt);
        datePickerTxt.setOnClickListener((View v) -> {
            Log.d("test", "onCreate: " + dataPickerFragment.getShowsDialog());
            if (dataPickerFragment.getDialog() == null ||
                    !dataPickerFragment.getDialog().isShowing()
            ) {
                dataPickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    public void onDateSetOuter(DatePicker view, int year, int month, int day) {
        datePickerTxt.setText(day + "/" + month + "/" + year);
    }

    public static class DatePickerFragment extends DialogFragment {
        private DatePickerDialog.OnDateSetListener listener;

        private DatePickerFragment(DatePickerDialog.OnDateSetListener l) {
            listener = l;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }

    }
}