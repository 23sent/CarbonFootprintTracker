package com.example.carbonfootprinttracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddTransportationEmissionActivity extends AppCompatActivity {
    private EmissionTypes.Category category = EmissionTypes.Category.TRANSPORT;

    DatePickerFragment dataPickerFragment;
    Spinner typeSpinner;

    TextView datePickerTxt;
    EditText quantityInput;
    Button addBtn;
    TextView carbonEmissionTxt;

    Emission emission = new Emission();
    CarbonFootprintTracker app;

    List<EmissionTypes.Type> emissionTypes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transportation_emission);
        category = (EmissionTypes.Category) getIntent().getExtras().getSerializable("category");
        emissionTypes = EmissionTypes.getTypes(category);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View actionBar = getSupportActionBar().getCustomView();
        TextView pageHead = actionBar.findViewById(R.id.pageNameTxt);
        pageHead.setText("Add Emission");
        ImageView backBtn = actionBar.findViewById(R.id.backBtn);
        backBtn.setOnClickListener((View view) -> {
            onBackPressed();
        });

        initTypeText();

        app = CarbonFootprintTracker.getInstance();
        quantityInput = (EditText) findViewById(R.id.quantityInput);
        datePickerTxt = (TextView) findViewById(R.id.datePickerTxt);
        addBtn = (Button) findViewById(R.id.addBtn);
        carbonEmissionTxt = (TextView) findViewById(R.id.carbonEmissionTxt);
        typeSpinner = (Spinner) findViewById(R.id.transportTypeSpinner);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(emission.getDate());
        setDateTxt(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH), calendar.get(GregorianCalendar.DAY_OF_MONTH));

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
                carbonEmissionTxt.setText(emission.getCarbonFootprintString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        emission.setType(emissionTypes.get(0));

        String[] TRANSPORT_TYPES = EmissionTypes.getNames(emissionTypes);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, TRANSPORT_TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setSelection(emissionTypes.indexOf(emission.getType()));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setType(emissionTypes.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("Spinner selected", "onNothingSelected");
                emission.setType(null);
            }
        });

        Toast.makeText(this, "" + category.name, Toast.LENGTH_SHORT).show();
    }

    public void initTypeText() {
        String typeText = "";
        String headerText = "";
        if (category == EmissionTypes.Category.TRANSPORT) {
            typeText = "Transport";
            headerText = "Transportation";
        } else if (category == EmissionTypes.Category.ENERGY) {
            typeText = "Energy or Resource";
            headerText = "Natural Resources";
        } else if (category == EmissionTypes.Category.AGRICULTURE) {
            typeText = "Agriculture or Food";
            headerText = "Agriculture & Food";
        }
        typeText += " Type";

        TextView type = findViewById(R.id.type);
        type.setText(typeText);

        TextView header = findViewById(R.id.header);
        header.setText(headerText);
    }

    public void setType(EmissionTypes.Type type) {
        Log.d("Spinner selected", "onItemSelected: " + type);
        emission.setType(type);
        carbonEmissionTxt.setText(emission.getCarbonFootprintString());

        TextView unitHeader = findViewById(R.id.unitHeader);
        unitHeader.setText(type.unit.tag);

        TextView unitText = findViewById(R.id.unitText);
        unitText.setText(type.unit.name);
    }

    public void setDateTxt(int year, int month, int day) {
        Date newDate = new GregorianCalendar(year, month, day).getTime();
        emission.setDate(newDate);
        datePickerTxt.setText(emission.getDateString("EEEE, dd MMMM yyyy"));
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