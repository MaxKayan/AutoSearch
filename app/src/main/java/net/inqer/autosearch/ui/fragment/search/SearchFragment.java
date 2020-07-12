package net.inqer.autosearch.ui.fragment.search;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.EditableFilter;
import net.inqer.autosearch.databinding.FragmentSearchBinding;
import net.inqer.autosearch.ui.dev.DevActivity;
import net.inqer.autosearch.ui.dialog.listsearch.DialogListSearch;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.Flowable;

public class SearchFragment extends DaggerFragment {
    private static final String TAG = "SearchFragment";
    // Dialog fragment id
    private final int REGION = 1;
    private final int CITY = 2;
    private final int MARK = 3;
    private final int MODEL = 4;

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
        setResultListeners();
    }


    /**
     * Subscribes to needed live data.
     * Current filter - instance of editable filter that is currently being viewed/edited
     */
    private void subscribeObservers() {
        viewModel.getCurrentFilter().observe(getViewLifecycleOwner(), queryFilter -> {
            Log.d(TAG, "subscribeObservers: current filter changed");
            setupViewByFilter(queryFilter);
        });
    }

    /**
     * Sets all values of an existing EditableFilter instance to the view.
     *
     * @param filter Saved filter that contains some values
     */
    @SuppressLint("SetTextI18n")
    private void setupViewByFilter(EditableFilter filter) {
        binding.fEditMarkValue.setText(filter.getCarMark() == null ? "" : filter.getCarMark().getName());
        binding.fEditModelValue.setText(filter.getCarModel() == null ? "" : filter.getCarModel().getName());
        binding.fEditRegionValue.setText(filter.getRegion() == null ? "" : filter.getRegion().getName());
        binding.fEditCityValue.setText(filter.getCity() == null ? "" : filter.getCity().getName());
        binding.fEditPriceValue.setText(filter.getPriceMinimum() +
                " до: " + filter.getPriceMaximum());
        binding.fEditYearValue.setText(filter.getManufactureYearMin() + " до: " + filter.getManufactureYearMax());
        binding.fEditTransmissionValue.setText(filter.getTransmission());
        binding.fEditHullValue.setText(filter.getHull());
        binding.fEditFuelValue.setText(filter.getFuel());
        binding.fEditDisplacementValue.setText(filter.getEngineDisplacementMin() + " до: " + filter.getEngineDisplacementMax());
        binding.fEditRadiusValue.setText(String.valueOf(filter.getRadius()));

        if (filter.getRegion() == null) binding.fEditCityLabel.setEnabled(false);
        else binding.fEditCityLabel.setEnabled(true);
    }


    /**
     * Sets on-click listeners for all parameters of the filter editor.
     */
    private void setupClickListeners() {
        binding.fEditMark.setOnClickListener(v -> showListSearchDialog(MARK, "Марка Авто", "", "Наименование марки",
                viewModel.observeMarks()));
        binding.fEditModel.setOnClickListener(v -> {
            EditableFilter currentFilter = getCurrentFilter();
            if (currentFilter != null && currentFilter.getCarMark() != null) {
                showListSearchDialog(MODEL, "Модель Авто", "", "Наименование модели",
                        viewModel.observeModelsByMark(currentFilter.getCarMark()));
            } else Log.e(TAG, "setupClickListeners: fEditModel: null data");
        });

        binding.fEditRegion.setOnClickListener(v -> {
            showListSearchDialog(REGION, "Регион", "", "Наименование региона",
                    viewModel.observeRegions());
        });
        binding.fEditCity.setOnClickListener(v -> {
            EditableFilter currentFilter = getCurrentFilter();
            if (currentFilter != null && currentFilter.getRegion() != null) {
                showListSearchDialog(CITY, "Город", "", "Наименование города",
                        viewModel.getCitiesByRegion(currentFilter.getRegion()).toFlowable());
            } else Log.e(TAG, "setupClickListeners: fEditCity: null data");
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
            viewModel.submitFilter();
        });
    }

    @Nullable
    private EditableFilter getCurrentFilter() {
        return viewModel.getCurrentFilter().getValue();
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Log.d(TAG, "onActivityResult: requestCode: " + requestCode + " ; resultCode: " + resultCode + " intent data: " + data);
//        if (data != null) {
//            switch (requestCode) {
//                case MARK:
//                    if (resultCode == Activity.RESULT_OK) {
//                        viewModel.setMark(data.getParcelableExtra(DialogListSearch.RESULT));
//                    }
//                    break;
//                case MODEL:
//                    if (resultCode == Activity.RESULT_OK) {
//                        viewModel.setModel(data.getParcelableExtra(DialogListSearch.RESULT));
//                    }
//                    break;
//                case REGION:
//                    if (resultCode == Activity.RESULT_OK) {
//                        viewModel.setRegion(data.getParcelableExtra(DialogListSearch.RESULT));
//                    }
//                    break;
//                case CITY:
//                    if (resultCode == Activity.RESULT_OK) {
//                        viewModel.setCity(data.getParcelableExtra(DialogListSearch.RESULT));
//                    }
//                    break;
//            }
//        }
//    }


    private void setResultListeners() {
        getParentFragmentManager().setFragmentResultListener("listener", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.i(TAG, "onFragmentResult: " + requestKey + " - " + result.toString() + " - " + result.getString(DialogListSearch.RESULT));
            }
        });
    }


    private void showListSearchDialog(int requestCode, String title, String subtitle, String hint, Flowable dataSource) {
        FragmentManager manager = getParentFragmentManager();
        manager.executePendingTransactions();
        if (manager.findFragmentByTag(DialogListSearch.TAG) != null) {
            Log.w(TAG, "setupClickListeners: fEditRegion already exists");
            return;
        }

        DialogListSearch dialog = DialogListSearch.newInstance(title, hint, dataSource);
//        dialog.setTargetFragment(SearchFragment.this, requestCode);
        dialog.show(manager, DialogListSearch.TAG);
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
                startActivity(new Intent(getContext(), DevActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
