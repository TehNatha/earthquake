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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tehnatha.earthquake.databinding.FragmentEarthquakesBinding;
import com.tehnatha.earthquake.datamodel.Earthquake;
import com.tehnatha.earthquake.viewmodels.EarthquakesListViewModel;
import com.tehnatha.earthquake.viewmodels.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class EarthquakesListFragment extends Fragment {

    private EarthquakesListViewModel viewModel;
    private EarthquakesRecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final FragmentEarthquakesBinding binding = FragmentEarthquakesBinding.inflate(inflater);

        binding.setViewmodel(viewModel);
        binding.recyclerView.setAdapter(adapter);

        viewModel.getEarthquakes().observe(this, getEarthquakeObserver(binding, adapter));

        binding.earthquakesListRefresh.setOnRefreshListener(getRefreshListener(viewModel));

        return binding.earthquakesFragmentOuterFrame;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getApplication()))
                .get(EarthquakesListViewModel.class);
        adapter = new EarthquakesRecyclerViewAdapter(context, new ArrayList<Earthquake>());
    }

    private static Observer<List<Earthquake>> getEarthquakeObserver(final FragmentEarthquakesBinding binding, final EarthquakesRecyclerViewAdapter adapter) {
        return new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(@Nullable List<Earthquake> earthquakes) {
                if (earthquakes != null && earthquakes.size() > 0) {
                    adapter.setData(earthquakes);
                    binding.earthquakesListRefresh.setRefreshing(false);
                    binding.earthquakesListRefresh.setEnabled(false);
                }
            }
        };
    }

    private static SwipeRefreshLayout.OnRefreshListener getRefreshListener(final EarthquakesListViewModel viewModel) {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
            }
        };
    }
}
