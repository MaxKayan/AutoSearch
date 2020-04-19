package net.inqer.autosearch.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.inqer.autosearch.databinding.DialogBinding;

import dagger.android.support.DaggerDialogFragment;

public class TestDialog extends DaggerDialogFragment {
    public static final String RESULT = "result";
    private static final String TAG = "TestDialog";
    public OnInputSelected onInputSelected;
    private DialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        binding.actionCancel.setOnClickListener(v -> {
            Log.d(TAG, "onViewCreated: closing dialog");
            dismiss();
        });

        binding.actionOk.setOnClickListener(v -> {
            Log.d(TAG, "setupDialog: OK");
            Intent data = new Intent();

            String input = binding.input.getText().toString();

            data.putExtra(RESULT, input);

            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
            dismiss();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: Error: ", e);
        }
    }


    public interface OnInputSelected {
        void sendInput(String input);
    }
}
