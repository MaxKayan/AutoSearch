package net.inqer.autosearch.ui.dialog.valuespicker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.inqer.autosearch.BuildConfig;
import net.inqer.autosearch.databinding.DialogValuesPickerBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DialogValuesPicker extends DialogFragment {
    public static final String RESULT = "dialog_values_result";

    public static final String TAG = "DialogValuesPicker";
    private static final String STEP = "dialog_values_step";
    private static final String TITLE = "dialog_header";
    private static final String HINT = "dialog_values_hint";
    private static final String CODE = "dialog_values_request_code";
    private static final String MIN = "dialog_values_min";
    private static final String MAX = "dialog_values_max";

    public static DialogValuesPicker newInstance(String requestCode, int min, int max, int step, String title, String hint) {
        DialogValuesPicker instance = new DialogValuesPicker();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(HINT, hint);
        args.putInt(MIN, min);
        args.putInt(MAX, max);
        args.putInt(STEP, step);
        args.putString(CODE, requestCode);
        instance.setArguments(args);

        return instance;
    }

    private String requestKey;
    private String title;
    private String hint;

    private DialogValuesPickerBinding binding;

    //    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        unpackBundleArgs(args);
        setupView(args);
        setupListeners();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogValuesPickerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * @param lVal Value from the left picker ("From")
     * @param rVal Value from the right picker ("To")
     */
    private void compareValues(int lVal, int rVal) {
        if (lVal > rVal) {
            binding.dialogValR.setValue(lVal);
        }
    }

    private void setupView(Bundle bundle) {
        if (title != null && !title.isEmpty()) binding.dialogValHeader.setText(title);
        if (hint != null && !hint.isEmpty()) binding.dialogValHint.setText(hint);

        int step = bundle.getInt(STEP);
        int min = bundle.getInt(MIN);
        int max = bundle.getInt(MAX) / step;
        if (BuildConfig.DEBUG && min >= max) {
            throw new AssertionError("MIN should always be less than MAX");
        }

//        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
//            @Override
//            public String format(int value) {
//                return String.valueOf(value * step);
//            }
//        };
//        binding.dialogValL.setFormatter(formatter);
//        binding.dialogValR.setFormatter(formatter);

        List<Integer> range = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());

//        String[] stepValues = new String[max - min + 1];
        List<String> stepValues = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            String number = Integer.toString(i * step);
            stepValues.add(number);
//            stepValues[i] = number;
        }


        Log.d(TAG, "setupView: stepValues: " + stepValues.toString());
//        Log.d(TAG, "setupView: range: " + range.toString());

        binding.dialogValL.setDisplayedValues(stepValues.toArray(new String[0]));
        binding.dialogValR.setDisplayedValues(stepValues.toArray(new String[0]));

        binding.dialogValAccept.setOnClickListener(v -> {
            Log.d(TAG, "setupView: " + binding.dialogValL.getValue() * step);
            Log.d(TAG, "setupView: " + binding.dialogValR.getValue() * step);
//            Log.d(TAG, "setupView: stepValues: "+stepValues.length);
        });

        binding.dialogValL.setMinValue(min);
        binding.dialogValR.setMinValue(min);
        binding.dialogValL.setMaxValue(max);
        binding.dialogValR.setMaxValue(max);
    }


    private void unpackBundleArgs(@Nullable Bundle bundle) {
        if (bundle != null) {
            requestKey = bundle.getString(CODE);
            title = bundle.getString(TITLE);
            hint = bundle.getString(HINT);
        } else {
            Log.w(TAG, "unpackBundleArgs: args bundle is null!");
        }
    }

    private void setupListeners() {
        binding.dialogValL.setOnValueChangedListener((picker, oldVal, newVal) -> {
            Log.d(TAG, "setupListeners: OnValueChangedListener: " +
                    " " + oldVal + " " + newVal);

            if (newVal > binding.dialogValR.getValue()) {
                binding.dialogValR.setValue(newVal);
            }
        });
        binding.dialogValR.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (newVal < binding.dialogValL.getValue()) {
                binding.dialogValL.setValue(newVal);
            }
        });

//        binding.dialogValCancel.setOnClickListener(v -> dismiss());
    }


    public static enum TYPE {}


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDetach() {
        super.onDetach();
//        disposableBag.clear();
    }


    private void finishWithResult(Float result) {
        if (result != null) {
            Bundle bundle = new Bundle();
//            bundle.putParcelable(RESULT, result);
            getParentFragmentManager().setFragmentResult(requestKey, bundle);
            this.dismiss();
        }
    }

}
