package com.example.carbonfootprinttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.carbonfootprinttracker.Graphs.LineGraph;

import java.util.ArrayList;

public class ViewEmissionsActivity extends AppCompatActivity {
    LineGraph graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emissions);
        graph = findViewById(R.id.weeklyGraph);
        initLineGraph();
    }

    public void initLineGraph() {
        ArrayList<LineGraph.PlotPoint> points = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            points.add(new LineGraph.PlotPoint(i , i , i+""));
        }
        graph.setPlotPoints(points);
    }
}