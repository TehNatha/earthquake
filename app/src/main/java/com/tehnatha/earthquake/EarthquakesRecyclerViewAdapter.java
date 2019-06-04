package com.tehnatha.earthquake;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tehnatha.earthquake.databinding.EarthquakeItemBinding;

import java.lang.ref.WeakReference;
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
        return new EarthquakeHolder(binding);
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
        WeakReference<MainActivity> activity;

        EarthquakeHolder(@NonNull EarthquakeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            Context context = binding.getRoot().getContext();
            if (context instanceof  MainActivity)
                this.activity = new WeakReference<>((MainActivity) context);
        }

        public void bindData(Earthquake data) {
            binding.setModel(data);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity.get() != null)
                        activity.get().openMap();
                }
            });
        }
    }
}
