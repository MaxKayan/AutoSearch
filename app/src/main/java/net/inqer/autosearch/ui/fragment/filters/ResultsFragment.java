package net.inqer.autosearch.ui.fragment.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.inqer.autosearch.databinding.FragmentResultsBinding;

import org.jetbrains.annotations.NotNull;

import dagger.android.support.DaggerFragment;

public class ResultsFragment extends DaggerFragment {

//    public ResultsFragment() {
//        // Required empty public constructor
//    }

    private FragmentResultsBinding binding;

    public static ResultsFragment newInstance() { return new ResultsFragment(); }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
