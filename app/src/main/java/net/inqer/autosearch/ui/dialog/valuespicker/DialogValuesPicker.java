package net.inqer.autosearch.ui.dialog.valuespicker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.inqer.autosearch.databinding.DialogValuesPickerBinding;

public class DialogValuesPicker extends DialogFragment {
    public static final String TAG = "DialogValuesPicker";

    private static final String TITLE = "dialog_header";
    private static final String HINT = "dialog_values_hint";
    private static final String CODE = "dialog_values_request_code";
    private static final String MIN = "dialog_values_min";
    private static final String MAX = "dialog_values_max";
    public static final String RESULT = "dialog_values_result";

    private String requestKey;
    private String title;
    private String hint;

    private DialogValuesPickerBinding binding;

    public static DialogValuesPicker newInstance(String requestCode, int min, int max, String title, String hint) {
        DialogValuesPicker instance = new DialogValuesPicker();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(HINT, hint);
        args.putInt(MIN, min);
        args.putInt(MAX, max);
        args.putString(CODE, requestCode);
        instance.setArguments(args);

        return instance;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogValuesPickerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unpackBundleArgs(getArguments());
        setupView();
    }


    private void unpackBundleArgs(@Nullable Bundle bundle) {
        if (bundle != null) {
            requestKey = bundle.getString(CODE);
            title = bundle.getString(TITLE);
            hint = bundle.getString(HINT);
            binding.dialogValL.setMinValue(bundle.getInt(MIN));
            binding.dialogValR.setMinValue(bundle.getInt(MIN));
            binding.dialogValL.setMinValue(bundle.getInt(MAX));
            binding.dialogValR.setMinValue(bundle.getInt(MAX));
        } else {
            Log.w(TAG, "unpackBundleArgs: args bundle is null!");
        }
    }


    private void setupView() {
        if (title != null && !title.isEmpty()) binding.dialogValHeader.setText(title);
        if (hint != null && !hint.isEmpty()) binding.dialogValLHelpText.setHint(hint);

        binding.dialogValAccept.setOnClickListener(v -> {
            Log.d(TAG, "setupView: " + binding.dialogValL.getValue());
            Log.d(TAG, "setupView: " + binding.dialogValR.getValue());
        });
    }

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
