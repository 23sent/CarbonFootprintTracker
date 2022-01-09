package com.example.carbonfootprinttracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class EmissionsFragment extends Fragment {

    private static final String ARG_EMISSION_LIST = "ARG_EMISSION_LIST";

    private ArrayList<Emission> emissions;
    private OnEmissionSelectListener listener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmissionsFragment() {
    }

    @SuppressWarnings("unused")
    public static EmissionsFragment newInstance(ArrayList<Emission> e) {
        EmissionsFragment fragment = new EmissionsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EMISSION_LIST, e);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            emissions = (ArrayList<Emission>) getArguments().getSerializable(ARG_EMISSION_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emission_fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new ViewEmissionsRecyclerViewAdapter(emissions, listener));
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnEmissionSelectListener) {
            listener = (OnEmissionSelectListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnNoteListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnEmissionSelectListener {
        void onEmissionSelected(Emission emission);
    }
}