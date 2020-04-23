package net.inqer.autosearch.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import net.inqer.autosearch.data.model.ListItem;
import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.databinding.DialogListSearchBinding;
import net.inqer.autosearch.ui.dialog.adapter.DialogListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class DialogListSearch<T extends ListItem & Parcelable> extends DialogFragment {
    public static final String REG_ID = "region_slug";
    private static final String TAG = "TestDialog";
    private static final String TITLE = "dialog_header";
    private static final String HINT = "dialog_search_hint";
    private static final String LIST = "dialog_search_hint";

    final Comparator<Region> alphabeticalComparator = new Comparator<Region>() {
        @Override
        public int compare(Region a, Region b) {
            return a.getName().compareTo(b.getName());
        }
    };


    //    @Inject
//    MainApi api;
//    @Inject
//    RegionsRepository repository;
    private DialogListSearchViewModel<T> viewModel;
    private DialogListSearchBinding binding;
    private DialogListAdapter<T> adapter;
    private CompositeDisposable disposableBag = new CompositeDisposable();

    private String title;
    private String hint;
    private List<T> dataList = new ArrayList<>();
    private Flowable<List<T>> dataSource;

    private Intent resultData = new Intent();


    public static <T extends ListItem> DialogListSearch<T> newInstance(String title, String hint, Flowable<List<T>> observer) {
        DialogListSearch<T> instance = new DialogListSearch<>();
        instance.dataSource = observer;
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(HINT, hint);

        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogListSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        DialogListSearchViewModelFactory<T> viewModelFactory = new DialogListSearchViewModelFactory<>(title, dataSource);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DialogListSearchViewModel.class);

//        unpackBundleArgs(savedInstanceState);
        unpackBundleArgs(getArguments());
        setupView();
        setupSearchInput();
        setupRecyclerView();
        getData();
    }


    private void unpackBundleArgs(@Nullable Bundle bundle) {
        if (bundle != null) {
            title = bundle.getString(TITLE);
            hint = bundle.getString(HINT);
            dataList = bundle.getParcelableArrayList(LIST);
            Log.d(TAG, "unpackBundleArgs: " + title + '\n' +
                    "data source : " + dataSource + '\n' +
                    "data list: " + dataList);
        } else {
            Log.w(TAG, "unpackBundleArgs: args bundle is null!");
        }
    }

    private void setupView() {
        if (title != null && !title.isEmpty()) binding.dialogLocHeader.setText(title);
        if (hint != null && !hint.isEmpty()) binding.dialogLocInput.setHint(hint);
    }


    private void setupSearchInput() {
        binding.dialogLocInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
    }

    private void getData() {
        viewModel.observerLiveData().observe(getViewLifecycleOwner(), list -> {
            Log.d(TAG, "getData: " + list.size());
            adapter.setNewList(list);
            binding.dialogLocRv.setHasFixedSize(true);
        });
//        Disposable sub = dataSource
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(list -> {
//                    adapter.setNewList(list);
//                    binding.dialogLocRv.setHasFixedSize(true);
//                }, throwable -> {
//                    Log.e(TAG, "getData: Error", throwable);
//                });
    }


    private void setupRecyclerView() {
        binding.dialogLocRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DialogListAdapter<T>(position -> {
            T region = adapter.getItemAt(position);
            Toast.makeText(getContext(), "Pressed: " + region.toString(), Toast.LENGTH_SHORT).show();

            resultData.putExtra(REG_ID, region.getSlug());

            Fragment target = getTargetFragment();
            if (target != null) {
                target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, resultData);
                dismiss();
            }
        });

        binding.dialogLocRv.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
//        outState.putParcelableArray(LIST, (Parcelable[]) adapter.getCurrentList().toArray());
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: Called");
//        disposableBag.clear();
    }

}
