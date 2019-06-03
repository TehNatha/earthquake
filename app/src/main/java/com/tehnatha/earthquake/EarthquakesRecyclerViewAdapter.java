package com.tehnatha.earthquake;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tehnatha.earthquake.databinding.EarthquakeItemBinding;

import java.util.List;

public class EarthquakesRecyclerViewAdapter extends RecyclerView.Adapter<EarthquakesRecyclerViewAdapter.EarthquakeHolder> {
    private List<Earthquake> data;
    private LayoutInflater inflater;

    public EarthquakesRecyclerViewAdapter(Context context, List<Earthquake> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public EarthquakeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EarthquakeItemBinding binding = EarthquakeItemBinding.inflate(inflater);
        binding.earthquakeItemOuterFrame.setLayoutParams(parent.getLayoutParams());
        return new EarthquakeHolder(binding.earthquakeItemOuterFrame, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Earthquake> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class EarthquakeHolder extends RecyclerView.ViewHolder {

        EarthquakeItemBinding binding;

        EarthquakeHolder(@NonNull View itemView, @NonNull EarthquakeItemBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void bindData(Earthquake data) {
            binding.setModel(data);
        }
    }
}
