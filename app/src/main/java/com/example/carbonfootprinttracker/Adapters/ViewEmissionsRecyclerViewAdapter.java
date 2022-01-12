package com.example.carbonfootprinttracker.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carbonfootprinttracker.Fragments.EmissionsFragment;
import com.example.carbonfootprinttracker.Models.Emission;
import com.example.carbonfootprinttracker.databinding.EmissionFragmentItemBinding;

import java.util.List;


public class ViewEmissionsRecyclerViewAdapter extends RecyclerView.Adapter<ViewEmissionsRecyclerViewAdapter.ViewHolder> {

    private final List<Emission> mValues;
    private EmissionsFragment.OnEmissionSelectListener listener;

    public ViewEmissionsRecyclerViewAdapter(List<Emission> items, EmissionsFragment.OnEmissionSelectListener listener) {
        mValues = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(EmissionFragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Emission emission = mValues.get(position);
        holder.mItem = emission;
        holder.date.setText(emission.getDateString("dd MMM. yyyy"));
        holder.type.setText(emission.getTypeString());
        holder.amount.setText(emission.getCarbonFootprintString());
        holder.view.setOnClickListener((View view) -> {
            if (listener != null) {
                listener.onEmissionSelected(holder.mItem);
            }
        });

       holder.imgView.setImageResource(emission.getType().category.imageResource);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView date;
        public final TextView type;
        public final TextView amount;
        public final ImageView imgView;
        public Emission mItem;

        public ViewHolder(EmissionFragmentItemBinding binding) {
            super(binding.getRoot());
            date = binding.date;
            type = binding.type;
            amount = binding.amount;
            view = binding.getRoot();
            imgView  = binding.imageView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + type.getText() + "'";
        }
    }
}