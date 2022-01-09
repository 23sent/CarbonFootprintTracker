package com.example.carbonfootprinttracker.Graphs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carbonfootprinttracker.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PieChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PieChartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HEADER = "ARG_HEADER";
    private static final String ARG_LABEL = "ARG_LABEL";

    // TODO: Rename and change types of parameters
    private String mHeader;
    private String mLabel;

    public PieChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PieChartFragment.
     */
    public static PieChartFragment newInstance(String param1, String param2) {
        PieChartFragment fragment = new PieChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HEADER, param1);
        args.putString(ARG_LABEL, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHeader = getArguments().getString(ARG_HEADER);
            mLabel = getArguments().getString(ARG_LABEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pie_chart, container, false);
    }
}