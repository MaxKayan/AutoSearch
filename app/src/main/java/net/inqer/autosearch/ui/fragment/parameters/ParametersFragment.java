package net.inqer.autosearch.ui.fragment.parameters;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.databinding.FragmentParametersBinding;

public class ParametersFragment extends Fragment {
    private static final String TAG = "ParametersFragment";

    private ParametersViewModel mViewModel;

    private FragmentParametersBinding binding;

    public static ParametersFragment newInstance() {
        return new ParametersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentParametersBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_parameters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        binding = FragmentParametersBinding.inflate(getLayoutInflater());
        mViewModel = new ViewModelProvider(this).get(ParametersViewModel.class);

        mViewModel.updateAccountData();
        mViewModel.getAccountProperties().observe(getViewLifecycleOwner(), this::bindProfileData);

        binding.paramButtonSignOut.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign Out button pressed", Toast.LENGTH_SHORT).show();
        });
        binding.paramButtonSignOut.setOnLongClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign Out LONG click pressed", Toast.LENGTH_SHORT).show();
            return true;
        });
    }


    private void bindProfileData(AccountProperties properties) {
        Log.d(TAG, "bindProfileData: Called.");
        Log.d(TAG, "bindProfileData: "+properties.toString());
        Log.d(TAG, "bindProfileData: "+binding.paramUsername.getText().toString());
        binding.paramUsername.setText(properties.getUsername());
        binding.paramEmail.setText(properties.getEmail());
        binding.paramLastLoginValue.setText(properties.getLast_login());
        binding.paramDateJoinedValue.setText(properties.getDate_joined());
    }
}
