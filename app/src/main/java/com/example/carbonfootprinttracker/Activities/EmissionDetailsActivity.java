package com.example.carbonfootprinttracker.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carbonfootprinttracker.EmissionTypes;
import com.example.carbonfootprinttracker.Models.CarbonFootprintTracker;
import com.example.carbonfootprinttracker.Models.Emission;
import com.example.carbonfootprinttracker.R;

/**
 * Utku Sağocak, Hüseyin Emre Arı
 */
public class EmissionDetailsActivity extends AppCompatActivity {
    Emission emission;
    TextView categoryTxt;
    TextView typeTxt;
    TextView quantityHeader;
    TextView quantityTxt;
    TextView dateTxt;
    TextView emissionAmountTxt;

    Button deleteBtn;

    CarbonFootprintTracker app;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emission_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View actionBar = getSupportActionBar().getCustomView();
        TextView pageHead = actionBar.findViewById(R.id.pageNameTxt);
        pageHead.setText("Emission Details");
        ImageView backBtn = actionBar.findViewById(R.id.backBtn);
        backBtn.setOnClickListener((View view) -> {
            onBackPressed();
        });

        emission = (Emission) getIntent().getExtras().getSerializable("emission");

        categoryTxt = findViewById(R.id.categoryTxt);
        typeTxt = findViewById(R.id.typeTxt);
        quantityHeader = findViewById(R.id.quantityHeader);
        quantityTxt = findViewById(R.id.quantityTxt);
        dateTxt = findViewById(R.id.dateTxt);
        emissionAmountTxt = findViewById(R.id.emissionTxt);
        deleteBtn = findViewById(R.id.deleteBtn);

        EmissionTypes.Type type = emission.getType();
        categoryTxt.setText(type.category.name);
        typeTxt.setText(type.name);
        quantityHeader.setText(type.unit.tag);
        quantityTxt.setText(emission.getQuantity() + " " + type.unit.name);
        emissionAmountTxt.setText(emission.getCarbonFootprintString());

        dateTxt.setText(emission.getDateString("EEEE, dd MMMM yyyy"));

        app = CarbonFootprintTracker.getInstance();

        deleteBtn.setOnClickListener((View view) -> {
            this.onClickDelete();
            Toast.makeText(this, "Emission is Deleted", Toast.LENGTH_SHORT).show();
            finish();
        });

        imgView = findViewById(R.id.imageView);
        imgView.setImageResource(emission.getType().category.imageResource);
    }

    public void onClickDelete() {
        app.deleteEmission(emission);
    }
}