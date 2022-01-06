package com.example.carbonfootprinttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class AddTransportationEmissionActivity extends AppCompatActivity {
    DatePickerFragment dataPickerFragment;
    Spinner typeSpinner;

    TextView datePickerTxt;
    EditText quantityInput;
    Button addBtn;
    TextView carbonEmissionTxt;

    Emission emission = new Emission();
    CarbonFootprintTracker app;

    List<EmissionTypes.Type> transporters = EmissionTypes.getTypes(EmissionTypes.Category.TRANSPORT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transportation_emission);


        app = CarbonFootprintTracker.getInstance();
        quantityInput = (EditText) findViewById(R.id.quantityInput);
        datePickerTxt = (TextView) findViewById(R.id.datePickerTxt);
        addBtn = (Button) findViewById(R.id.addBtn);
        carbonEmissionTxt = (TextView) findViewById(R.id.carbonEmissionTxt);
        typeSpinner = (Spinner) findViewById(R.id.transportTypeSpinner);

        setDateTxt(emission.getDate().getYear(), emission.getDate().getMonth(), emission.getDate().getDay());

        dataPickerFragment = new DatePickerFragment((DatePicker view, int year, int month, int day) -> {
            setDateTxt(year, month, day);
        });

        datePickerTxt.setOnClickListener((View v) -> {
            Log.d("test", "onCreate: " + dataPickerFragment.getShowsDialog());
            if (dataPickerFragment.getDialog() == null ||
                    !dataPickerFragment.getDialog().isShowing()
            ) {
                dataPickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        addBtn.setOnClickListener((View v) -> {
            Log.d("Add Emission", "on click add button");
            this.onClickSave();
        });

        quantityInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String quantityString = s.toString();
                if (!quantityString.trim().isEmpty()) {
                    float quantity = Float.parseFloat(quantityString);
                    emission.setQuantity(quantity);
                } else {
                    emission.setQuantity(0);
                }
                carbonEmissionTxt.setText(emission.getCarbonFootprint() + " kg CO2");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        emission.setType(transporters.get(0));

        String[] TRANSPORT_TYPES = EmissionTypes.getNames(transporters);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, TRANSPORT_TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setSelection(transporters.indexOf(emission.getType()));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Spinner selected", "onItemSelected: " + transporters.get(i));
                emission.setType(transporters.get(i));
                carbonEmissionTxt.setText(emission.getCarbonFootprint() + " kg CO2");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("Spinner selected", "onNothingSelected");
                emission.setType(null);
            }
        });
    }

    public void setDateTxt(int year, int month, int day) {
        datePickerTxt.setText(day + "/" + month + "/" + year);
        emission.setDate(new Date(year, month, day));
    }

    public void onClickSave() {
        if (emission.getDate() != null && emission.getType() != null && emission.getQuantity() > 0) {
            app.addEmissions(emission);
            Log.d("Add Emission", emission.toString());
            finish();
        } else {
            Toast.makeText(this, "Please fill the type, distance and date.", Toast.LENGTH_SHORT).show();
        }
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