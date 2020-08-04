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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DialogValuesPicker extends DialogFragment {
    public static final String RESULT = "dialog_values_result";
    // Bundle codes
    public static final String TAG = "DialogValuesPicker";
    private static final String TYPE = "dialog_values_type";
    private static final String STEP = "dialog_values_step";
    private static final String TITLE = "dialog_header";
    private static final String HINT = "dialog_values_hint";
    private static final String CODE = "dialog_values_request_code";
    private static final String FROM = "dialog_values_from";
    private static final String TO = "dialog_values_to";
    private static final String MIN = "dialog_values_min";
    private static final String MAX = "dialog_values_max";
    private PickerType type;
    private String requestKey;
    private String title;   // Dialog header to be displayed TODO: Can these be local variables?
    private String hint;    // Description string to be displayed
    private int from;       // Current "left" value
    private int to;         // Current "right" value
    private int min;         // Minimum acceptable value for both pickers
    private int max;         // Maximum acceptable value for both pickers
    private int step;       // Value step, doesn't change through the instance life.
    private DialogValuesPickerBinding binding;  // View binding

    public DialogValuesPicker() {
    }

    /**
     * @param requestCode Dialog request code to be used upon saving fragment's result
     * @param from        Initial "from" value at the first picker
     * @param to          Initial "to" value at the second picker
     * @param min         Minimal acceptable value for both pickers
     * @param max         Maximum acceptable value for both pickers
     * @param step        Step of the values for both pickers
     * @param title       Title at the header of the dialog
     * @param hint        Description under the header (title)
     * @return Valid dialog instance with specified arguments put as Bundle
     */
    public static DialogValuesPicker newInstance(String requestCode,
                                                 PickerType type,
                                                 int from, int to, int min, int max, int step, String title, String hint) {
        DialogValuesPicker instance = new DialogValuesPicker();
        Bundle args = new Bundle();
        args.putSerializable(TYPE, type);
        args.putString(TITLE, title);
        args.putString(HINT, hint);
        args.putInt(FROM, from);
        args.putInt(TO, to);
        args.putInt(MIN, min);
        args.putInt(MAX, max);
        args.putInt(STEP, step);
        args.putString(CODE, requestCode);
        instance.setArguments(args);

        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogValuesPickerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        unpackBundleArgs(args);
        setupView(args);
        setupListeners();

        binding.dialogValL.setValue(from);
        binding.dialogValR.setValue(to);
    }

    /**
     * Gets needed arguments from the bundle and writes them to class private fields.
     *
     * @param bundle Arguments passed upon fragment's creation.
     */
    private void unpackBundleArgs(@Nullable Bundle bundle) {
        if (bundle != null) {
            requestKey = bundle.getString(CODE);
            title = bundle.getString(TITLE);
            hint = bundle.getString(HINT);
            step = bundle.getInt(STEP);  // Must be > 0
            from = bundle.getInt(FROM) / step;
            to = bundle.getInt(TO) / step;
            min = bundle.getInt(MIN);
            max = bundle.getInt(MAX) / step;
        } else {
            Log.w(TAG, "unpackBundleArgs: args bundle is null!");
        }
    }

    /**
     * Get values for both number pickers in case of using integers
     *
     * @return Array of strings that represent available values
     */
    private String[] getDisplayValues(int min, int max, int step) {
        List<String> values = new ArrayList<>();
        NumberFormat formatter = NumberFormat.getIntegerInstance();
        values.add("Все");
        for (int i = min == 0 ? min + 1 : min; i <= max; i++) {
//            String number = Integer.toString(i * step);
            String number = formatter.format(i * step);
            values.add(number);
        }

        return values.toArray(new String[0]);
    }

    /**
     * Get values for both number pickers in case of using float
     *
     * @return Array of strings that represent available values
     */
    private String[] getDisplayValues(float min, float max, float step) {
        List<String> values = new ArrayList<>();
        values.add("Все");
        for (float i = min == 0 ? min + 1 : min; i <= max; i++) {
            String number = Float.toString(i * step);
            values.add(number);
        }

        return values.toArray(new String[0]);
    }

    /**
     * Applies data to the view both from the saved instance's bundle and class private fields.
     *
     * @param bundle Arguments passed upon fragment's creation.
     */
    private void setupView(Bundle bundle) {
        if (title != null && !title.isEmpty()) binding.dialogValHeader.setText(title);
        if (hint != null && !hint.isEmpty()) binding.dialogValHint.setText(hint);

        binding.dialogValL.setValue(from);
        binding.dialogValR.setValue(to);

        if (BuildConfig.DEBUG && min >= max) {
            throw new AssertionError("MIN should always be less than MAX");
        }

        String[] displayValues = getDisplayValues(min, max, step);
        binding.dialogValL.setDisplayedValues(displayValues);
        binding.dialogValR.setDisplayedValues(displayValues);

        binding.dialogValAccept.setOnClickListener(v -> {
            finishWithResult(binding.dialogValL.getValue() * step, binding.dialogValR.getValue() * step);
        });

        // Set min and max acceptable values for both pickers
        binding.dialogValL.setMinValue(min);
        binding.dialogValR.setMinValue(min);
        binding.dialogValL.setMaxValue(max);
        binding.dialogValR.setMaxValue(max);
    }

    /**
     * Set view action listeners.
     */
    private void setupListeners() {
        binding.dialogValL.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int rightValue = binding.dialogValR.getValue();
            if (newVal > rightValue && newVal != 0 && rightValue != 0) {
                binding.dialogValR.setValue(newVal);
                to = newVal;
            }
            from = newVal;
        });

        binding.dialogValR.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int leftValue = binding.dialogValL.getValue();
            if (newVal < leftValue && newVal != 0 && leftValue != 0) {
                binding.dialogValL.setValue(newVal);
                from = newVal;
            }
            to = newVal;
        });
    }

    /**
     * @param outState Bundle to be saved. Will be restored later.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(FROM, from);
        outState.putInt(TO, to);
        super.onSaveInstanceState(outState);
    }

    /**
     * @param savedInstanceState Bundle with saved arguments
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            from = savedInstanceState.getInt(FROM);
            to = savedInstanceState.getInt(TO);
            binding.dialogValL.setValue(from);
            binding.dialogValR.setValue(to);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    /**
     * Packs 2 final values into bundle as an integer array of length 2. Sets
     *
     * @param from Final "from" value from the left picker
     * @param to   Final "to" value from the right picker
     */
    private void finishWithResult(int from, int to) {
        Bundle bundle = new Bundle();
        bundle.putIntArray(RESULT, new int[]{from, to});
        getParentFragmentManager().setFragmentResult(requestKey, bundle);
        this.dismiss();
    }


    public enum PickerType {
        NORMAL,  // Plain integer values
        CURRENCY, // Integer values formatted as currency
        SINGLE
    }

}
