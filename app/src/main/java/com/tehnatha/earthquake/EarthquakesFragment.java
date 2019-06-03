package com.tehnatha.earthquake;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.tehnatha.earthquake.databinding.FragmentEarthquakesBinding;

import java.util.List;


public class EarthquakesFragment extends Fragment {

    private EarthquakesViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final FragmentEarthquakesBinding binding = FragmentEarthquakesBinding.inflate(inflater);

        binding.setViewmodel(viewModel);

        viewModel.getEarthquakes().observe(this, new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(@Nullable List<Earthquake> earthquakes) {
                StringBuilder builder = new StringBuilder();
                if (earthquakes == null) builder.append("failed");
                else
                    for (Earthquake quake : earthquakes)
                        builder.append(quake.getLocation()).append("\n");
                binding.text.setText(builder.toString());
            }
        });

        return binding.frame;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders
                .of(this, new EarthquakesViewModelFactory(getActivity().getApplication()))
                .get(EarthquakesViewModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
