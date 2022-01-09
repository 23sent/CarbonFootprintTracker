package com.example.carbonfootprinttracker;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.mItem = mValues.get(position);
        holder.date.setText(mValues.get(position).getDateString("dd MMM. yyyy"));
        holder.type.setText(mValues.get(position).getTypeString());
        holder.amount.setText(mValues.get(position).getCarbonFootprintString());
        holder.view.setOnClickListener((View view) -> {
            if (listener != null) {
                listener.onEmissionSelected(holder.mItem);
            }
        });
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
        public Emission mItem;

        public ViewHolder(EmissionFragmentItemBinding binding) {
            super(binding.getRoot());
            date = binding.date;
            type = binding.type;
            amount = binding.amount;
            view = binding.getRoot();
        }

        @Override
        public String toString() {
            return super.toString() + " '" + type.getText() + "'";
        }
    }
}