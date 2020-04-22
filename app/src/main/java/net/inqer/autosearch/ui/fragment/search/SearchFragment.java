package net.inqer.autosearch.ui.fragment.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.EditableFilter;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.databinding.FragmentSearchBinding;
import net.inqer.autosearch.ui.dialog.DialogListSearch;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SearchFragment extends DaggerFragment {
    private static final String TAG = "SearchFragment";
    private final int REGION = 1;
    @Inject
    ViewModelProviderFactory providerFactory;
    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, providerFactory).get(SearchViewModel.class);
        setHasOptionsMenu(true);

        subscribeObservers();
        setupClickListeners();
    }

    private void subscribeObservers() {
        viewModel.getCurrentFilter().observe(getViewLifecycleOwner(), queryFilter -> {
            Log.d(TAG, "subscribeObservers: current filter changed");
            setupViewByFilter(queryFilter);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupViewByFilter(EditableFilter filter) {
        binding.fEditMarkValue.setText(filter.getCarMark());
        binding.fEditModelValue.setText(filter.getCarModel());
        binding.fEditRegionValue.setText(filter.getRegion() == null ? "" : filter.getRegion().getName());
        binding.fEditCityValue.setText(filter.getCities());
        binding.fEditPriceValue.setText(filter.getPriceMinimum() +
                " до: " + filter.getPriceMaximum());
        binding.fEditYearValue.setText(filter.getManufactureYearMin() + " до: " + filter.getManufactureYearMax());
        binding.fEditTransmissionValue.setText(filter.getTransmission());
        binding.fEditHullValue.setText(filter.getHull());
        binding.fEditFuelValue.setText(filter.getFuel());
        binding.fEditDisplacementValue.setText(filter.getEngineDisplacementMin() + " до: " + filter.getEngineDisplacementMax());
        binding.fEditRadiusValue.setText(String.valueOf(filter.getRadius()));
    }


    private void setupClickListeners() {
        binding.fEditMark.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Mark clicked!", Toast.LENGTH_SHORT).show();
        });
        binding.fEditModel.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Model clicked!", Toast.LENGTH_SHORT).show();
        });

        binding.fEditRegion.setOnClickListener(v -> {
            DialogListSearch dialog = DialogListSearch.newInstance("Регион", "Наименование региона", new ArrayList<Region>());
            dialog.setTargetFragment(SearchFragment.this, REGION);
            dialog.show(getParentFragmentManager(), "RegionDialog");
        });
        binding.fEditCity.setOnClickListener(v -> {

        });

        binding.fEditPrice.setOnClickListener(v -> {

        });
        binding.fEditYear.setOnClickListener(v -> {

        });
        binding.fEditTransmission.setOnClickListener(v -> {

        });
        binding.fEditHull.setOnClickListener(v -> {

        });
        binding.fEditFuel.setOnClickListener(v -> {

        });
        binding.fEditDisplacement.setOnClickListener(v -> {

        });
        binding.fEditRadius.setOnClickListener(v -> {

        });

        binding.fEditSearchButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Search button clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode + " ; resultCode: " + resultCode + " intent data: " + data);
        if (data != null) {
            switch (requestCode) {
                case REGION:
                    if (resultCode == Activity.RESULT_OK) {
                        String rSlug = data.getStringExtra(DialogListSearch.REG_ID);
                        viewModel.setRegion(rSlug);
//                        viewModel.getCurrentFilter().getValue().setRegion();
                    }
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_bar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Toast.makeText(getContext(), "Profile button pressed", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(getContext(), "Settings button pressed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
