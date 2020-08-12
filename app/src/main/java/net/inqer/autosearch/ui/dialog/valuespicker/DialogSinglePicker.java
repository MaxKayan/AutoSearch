package net.inqer.autosearch.ui.dialog.valuespicker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.inqer.autosearch.databinding.DialogSinglePickerBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogSinglePicker extends DialogFragment {
    private static final String TAG = "DialogSinglePicker";
    private static final String RESULT = "DialogSinglePicker_result";
    private static final String STEP = "dialog_values_step";
    private static final String TITLE = "dialog_header";
    private static final String HINT = "dialog_values_hint";
    private static final String CODE = "dialog_values_request_code";
    private static final String VALUE = "dialog_values_value";
    private static final String MIN = "dialog_values_min";
    private static final String MAX = "dialog_values_max";
    DialogSinglePickerBinding binding;

    private String requestKey;
    private int value;
    private int min;
    private int max;
    private int step;
    private String[] displayedValues;

    public DialogSinglePicker() {
    }

    public static DialogSinglePicker getInstance(String requestCode, String title, String hint, Integer initialVal, int min, int max, int step) {
        DialogSinglePicker instance = new DialogSinglePicker();
        Bundle args = new Bundle();
        args.putString(CODE, requestCode);
        args.putString(TITLE, title);
        args.putString(HINT, hint);

        initialVal = initialVal != null ? initialVal : 0;
        args.putInt(VALUE, initialVal);

        args.putInt(MIN, min);
        args.putInt(MAX, max);
        args.putInt(STEP, step);

        instance.setArguments(args);
        return instance;
    }

    ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSinglePickerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        // We can't do anything if arguments are null
        if (args == null) {
            Log.e(TAG, "onViewCreated: Null arguments, closing dialog.");
            this.dismiss();
            return;
        }

        unpackArguments(args);
        setupView();
    }


    private void unpackArguments(Bundle args) {
        requestKey = args.getString(CODE);
        binding.textHeader.setText(args.getString(TITLE));
        binding.textHint.setText(args.getString(HINT));
        value = args.getInt(VALUE);
        min = args.getInt(MIN);
        max = args.getInt(MAX);
        step = args.getInt(STEP);
    }


    private void setupView() {
        displayedValues = getDisplayedValues();
        binding.numberPicker.setDisplayedValues(displayedValues);
        binding.numberPicker.setMinValue(0);
        binding.numberPicker.setMaxValue(displayedValues.length - 1);

        binding.numberPicker.setValue(
                Arrays.binarySearch(displayedValues, Integer.toString(value))
        );

        binding.buttonAccept.setOnClickListener(v -> {
            int pos = binding.numberPicker.getValue();
            Integer value = pos > 0 ? Integer.valueOf(displayedValues[binding.numberPicker.getValue()]) : null;
            finishWithResult(value);
        });
    }


    private String[] getDisplayedValues() {
        List<String> values = new ArrayList<>();
        values.add("Любой");
        for (int i = min; i <= max; i += step) {
            values.add(Integer.toString(i));
        }
        return values.toArray(new String[0]);
    }


    private void finishWithResult(Integer value) {
        Bundle bundle = new Bundle();
        bundle.putInt(RESULT, value);
        getParentFragmentManager().setFragmentResult(requestKey, bundle);
        this.dismiss();
    }
}
