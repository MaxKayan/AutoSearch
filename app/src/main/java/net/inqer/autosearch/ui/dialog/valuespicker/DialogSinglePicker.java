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
import java.util.List;

public class DialogSinglePicker extends DialogFragment {
    public static final String RESULT = "DialogSinglePicker_result";
    private static final String TAG = "DialogSinglePicker";
    private static final String STEP = "dialog_single_step";
    private static final String TITLE = "dialog_single_header";
    private static final String HINT = "dialog_single_hint";
    private static final String CODE = "dialog_single_request_code";
    private static final String INIT_VAL = "dialog_single_initial_value";
    private static final String POS = "dialog_single_picker_position";
    private static final String MIN = "dialog_single_min";
    private static final String MAX = "dialog_single_max";
    DialogSinglePickerBinding binding;

    private String requestKey;
    private int pos;
    private int min;
    private int max;
    private int step;
    private List<String> displayedValues;

    public DialogSinglePicker() {
    }

    public static DialogSinglePicker getInstance(String requestCode, String title, String hint, Integer initialVal, int min, int max, int step) {
        DialogSinglePicker instance = new DialogSinglePicker();
        Bundle args = new Bundle();
        args.putString(CODE, requestCode);
        args.putString(TITLE, title);
        args.putString(HINT, hint);

        args.putSerializable(INIT_VAL, initialVal);

        args.putInt(MIN, min);
        args.putInt(MAX, max);
        args.putInt(STEP, step);

        instance.setArguments(args);
        return instance;
    }


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
        setupView(args);
    }


    private void unpackArguments(Bundle args) {
        requestKey = args.getString(CODE);
        binding.textHeader.setText(args.getString(TITLE));
        binding.textHint.setText(args.getString(HINT));
        min = args.getInt(MIN);
        max = args.getInt(MAX);
        step = args.getInt(STEP);
    }


    private void setupView(Bundle args) {
        displayedValues = getDisplayedValues();
        binding.numberPicker.setDisplayedValues(displayedValues.toArray(new String[0]));
        binding.numberPicker.setMinValue(0);
        binding.numberPicker.setMaxValue(displayedValues.size() - 1);

        Integer initValue = (Integer) args.getSerializable(INIT_VAL);
        pos = initValue != null ? displayedValues.indexOf(initValue.toString()) : 0;
        binding.numberPicker.setValue(pos);

        binding.numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            pos = newVal;
        });

        binding.buttonAccept.setOnClickListener(v -> {
            int pos = binding.numberPicker.getValue();
            Integer value = pos > 0 ? Integer.valueOf(displayedValues.get(binding.numberPicker.getValue())) : null;
            finishWithResult(value);
        });
    }


    private List<String> getDisplayedValues() {
        List<String> values = new ArrayList<>();
        values.add("Любой");
        for (int i = min; i <= max; i += step) {
            values.add(Integer.toString(i));
        }
        return values;
    }


    private void finishWithResult(Integer value) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RESULT, value);
        getParentFragmentManager().setFragmentResult(requestKey, bundle);
        this.dismiss();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(POS, binding.numberPicker.getValue());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            binding.numberPicker.setValue(savedInstanceState.getInt(POS));
        }
    }
}
