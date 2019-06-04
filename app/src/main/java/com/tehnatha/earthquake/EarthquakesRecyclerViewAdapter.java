package com.tehnatha.earthquake;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tehnatha.earthquake.databinding.EarthquakeItemBinding;
import com.tehnatha.earthquake.datamodel.Earthquake;
import com.tehnatha.earthquake.viewmodels.EarthquakeListItemViewModel;

import java.util.List;

public class EarthquakesRecyclerViewAdapter extends RecyclerView.Adapter<EarthquakesRecyclerViewAdapter.EarthquakeHolder> {
    private List<Earthquake> data;
    private LayoutInflater inflater;

    public EarthquakesRecyclerViewAdapter(Context context, List<Earthquake> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public EarthquakeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EarthquakeItemBinding binding = EarthquakeItemBinding.inflate(inflater);
        binding.getRoot().setLayoutParams(parent.getLayoutParams());
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

    @Override
    public long getItemId(int position) {
        return data.get(position).getEqid().hashCode();
    }

    public void setData(List<Earthquake> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class EarthquakeHolder extends RecyclerView.ViewHolder {

        EarthquakeItemBinding binding;

        EarthquakeHolder(@NonNull EarthquakeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(final Earthquake data) {
            binding.setViewmodel(new EarthquakeListItemViewModel(data));
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("lat", data.getLat());
                    intent.putExtra("lng", data.getLng());
                    context.startActivity(intent);
                }
            });
        }
    }
}
