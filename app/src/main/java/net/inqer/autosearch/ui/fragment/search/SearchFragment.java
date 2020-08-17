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
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import net.inqer.autosearch.ui.dialog.radiopicker.DialogRadioPicker;
import net.inqer.autosearch.ui.dialog.valuespicker.DialogSinglePicker;
import net.inqer.autosearch.ui.dialog.valuespicker.DialogValuesPicker;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
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
    private final String TRANSMISSION = "values_picker_transmission";
    private final String HULL = "values_picker_hull";
    private final String FUEL = "values_picker_fuel";
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
        viewModel.getCurrentFilter().observe(getViewLifecycleOwner(), this::setupViewByFilter);
    }


    /**
     * Sets all values of an {@link EditableFilter} instance to the view.
     *
     * @param filter Saved filter that contains some values
     */
    @SuppressLint("SetTextI18n")
    private void setupViewByFilter(EditableFilter filter) {
        setParamState(binding.fEditMark, true, filter.getCarMark());
        setParamState(binding.fEditModel, filter.getCarMark() != null, filter.getCarModel());
        setParamState(binding.fEditRegion, true, filter.getRegion());
        setParamState(binding.fEditCity, filter.getRegion() != null, filter.getCity());
        binding.fEditPriceValue.setText(getRangeString(filter.getPriceMin(), filter.getPriceMax(), true));
        binding.fEditYearValue.setText(getRangeString(filter.getManufactureYearMin(), filter.getManufactureYearMax(), false));
        binding.fEditTransmissionValue.setText(filter.getTransmission());
        binding.fEditHullValue.setText(filter.getHull());
        binding.fEditFuelValue.setText(filter.getFuel());
        binding.fEditDisplacementValue.setText(getRangeString(filter.getEngineDisplacementMin(), filter.getEngineDisplacementMax(), false));
        binding.fEditRadiusValue.setText(filter.getRadius() != null ? filter.getRadius().toString() : "");

        binding.fEditCity.setEnabled(filter.getRegion() != null);
        binding.fEditModel.setEnabled(filter.getCarMark() != null);
    }

    /**
     * Produces a "from X to Y" string display value for a range of plain {@link Integer} {@link String} etc. values
     * or currency values.
     *
     * @param from       Value that represents the start of the range.
     * @param to         Value that represents the end of the range.
     * @param isCurrency Format the resulting values as currency?
     * @return "From X to Y" string value to be displayed for user.
     */
    private String getRangeString(Object from, Object to, boolean isCurrency) {
        if (from == null && to == null) return "";

        NumberFormat formatter = NumberFormat.getIntegerInstance();
        String lVal = from == null ? "Все" : isCurrency ? formatter.format(from) : from.toString();
        String rVal = to == null ? "Все" : isCurrency ? formatter.format(to) : to.toString();
        return lVal + " до: " + rVal;
    }

    /**
     * Set fragment's parameter state (enabled/disabled & current value) based on passed parameters.
     *
     * @param view    Parameter layout who's 1-st child is title, 2-nd child is value. Both should be {@link TextView}.
     * @param enabled Should this parameter layout be clickable?
     * @param item    Value instance that implements a {@link ListItem#getName()} method.
     */
    private void setParamState(RelativeLayout view, boolean enabled, ListItem item) {
        view.setEnabled(enabled);
        View title = view.getChildAt(0);
        if (title != null) title.setEnabled(enabled);

        View value = view.getChildAt(1);
        if (enabled && item != null && value instanceof TextView) {
            ((TextView) value).setText(item.getName());
        } else if (value instanceof TextView) {
            ((TextView) value).setText("");
        }
    }


    /**
     * Sets on-click listeners for all parameters of the filter editor.
     */
    private void setupClickListeners() {
        binding.fEditMark.setOnClickListener(v -> showListSearchDialog(MARK, "Марка Авто", "Наименование марки",
                viewModel.observeMarks()));

        binding.fEditModel.setOnClickListener(v -> withFilter(filter -> {
            if (filter.getCarMark() == null) {
                Toast.makeText(this.getContext(), "Укажите марку", Toast.LENGTH_SHORT).show();
                return;
            }
            showListSearchDialog(MODEL, "Модель Авто", "Наименование модели",
                    viewModel.observeModelsByMark(filter.getCarMark()));
        }));

        binding.fEditRegion.setOnClickListener(v ->
                showListSearchDialog(REGION, "Регион", "Наименование региона",
                        viewModel.observeRegions()));

        binding.fEditCity.setOnClickListener(v -> withFilter(filter -> {
            if (filter.getRegion() == null) {
                Toast.makeText(this.getContext(), "Укажите регион", Toast.LENGTH_SHORT).show();
                return;
            }
            showListSearchDialog(CITY, "Город", "Наименование города",
                    viewModel.getCitiesByRegion(filter.getRegion()).toFlowable());
        }));

        binding.fEditPrice.setOnClickListener(v -> withFilter(filter -> showDialog(
                DialogValuesPicker.getCurrencyInstance(PRICE, "Цена", "Выберите интервал стоимости (р.)",
                        filter.getPriceMin(), filter.getPriceMax(), 0, 1000000, 1000))));

        binding.fEditYear.setOnClickListener(v -> withFilter(filter -> showDialog(
                DialogValuesPicker.getNumberInstance(YEAR, "Год выпуска", "Выберите год выпуска ТС",
                        filter.getManufactureYearMin(), filter.getManufactureYearMax(), 1980, 2020, 1))));

        binding.fEditTransmission.setOnClickListener(v -> withFilter(filter -> showDialog(
                DialogRadioPicker.getInstance(TRANSMISSION, "Коробка передач", "Укажите тип трансмиссии",
                        filter.getTransmission(), getResources().getStringArray(R.array.transmission))
        )));

        binding.fEditHull.setOnClickListener(v -> withFilter(filter -> showDialog(
                DialogRadioPicker.getInstance(HULL, "Кузов", "Укажите тип кузова",
                        filter.getHull(), getResources().getStringArray(R.array.hull))
        )));

        binding.fEditFuel.setOnClickListener(v -> withFilter(filter -> showDialog(
                DialogRadioPicker.getInstance(FUEL, "Двигатель", "Укажите тип двигателя",
                        filter.getFuel(), getResources().getStringArray(R.array.fuel))
        )));

        binding.fEditDisplacement.setOnClickListener(v -> withFilter(filter -> showDialog(
                DialogValuesPicker.getValuesInstance(DISPLACEMENT, "Объём двигателя",
                        "Укажите объём двигателя (л)", DISPLACEMENTS,
                        filter.getEngineDisplacementMin(),
                        filter.getEngineDisplacementMax()))));

        binding.fEditRadius.setOnClickListener(v -> withFilter(filter -> showDialog(
                DialogSinglePicker.getInstance(RADIUS, "Радиус поиска", "Укажите радиус поиска (км)",
                        filter.getRadius(), 0, 300, 10)
        )));

        binding.fEditSearchButton.setOnClickListener(v -> {
            viewModel.submitFilter();
        });
    }


    /**
     * Run any operations that need a filter instance null-safe. Null-check would be performed
     * each time and callback executed only if filter was retrieved successfully.
     *
     * @param operation Callback that requires an instance of {@link EditableFilter}
     */
    private void withFilter(SearchViewModel.FilterOperation operation) {
        EditableFilter currentFilter = getCurrentFilter();
        if (currentFilter != null) {
            operation.run(currentFilter);
        }
    }


    /**
     * @return Сurrent filter value from viewmodel's live data. Null if there's no filter.
     * was set yet.
     */
    @Nullable
    private EditableFilter getCurrentFilter() {
        return viewModel.getCurrentFilter().getValue();
    }


    /**
     * Setup result listeners for all dialog fragments that can be opened. I.e.: Save dialog's
     * returned data to {@link EditableFilter} instance.
     */
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
            ArrayList<Integer> values = result.getIntegerArrayList(valKey);
            if (values != null) {
                Log.d(TAG, "setupResultListeners: values: " + values);
                viewModel.setPrice(values.get(0), values.get(1));
            }
        });
        manager.setFragmentResultListener(YEAR, this, (requestKey, result) -> {
            ArrayList<Integer> values = result.getIntegerArrayList(valKey);
            if (values != null) {
                viewModel.setYear(values.get(0), values.get(1));
            }
        });
        manager.setFragmentResultListener(TRANSMISSION, this, (requestKey, result) -> {
            String value = result.getString(DialogRadioPicker.RESULT);
            viewModel.setTransmission(value);
        });
        manager.setFragmentResultListener(HULL, this, (requestKey, result) -> {
            String value = result.getString(DialogRadioPicker.RESULT);
            viewModel.setHull(value);
        });
        manager.setFragmentResultListener(FUEL, this, (requestKey, result) -> {
            String value = result.getString(DialogRadioPicker.RESULT);
            viewModel.setFuel(value);
        });
        manager.setFragmentResultListener(DISPLACEMENT, this, (requestKey, result) -> {
            String[] values = result.getStringArray(valKey);
            if (values != null) {
                viewModel.setDisplacement(values[0], values[1]);
            }
        });
        manager.setFragmentResultListener(RADIUS, this, (requestKey, result) -> {
            Integer value = (Integer) result.getSerializable(DialogSinglePicker.RESULT);
            viewModel.setRadius(value);
        });
    }


    private <T extends ListItem> void showListSearchDialog(String requestCode, String title, String hint, Flowable<List<T>> dataSource) {
        DialogListSearch<T> dialog = DialogListSearch.newInstance(requestCode, title, hint, dataSource);
        showDialog(dialog);
    }


    /**
     * Performs a valid dialog opening: waits for manager's pending transactions, checks if there's
     * already a dialog opened.
     *
     * @param dialog Valid dialog instance that is ready to be opened (showed).
     */
    private void showDialog(DialogFragment dialog) {
        String tag = TAG;
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
