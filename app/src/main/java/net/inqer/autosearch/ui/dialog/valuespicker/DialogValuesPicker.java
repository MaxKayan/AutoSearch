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

import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    private static final String VALUES = "dialog_values_array";
    private PickerType type;
    private NumberFormat formatter;
    private String[] displayedValues;
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
    public static DialogValuesPicker getInstance(String requestCode,
                                                 String title, String hint,
                                                 PickerType type,
                                                 Integer from, Integer to, int min, int max, int step) {
        DialogValuesPicker instance = new DialogValuesPicker();
        Bundle args = getBaseBundle(title, hint, requestCode);

        from = from != null ? from : 0;
        to = to != null ? to : 0;

        if (BuildConfig.DEBUG && step <= 0) {
            throw new AssertionError("Step must be greater than 0. Got: " + step);
        }
        if (BuildConfig.DEBUG && min >= max) {
            throw new AssertionError("Min argument should be less than max. Got: " + min + " -> " + max);
        }

        args.putSerializable(TYPE, type);
        args.putInt(STEP, step);
        args.putInt(FROM, from);
        args.putInt(TO, to);
        args.putInt(MIN, min);
        args.putInt(MAX, max);

        instance.setArguments(args);

        return instance;
    }


    public static DialogValuesPicker getCurrencyInstance(String requestCode, String title, String hint,
                                                         Integer from, Integer to, int min, int max, int step) {
        return getInstance(requestCode, title, hint, PickerType.CURRENCY, from, to, min, max, step);
    }


    public static DialogValuesPicker getNumberInstance(String requestCode, String title, String hint,
                                                       Integer from, Integer to, int min, int max, int step) {
        return getInstance(requestCode, title, hint, PickerType.NORMAL, from, to, min, max, step);
    }


    public static DialogValuesPicker getValuesInstance(String requestCode, String title, String hint,
                                                       String[] values, String from, String to) {
        DialogValuesPicker instance = new DialogValuesPicker();
        Bundle args = getBaseBundle(title, hint, requestCode);

        args.putSerializable(TYPE, PickerType.ENGINE);
        args.putStringArray(VALUES, values);
        instance.setArguments(args);
        return instance;
    }


    /**
     * Create basic bundle with arguments that are present on any dialog type.
     *
     * @return Base Bundle instance with common arguments
     */
    private static Bundle getBaseBundle(String title, String hint, String code) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(HINT, hint);
        args.putString(CODE, code);
        return args;
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
        // We can't do anything if arguments are null
        if (args == null) {
            Log.e(TAG, "onViewCreated: Null arguments, closing dialog.");
            this.dismiss();
            return;
        }

        unpackBundleArgs(args); // Extract bundle arguments and write them to class private fields
        setupView();            // Apply data to dialog's UI
        setupListeners();       // Setup UI click/action listeners
    }


    /**
     * Gets needed arguments from the bundle and writes them to class private fields.
     *
     * @param bundle Arguments passed upon fragment's creation.
     */
    private void unpackBundleArgs(@NonNull Bundle bundle) {
        Objects.requireNonNull(bundle);

        // Get type from bundle, check if it's null.
        type = (PickerType) bundle.getSerializable(TYPE);

        // Common arguments for all types
        requestKey = bundle.getString(CODE);
        title = bundle.getString(TITLE);
        hint = bundle.getString(HINT);

        // Specific arguments depending on current type
        switch (type) {
            case CURRENCY:
            case NORMAL:
                step = bundle.getInt(STEP);  // Must be >= 1
                from = bundle.getInt(FROM);
                to = bundle.getInt(TO);
                min = bundle.getInt(MIN);
                max = bundle.getInt(MAX);
                break;
            case ENGINE:
                displayedValues = bundle.getStringArray(VALUES);
                Log.d(TAG, "unpackBundleArgs: values: " + Arrays.toString(displayedValues));
                break;
        }
    }


    /**
     * Applies data to the view both from the saved instance's bundle and class private fields.
     */
    private void setupView() {
        if (title != null && !title.isEmpty()) binding.dialogValHeader.setText(title);
        if (hint != null && !hint.isEmpty()) binding.dialogValHint.setText(hint);

        switch (type) {
            case NORMAL:
                displayedValues = this.getDisplayedValues(null, min, max, step);
                break;
            case CURRENCY:
                formatter = NumberFormat.getIntegerInstance();
                displayedValues = this.getDisplayedValues(formatter, min, max, step);
                break;
            case ENGINE:
                break;
            default:
                throw new InvalidParameterException("Unexpected picker type - " + type);
        }

        // Set min and max acceptable values for both pickers
        binding.dialogValL.setMinValue(0);
        binding.dialogValR.setMinValue(0);
        binding.dialogValL.setMaxValue(displayedValues.length - 1);
        binding.dialogValR.setMaxValue(displayedValues.length - 1);

        binding.dialogValL.setDisplayedValues(displayedValues);
        binding.dialogValR.setDisplayedValues(displayedValues);
    }


    /**
     * Get values for both number pickers in case of using integers
     *
     * @return Array of strings that represent available values
     */
    private String[] getDisplayedValues(NumberFormat formatter, int min, int max, int step) {
        List<String> values = new ArrayList<>();
        values.add("Все");

        if (formatter != null) {
            for (int i = min; i <= max; i += step) {
                String value = formatter.format(i);
                values.add(value);
            }
        } else {
            for (int i = min; i <= max; i += step) {
                values.add(Integer.toString(i));
            }
        }

        // Check if first actual value is 0, remove it if true.
        if (values.get(1).equals("0")) {
            values.remove(1);
        }

        return values.toArray(new String[0]);
    }


    /**
     * Set picker action listeners.
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

        binding.dialogValAccept.setOnClickListener(v -> {
            String lVal = displayedValues[binding.dialogValL.getValue()];
            String rVal = displayedValues[binding.dialogValR.getValue()];

            switch (type) {
                case CURRENCY:
                    try {
                        Number lResult = formatter.parse(lVal);
                        Number rResult = formatter.parse(rVal);
                        if (lResult != null && rResult != null) {
                            finishWithResult(lResult.intValue(), rResult.intValue());
                        }
                    } catch (ParseException e) {
                        Log.e(TAG, "dialogValAccept: failed to parse", e);
                    }
                    break;

                case NORMAL:
                    finishWithResult(Integer.parseInt(lVal), Integer.parseInt(rVal));
                    break;

                case ENGINE:
                    finishWithResult(lVal, rVal);
                    break;
            }
        });
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

    // TODO: Check if there's a way to not duplicate method for different types
    private void finishWithResult(String from, String to) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(RESULT, new String[]{from, to});
        getParentFragmentManager().setFragmentResult(requestKey, bundle);
        this.dismiss();
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


    public enum PickerType {
        NORMAL,  // Plain integer values
        CURRENCY, // Integer values formatted as currency
        ENGINE,
        SINGLE
    }

}
