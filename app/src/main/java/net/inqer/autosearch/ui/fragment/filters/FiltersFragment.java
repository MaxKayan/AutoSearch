package net.inqer.autosearch.ui.fragment.filters;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.inqer.autosearch.R;
import net.inqer.autosearch.databinding.FragmentFiltersBinding;

public class FiltersFragment extends Fragment {

    private FiltersViewModel mViewModel;

    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

    private FragmentFiltersBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filters, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentFiltersBinding.inflate(getLayoutInflater());
    }


    @Override
    public void onResume() {
        binding.filtersShimmerViewContainer.startShimmer();
        super.onResume();
    }


    @Override
    public void onPause() {
        binding.filtersShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
