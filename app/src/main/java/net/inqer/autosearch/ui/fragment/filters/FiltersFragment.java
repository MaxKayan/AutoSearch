package net.inqer.autosearch.ui.fragment.filters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import net.inqer.autosearch.databinding.FragmentFiltersBinding;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FiltersFragment extends DaggerFragment {
    private static final String TAG = "FiltersFragment";

    @Inject
    FiltersAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;
    private FiltersViewModel viewModel;

    private FragmentFiltersBinding binding;

    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFiltersBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, providerFactory).get(FiltersViewModel.class);

        showProgressBar(true);
        setupRecyclerView();
        subscribeObservers();
        setupFab();
    }

    private void subscribeObservers() {
        viewModel.observeFilterData().observe(getViewLifecycleOwner(),
                filters -> adapter.submitList(filters));

        viewModel.observeFilterEvents().observe(getViewLifecycleOwner(), event -> {
            switch (event.status) {
                case LOADING:
                    showProgressBar(true);
                    break;
                case SUCCESS:
                    showProgressBar(false);
                    break;
                case ERROR:
                    showError(event.message);
                    showProgressBar(false);
                    break;
            }
        });
    }


    private void setupRecyclerView() {
        binding.filtersRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.filtersRecyclerview.setHasFixedSize(true);
        binding.filtersRecyclerview.setAdapter(adapter);
        Log.d(TAG, "setupRecyclerView: " + binding.filtersRecyclerview.getAdapter());
    }


    private void setupFab() {
        binding.filtersFab.setOnClickListener(v -> {
            viewModel.refreshData();
        });
        binding.filtersFab.setOnLongClickListener(v -> {
            viewModel.deleteFilters();
            return true;
        });
    }


    private void showProgressBar(boolean show) {
        if (show) {
            binding.filtersProgressbar.setVisibility(View.VISIBLE);
        } else {
            binding.filtersProgressbar.setVisibility(View.GONE);
        }
    }

    private void showError(String message) {
        Log.w(TAG, "showError: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
//        binding.filtersShimmerViewContainer.startShimmer();
        super.onResume();
    }


    @Override
    public void onPause() {
//        binding.filtersShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
