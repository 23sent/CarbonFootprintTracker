package com.example.carbonfootprinttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class EmissionDetails extends AppCompatActivity {
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
            Intent intent = new Intent(this, ViewEmissionsActivity.class);
            startActivity(intent);
        });

        imgView = findViewById(R.id.imageView);
        imgView.setImageResource(emission.getType().category.imageResource);
    }

    public void onClickDelete() {
        app.deleteEmission(emission);
    }
}