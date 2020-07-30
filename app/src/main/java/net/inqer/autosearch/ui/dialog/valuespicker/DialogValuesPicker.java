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

public class DialogValuesPicker extends DialogFragment {
    public static final String RESULT = "dialog_values_result";

    public static final String TAG = "DialogValuesPicker";
    private static final String STEP = "dialog_values_step";
    private static final String TITLE = "dialog_header";
    private static final String HINT = "dialog_values_hint";
    private static final String CODE = "dialog_values_request_code";
    private static final String FROM = "dialog_values_from";
    private static final String TO = "dialog_values_to";
    private static final String MIN = "dialog_values_min";
    private static final String MAX = "dialog_values_max";
    private String requestKey;
    private String title;
    private String hint;
    private DialogValuesPickerBinding binding;

    public DialogValuesPicker() {
    }

    public static DialogValuesPicker newInstance(String requestCode, int from, int to, int min, int max, int step, String title, String hint) {
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


    private void setupView(Bundle bundle) {
        if (title != null && !title.isEmpty()) binding.dialogValHeader.setText(title);
        if (hint != null && !hint.isEmpty()) binding.dialogValHint.setText(hint);

        int step = bundle.getInt(STEP);
        int min = bundle.getInt(MIN);
        int max = bundle.getInt(MAX) / step;
        if (BuildConfig.DEBUG && min >= max) {
            throw new AssertionError("MIN should always be less than MAX");
        }


        List<String> stepValues = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            String number = Integer.toString(i * step);
            stepValues.add(number);
        }


        Log.d(TAG, "setupView: stepValues: " + stepValues.toString());

        binding.dialogValL.setDisplayedValues(stepValues.toArray(new String[0]));
        binding.dialogValR.setDisplayedValues(stepValues.toArray(new String[0]));

        binding.dialogValAccept.setOnClickListener(v -> {
            Log.d(TAG, "setupView: dialogValAccept: " + binding.dialogValL.getValue() + " " + binding.dialogValR.getValue());
            finishWithResult(binding.dialogValL.getValue() * step, binding.dialogValR.getValue() * step);
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
//            Log.d(TAG, "setupListeners: OnValueChangedListener: " +
//                    " " + oldVal + " " + newVal);

            if (newVal > binding.dialogValR.getValue()) {
                binding.dialogValR.setValue(newVal);
            }
        });
        binding.dialogValR.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (newVal < binding.dialogValL.getValue()) {
                binding.dialogValL.setValue(newVal);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void finishWithResult(int from, int to) {
        Bundle bundle = new Bundle();
        bundle.putIntArray(RESULT, new int[]{from, to});
        getParentFragmentManager().setFragmentResult(requestKey, bundle);
        Log.d(TAG, "finishWithResult: " + bundle.toString());
        this.dismiss();
    }


    public enum TYPE {NORMAL, CURRENCY, SINGLE}

}
