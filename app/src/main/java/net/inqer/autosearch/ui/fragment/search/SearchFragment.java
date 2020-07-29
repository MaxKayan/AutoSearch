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
import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.EditableFilter;
import net.inqer.autosearch.data.model.ListItem;
import net.inqer.autosearch.databinding.FragmentSearchBinding;
import net.inqer.autosearch.ui.dev.DevActivity;
import net.inqer.autosearch.ui.dialog.listsearch.DialogListSearch;
import net.inqer.autosearch.ui.dialog.valuespicker.DialogValuesPicker;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.Flowable;

public class SearchFragment extends DaggerFragment {
    private static final String TAG = "SearchFragment";
    // Dialog fragment id
    private final String REGION = "list_search_region";
    private final String CITY = "list_search_city";
    private final String MARK = "list_search_mark";
    private final String MODEL = "list_search_model";
    private final String PRICE = "values_picker_price";
    private final String YEAR = "values_picker_year";
    private final String RADIUS = "values_picker_radius";

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
        setupResultListeners();
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
        binding.fEditMark.setOnClickListener(v -> showListSearchDialog(MARK, "Марка Авто", "Наименование марки",
                viewModel.observeMarks()));

        binding.fEditModel.setOnClickListener(v -> {
            EditableFilter currentFilter = getCurrentFilter();
            if (currentFilter != null && currentFilter.getCarMark() != null) {
                showListSearchDialog(MODEL, "Модель Авто", "Наименование модели",
                        viewModel.observeModelsByMark(currentFilter.getCarMark()));
            } else {
                Log.e(TAG, "setupClickListeners: fEditModel: null data");
            }
        });

        binding.fEditRegion.setOnClickListener(v -> {
            showListSearchDialog(REGION, "Регион", "Наименование региона",
                    viewModel.observeRegions());
        });

        binding.fEditCity.setOnClickListener(v -> {
            EditableFilter currentFilter = getCurrentFilter();
            if (currentFilter != null && currentFilter.getRegion() != null) {
                showListSearchDialog(CITY, "Город", "Наименование города",
                        viewModel.getCitiesByRegion(currentFilter.getRegion()).toFlowable());
            } else {
                Log.e(TAG, "setupClickListeners: fEditCity: null data");
            }
        });

        binding.fEditPrice.setOnClickListener(v -> {
            showValuesPickerDialog(PRICE, "Цена", "Выберите интервал стоимости (р.)",
                    0, 1000000, 1000);
        });
        binding.fEditYear.setOnClickListener(v -> {
            showValuesPickerDialog(YEAR, "Год выпуска", "Выберите год выпуска ТС",
                    1980, 2020, 1);
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
            showValuesPickerDialog(RADIUS, "Радиус поиска", "Укажите радиус поиска (км)",
                    0, 1000, 10);
        });

        binding.fEditSearchButton.setOnClickListener(v -> {
            viewModel.submitFilter();
        });
    }


    @Nullable
    private EditableFilter getCurrentFilter() {
        return viewModel.getCurrentFilter().getValue();
    }


    private void setupResultListeners() {
        FragmentManager manager = getParentFragmentManager();
        manager.setFragmentResultListener(REGION, this, (requestKey, result) -> {
            viewModel.setRegion(result.getParcelable(DialogListSearch.RESULT));
        });
        manager.setFragmentResultListener(CITY, this, (requestKey, result) -> {
            viewModel.setCity(result.getParcelable(DialogListSearch.RESULT));
        });
        manager.setFragmentResultListener(MARK, this, (requestKey, result) -> {
            viewModel.setMark(result.getParcelable(DialogListSearch.RESULT));
        });
        manager.setFragmentResultListener(MODEL, this, (requestKey, result) -> {
            viewModel.setModel(result.getParcelable(DialogListSearch.RESULT));
        });
    }


    private <T extends ListItem> void showListSearchDialog(String requestCode, String title, String hint, Flowable<List<T>> dataSource) {
        FragmentManager manager = getParentFragmentManager();
        manager.executePendingTransactions();
        if (manager.findFragmentByTag(DialogListSearch.TAG) != null) {
            Log.w(TAG, "setupClickListeners: fragment " + DialogListSearch.TAG + " already exists");
            return;
        }

        DialogListSearch<T> dialog = DialogListSearch.newInstance(requestCode, title, hint, dataSource);
        dialog.show(manager, DialogListSearch.TAG);
    }

    private void showValuesPickerDialog(String requestCode, String title, String hint, int min, int max, int step) {
        FragmentManager manager = getParentFragmentManager();
        manager.executePendingTransactions();

        DialogValuesPicker dialog = DialogValuesPicker.newInstance(requestCode, min, max, step, title, hint);
        dialog.show(manager, DialogValuesPicker.TAG);
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
