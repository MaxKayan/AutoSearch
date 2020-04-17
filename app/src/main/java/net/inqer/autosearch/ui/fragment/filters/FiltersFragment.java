package net.inqer.autosearch.ui.fragment.filters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import net.inqer.autosearch.R;
import net.inqer.autosearch.databinding.FragmentFiltersBinding;
import net.inqer.autosearch.util.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FiltersFragment extends DaggerFragment {
    private static final String TAG = "FiltersFragment";

    @Inject
    FiltersAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;
    private FiltersViewModel viewModel;

    private FragmentFiltersBinding binding;

    public static FiltersFragment newInstance() {
        return new FiltersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFiltersBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, providerFactory).get(FiltersViewModel.class);

//        showProgressBar(true);
        setupRecyclerView(view);
        subscribeObservers();
        setupFab(view);
    }

    private void subscribeObservers() {
//        viewModel.observeFilterData().observe(getViewLifecycleOwner(),
//                filters -> adapter.submitList(filters));

//        viewModel.observeFilterEvents().observe(getViewLifecycleOwner(), event -> {
//            switch (event.status) {
//                case LOADING:
//                    showProgressBar(true);
//                    break;
//                case SUCCESS:
//                    if (event.data != null) {
//                        Log.d(TAG, "subscribeObservers: submitting: "+event.data.size());
//                        adapter.submitList(event.data);
//                    }
//                    showProgressBar(false);
//                    break;
//                case ERROR:
//                    showError(event.message);
//                    viewModel.resetFilterObserver();
//                    showProgressBar(false);
//                    break;
//            }
//        });

        viewModel.observeFilters().observe(getViewLifecycleOwner(), filters -> {
            adapter.submitList(filters);
            if (filters!=null && !filters.isEmpty()) {
                Log.i(TAG, "subscribeObservers: observeFilters size: "+filters.size());

            } else Log.w(TAG, "subscribeObservers: observeFilters: invalid data ");
            binding.filtersSwipeLayout.setRefreshing(false);
        });
    }


    private void setupRecyclerView(View view) {
        binding.filtersRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.filtersRecyclerview.setHasFixedSize(true);
        binding.filtersRecyclerview.setAdapter(adapter);

        binding.filtersSwipeLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "setOnRefreshListener: called");
            viewModel.refreshData();
        });

        FiltersSwipeCallback filtersSwipeCallback = new FiltersSwipeCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d(TAG, "onSwiped: swiped");
//                viewModel.deleteFilter(adapter.getFilterAt(viewHolder.getAdapterPosition()));
                Disposable delSub = viewModel.deleteFilterRx(adapter.getFilterAt(viewHolder.getAdapterPosition()))
//                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Snackbar.make(view, "Filter deleted!", Snackbar.LENGTH_LONG).show();
                        }, throwable -> {
                            Log.e(TAG, "onSwiped: Error", throwable);
                        });

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(filtersSwipeCallback);
        itemTouchHelper.attachToRecyclerView(binding.filtersRecyclerview);
    }


    private void setupFab(View view) {
        binding.filtersFab.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.navigation_fast_search);
        });
        binding.filtersFab.setOnLongClickListener(v -> {
            viewModel.deleteFilters();
            return true;
        });
    }


    private void showProgressBar(boolean show) {
        if (show) {
            binding.filtersProgressbar.setVisibility(View.VISIBLE);
        } else {
            binding.filtersProgressbar.setVisibility(View.GONE);
        }
    }

    private void showError(String message) {
        Log.w(TAG, "showError: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
//        binding.filtersShimmerViewContainer.startShimmer();
        super.onResume();
    }


    @Override
    public void onPause() {
//        binding.filtersShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
