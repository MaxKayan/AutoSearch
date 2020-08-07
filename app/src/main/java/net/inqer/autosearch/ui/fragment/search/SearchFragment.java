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
import androidx.fragment.app.DialogFragment;
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

import static net.inqer.autosearch.util.Util.DISPLACEMENTS;

public class SearchFragment extends DaggerFragment {
    private static final String TAG = "SearchFragment";
    // Dialog fragment id
    private final String REGION = "list_search_region";
    private final String CITY = "list_search_city";
    private final String MARK = "list_search_mark";
    private final String MODEL = "list_search_model";
    private final String PRICE = "values_picker_price";
    private final String YEAR = "values_picker_year";
    private final String DISPLACEMENT = "values_picker_displacement";
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
        binding.fEditPriceValue.setText(filter.getPriceMin() +
                " до: " + filter.getPriceMax());
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
            EditableFilter currentFilter = getCurrentFilter();
            if (currentFilter != null) {
                showDialog(
                        DialogValuesPicker.getCurrencyInstance(PRICE, "Цена", "Выберите интервал стоимости (р.)",
                                currentFilter.getPriceMin(), currentFilter.getPriceMax(), 0, 1000000, 1000),
                        PRICE
                );
            }
        });
        binding.fEditYear.setOnClickListener(v -> {
            EditableFilter currentFilter = getCurrentFilter();
            if (currentFilter != null) {
                showDialog(
                        DialogValuesPicker.getNumberInstance(YEAR, "Год выпуска", "Выберите год выпуска ТС",
                                currentFilter.getManufactureYearMin(), currentFilter.getManufactureYearMax(), 1980, 2020, 1),
                        YEAR
                );
            }
        });
        binding.fEditTransmission.setOnClickListener(v -> {

        });
        binding.fEditHull.setOnClickListener(v -> {

        });
        binding.fEditFuel.setOnClickListener(v -> {

        });
        binding.fEditDisplacement.setOnClickListener(v -> {
            showDialog(
                    DialogValuesPicker.getValuesInstance(DISPLACEMENT, "Объём двигателя",
                            "Укажите объём двигателя (л)", DISPLACEMENTS, "", ""),
                    DISPLACEMENT
            );
        });
        binding.fEditRadius.setOnClickListener(v -> {
            EditableFilter currentFilter = getCurrentFilter();
            if (currentFilter == null) return;

            showDialog(
                    DialogValuesPicker.getNumberInstance(RADIUS, "Радиус поиска", "Укажите радиус поиска (км)",
                            0, currentFilter.getRadius(), 0, 1000, 10),
                    RADIUS
            );
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
        String listKey = DialogListSearch.RESULT;
        String valKey = DialogValuesPicker.RESULT;

        manager.setFragmentResultListener(REGION, this, (requestKey, result) -> {
            viewModel.setRegion(result.getParcelable(listKey));
        });
        manager.setFragmentResultListener(CITY, this, (requestKey, result) -> {
            viewModel.setCity(result.getParcelable(listKey));
        });
        manager.setFragmentResultListener(MARK, this, (requestKey, result) -> {
            viewModel.setMark(result.getParcelable(listKey));
        });
        manager.setFragmentResultListener(MODEL, this, (requestKey, result) -> {
            viewModel.setModel(result.getParcelable(listKey));
        });
        manager.setFragmentResultListener(PRICE, this, (requestKey, result) -> {
            int[] values = result.getIntArray(valKey);
            if (values != null) {
                viewModel.setPrice(values[0], values[1]);
            }
        });
        manager.setFragmentResultListener(YEAR, this, (requestKey, result) -> {
            int[] values = result.getIntArray(valKey);
            if (values != null) {
                viewModel.setYear(values[0], values[1]);
            }
        });
        manager.setFragmentResultListener(DISPLACEMENT, this, (requestKey, result) -> {
            String[] values = result.getStringArray(valKey);
            if (values != null) {
                viewModel.setDisplacement(values[0], values[1]);
            }
        });
    }


    private <T extends ListItem> void showListSearchDialog(String requestCode, String title, String hint, Flowable<List<T>> dataSource) {
        DialogListSearch<T> dialog = DialogListSearch.newInstance(requestCode, title, hint, dataSource);
        showDialog(dialog, DialogListSearch.TAG);
    }

//    private void showValuesPickerDialog(String requestCode, String title, String hint, DialogValuesPicker.PickerType type,
//                                        Integer rawFrom, Integer rawTo, int min, int max, int step) {
//
//        DialogValuesPicker dialog = DialogValuesPicker.getInstance(requestCode, type, from, to, min, max, step, title, hint);
//        showDialog(dialog, DialogValuesPicker.TAG);
//    }

//    private void showValuesPickerDialog(String requestCode, String title, String hint, String[] values) {
//        DialogValuesPicker dialog = DialogValuesPicker.getInstance(requestCode, values, "", "", title, hint);
//        showDialog(dialog, DialogValuesPicker.TAG);
//    }

    private void showDialog(DialogFragment dialog, String tag) {
        FragmentManager manager = getParentFragmentManager();
        manager.executePendingTransactions();

        if (manager.findFragmentByTag(tag) != null) {
            Log.w(TAG, "showDialog: dialog " + tag + " already exists!");
            return;
        }

        dialog.show(manager, tag);
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
