package net.inqer.autosearch.ui.fragment.parameters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.data.model.AccountProperties;
import net.inqer.autosearch.databinding.FragmentParametersBinding;
import net.inqer.autosearch.databinding.FragmentParametersShimmerBinding;
import net.inqer.autosearch.ui.MainActivity;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ParametersFragment extends DaggerFragment {
    private static final String TAG = "ParametersFragment";

    @Inject
    ViewModelProviderFactory providerFactory;

    private ParametersViewModel viewModel;

    private FragmentParametersBinding binding;
    private FragmentParametersShimmerBinding shimmerBinding;

//    public static ParametersFragment newInstance() {
//        return new ParametersFragment();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentParametersBinding.inflate(inflater, container, false);
        shimmerBinding = FragmentParametersShimmerBinding.bind(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, providerFactory).get(ParametersViewModel.class);

        viewModel.updateAccountData();


        binding.paramButtonSignOut.setOnClickListener(v -> {
            signOut();
        });
        binding.paramButtonSignOut.setOnLongClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign Out LONG click pressed", Toast.LENGTH_SHORT).show();
            return true;
        });

        subscribeListeners();
    }

    private void subscribeListeners() {
        viewModel.getAccountProperties().observe(getViewLifecycleOwner(), this::bindProfileData);
    }


    private void bindProfileData(@NotNull AccountProperties properties) {
        Log.d(TAG, "bindProfileData: Called.");
        Log.d(TAG, "bindProfileData: " + properties.toString());
        Log.d(TAG, "bindProfileData: " + binding.paramUsername.getText().toString());
        binding.paramUsername.setText(properties.getUsername());
        binding.paramEmail.setText(properties.getEmail());
        binding.paramLastLoginValue.setText(properties.getLast_login());
        binding.paramDateJoinedValue.setText(properties.getDate_joined());

        shimmerBinding.parametersShimmerLayout.setVisibility(View.GONE);
    }

    private void signOut() {
        if (getActivity() instanceof MainActivity) {
            Toast.makeText(getContext(), "Signing Out...", Toast.LENGTH_SHORT).show();
            ( (MainActivity) getActivity() ).signOut();
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: start Shimmer");
        shimmerBinding.parametersShimmerLayout.startShimmer();
        super.onResume();
    }

    @Override
    public void onPause() {
        shimmerBinding.parametersShimmerLayout.stopShimmer();
        super.onPause();
    }
}
