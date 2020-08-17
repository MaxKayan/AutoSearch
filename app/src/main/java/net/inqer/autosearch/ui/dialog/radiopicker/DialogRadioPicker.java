package net.inqer.autosearch.ui.dialog.radiopicker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.inqer.autosearch.databinding.DialogRadioPickerBinding;

public class DialogRadioPicker extends DialogFragment {
    public static final String RESULT = "DialogRadioPicker_result";
    private static final String TAG = "DialogRadioPicker";
    private static final String TITLE = "DialogRadioPicker_title";
    private static final String HINT = "DialogRadioPicker_hint";
    private static final String VALUE = "DialogRadioPicker_value";
    private static final String CODE = "DialogRadioPicker_requestCode";
    private static final String VALUES = "DialogRadioPicker_valuesArray";

    DialogRadioPickerBinding binding;
    private String requestKey;

    public DialogRadioPicker() {
    }

    public static DialogRadioPicker getInstance(String requestCode, String title, String hint, String initValue, String[] stringArray) {
        DialogRadioPicker instance = new DialogRadioPicker();
        Bundle args = new Bundle();

        args.putString(CODE, requestCode);
        args.putString(TITLE, title);
        args.putString(HINT, hint);
        args.putString(VALUE, initValue);
        args.putStringArray(VALUES, stringArray);

        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogRadioPickerBinding.inflate(inflater, container, false);
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

        requestKey = args.getString(CODE);
        setupView(view, args);
        restoreSelected(args.getString(VALUE));
        setupListeners();
    }


    private void setupView(View view, Bundle args) {
        String[] values = args.getStringArray(VALUES);
        if (values == null) {
            this.dismiss();
            return;
        }

        binding.textHeader.setText(args.getString(TITLE));
        binding.textHint.setText(args.getString(HINT));

        addNewButton(view, "Все", true);

        for (String value : values) {
            addNewButton(view, value, false);
        }
    }


    private void addNewButton(View view, String text, boolean checked) {
        addNewButton(view, text, checked, View.generateViewId());
    }

    private void addNewButton(View view, String text, boolean checked, int id) {
        RadioButton button = new RadioButton(view.getContext());
        button.setId(id);

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 20);
        button.setLayoutParams(params);
        button.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        button.setText(text);
        button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button.setTextSize(20f);

        button.setChecked(checked);

        binding.radioGroup.addView(button);
    }


    private void finishWithResult(String text) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT, text);
        getParentFragmentManager().setFragmentResult(requestKey, bundle);
        this.dismiss();
    }


    private void restoreSelected(@Nullable String value) {
        if (value == null) return;

        for (int i = 0; i < binding.radioGroup.getChildCount(); i++) {
            View view = binding.radioGroup.getChildAt(i);
            if (view instanceof RadioButton) {
                RadioButton button = (RadioButton) view;
                if (button.getText().equals(value)) {
                    binding.radioGroup.clearCheck();
                    button.setChecked(true);
                }
            }
        }
    }


    private void setupListeners() {
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId <= 0) {
                return;
            }

            RadioButton button = group.findViewById(checkedId);
            if (button != null) {

                finishWithResult(group.indexOfChild(button) == 0 ? null : button.getText().toString());
            }
        });
    }
}
