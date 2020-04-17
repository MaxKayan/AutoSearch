package net.inqer.autosearch.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
    private static final String TAG = "TestDialog";

    private DialogBinding binding;

    public interface OnInputSelected {
        void sendInput(String input);
    }

    public OnInputSelected onInputSelected;

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
            getDialog().dismiss();
        });

        binding.actionOk.setOnClickListener(v -> {
            Log.d(TAG, "setupDialog: OK");

            String input = binding.input.getText().toString();
            if (!input.equals("")) {
                onInputSelected.sendInput(input);
            }
            getDialog().dismiss();
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        Log.d(TAG, "setupDialog: called");

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
}
