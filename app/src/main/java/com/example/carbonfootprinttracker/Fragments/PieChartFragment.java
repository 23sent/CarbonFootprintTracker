package com.example.carbonfootprinttracker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carbonfootprinttracker.CustomGraphs.PieChart;
import com.example.carbonfootprinttracker.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Fragment contains PieChart, title and labels to use in MainActivity.
 * Utku Sağocak
 */
public class PieChartFragment extends Fragment implements Serializable {

    private static final String ARG_HEADER = "ARG_HEADER";
    private static final String ARG_LABEL = "ARG_LABEL";
    private static final String ARG_CHART_LABEL = "ARG_CHART_LABEL";
    private static final String ARG_PIE_SLICES = "ARG_PIE_SLICES";


    private String mHeader;
    private String mLabel;
    private String mChartLabel;
    private ArrayList<PieChart.PieSlice> mPieSlices;

    public PieChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param h  Header.
     * @param l  Label.
     * @param cl PieChart Label.
     * @param sl PiChart.PieSlice List.
     * @return A new instance of fragment PieChartFragment.
     */
    public static PieChartFragment newInstance(String h, String l, String cl, ArrayList<PieChart.PieSlice> sl) {
        PieChartFragment fragment = new PieChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HEADER, h);
        args.putString(ARG_LABEL, l);
        args.putString(ARG_CHART_LABEL, cl);
        args.putSerializable(ARG_PIE_SLICES, sl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHeader = getArguments().getString(ARG_HEADER);
            mLabel = getArguments().getString(ARG_LABEL);
            mChartLabel = getArguments().getString(ARG_CHART_LABEL);
            mPieSlices = (ArrayList<PieChart.PieSlice>) getArguments().getSerializable(ARG_PIE_SLICES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pie_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView header = view.findViewById(R.id.header);
        TextView label = view.findViewById(R.id.label);
        PieChart chart = view.findViewById(R.id.pie_chart);

        header.setText(mHeader);
        label.setText(mLabel);
        chart.setText(mChartLabel);

        chart.setPieSlices(mPieSlices);
    }
}