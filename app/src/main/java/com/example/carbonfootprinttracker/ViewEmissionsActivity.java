package com.example.carbonfootprinttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carbonfootprinttracker.Graphs.LineGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ViewEmissionsActivity extends AppCompatActivity implements EmissionsFragment.OnEmissionSelectListener {
    LineGraph graph;
    CarbonFootprintTracker app = CarbonFootprintTracker.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emissions);
        graph = findViewById(R.id.weekly_graph);
        initLineGraph();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.emissions_container, EmissionsFragment.newInstance(app.getEmissions()));
        ft.commit();
    }

    public void initLineGraph() {
        ArrayList<LineGraph.PlotPoint> points = new ArrayList<>();

        List<Emission> emissions = app.getEmissions();

        // Show last 9 emission in graph.
        for(int i = 0; i <= 9 && i < emissions.size(); i++) {
            Emission emission = emissions.get(emissions.size() -i -1);
            points.add(new LineGraph.PlotPoint( i, emission.getCarbonFootprint(), ""));
        }
        graph.setPlotPoints(points);
    }

    @Override
    public void onEmissionSelected(Emission emission) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("emission", emission);
        Intent intent = new Intent(this, EmissionDetails.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}