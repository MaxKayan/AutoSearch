package net.inqer.autosearch.ui.fragment.search;

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
import net.inqer.autosearch.databinding.FragmentSearchBinding;
import net.inqer.autosearch.ui.dialog.TestDialog;
import net.inqer.autosearch.util.ViewModelProviderFactory;

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

        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.fEditMark.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Mark clicked!", Toast.LENGTH_SHORT).show();
        });
        binding.fEditModel.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Model clicked!", Toast.LENGTH_SHORT).show();
        });

        binding.fEditRegion.setOnClickListener(v -> {
            TestDialog dialog = new TestDialog();
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
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            binding.fEditRegionValue.setText(data.getStringExtra(TestDialog.REG_NAME));

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
