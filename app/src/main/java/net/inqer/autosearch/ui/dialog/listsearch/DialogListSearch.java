package net.inqer.autosearch.ui.dialog.listsearch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import net.inqer.autosearch.data.model.ListItem;
import net.inqer.autosearch.databinding.DialogListSearchBinding;
import net.inqer.autosearch.ui.dialog.listsearch.adapter.AutoCompleteListItemAdapter;
import net.inqer.autosearch.ui.dialog.listsearch.adapter.DialogListAdapter;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class DialogListSearch<T extends ListItem> extends DialogFragment {
    public static final String RESULT = "DialogListSearch_result";
    public static final String TAG = "DialogListSearch";
    private static final String TITLE = "dialog_header";
    private static final String HINT = "dialog_search_hint";

    private DialogListSearchViewModel<T> viewModel;
    private DialogListSearchBinding binding;
    private DialogListAdapter<T> adapter;
    private AutoCompleteListItemAdapter<T> autoCompleteAdapter;
    private CompositeDisposable disposableBag = new CompositeDisposable();

    private String title;
    private String hint;
    private Flowable<List<T>> dataSource;

    private Intent resultData = new Intent();


    public static <T extends ListItem> DialogListSearch<T> newInstance(String title, String hint, Flowable<List<T>> observer) {
        DialogListSearch<T> instance = new DialogListSearch<>();
        instance.dataSource = observer;
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(HINT, hint);
        instance.setArguments(args);

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
            Log.d(TAG, "unpackBundleArgs: " + title + '\n' +
                    "data source : " + dataSource + '\n');
        } else {
            Log.w(TAG, "unpackBundleArgs: args bundle is null!");
        }
    }


    private void setupView() {
        if (title != null && !title.isEmpty()) binding.dialogLocHeader.setText(title);
        if (hint != null && !hint.isEmpty()) binding.dialogLocInput.setHint(hint);
        binding.dialogLocInput.setOnItemClickListener((parent, view, position, id) -> {
            Log.d(TAG, "setupView: "+autoCompleteAdapter.getItem(position));
            T item = autoCompleteAdapter.getItem(position);
            if (item != null) {
                finishWithResult(item);
            }
        });
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
//                adapter.getFilter().filter(s);
            }
        });
    }


    private void getData() {
        viewModel.observerLiveData().observe(getViewLifecycleOwner(), list -> {
            Log.d(TAG, "getData: " + list.size());
//            binding.dialogLocRv.setHasFixedSize(false);
            adapter.setNewList(list);
            autoCompleteAdapter = new AutoCompleteListItemAdapter<>(getContext(), list);
            binding.dialogLocInput.setAdapter(autoCompleteAdapter);
//            binding.dialogLocInput.setAdapter(new ArrayAdapter<T>(getContext(), android.R.layout.simple_list_item_1, list));
            Log.d(TAG, binding.dialogLocInput.getAdapter().toString()+"\n"+
                    binding.dialogLocInput.getAdapter().getCount());
//            binding.dialogLocRv.setHasFixedSize(true);
            if (adapter.getCurrentList().size() > 0) {
                binding.dialogLocRv.setHasFixedSize(true);
            }
        });
    }


    @SuppressWarnings("unchecked")
    private void setupRecyclerView() {
//        binding.dialogLocRv.setHasFixedSize(false);
        binding.dialogLocRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DialogListAdapter<T>(item -> {
            finishWithResult((T) item);
        });

        binding.dialogLocRv.setAdapter(adapter);
    }


    @Override
    public void onPause() {
        binding.dialogLocRv.setHasFixedSize(false);
        super.onPause();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: Called");
//        disposableBag.clear();
    }


    private void finishWithResult(T result) {
//        Fragment target = getTargetFragment();
        if (result != null) {
            Bundle bundle = new Bundle();
            bundle.putString(RESULT, result.getName());
//            target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, resultData);
            getParentFragmentManager().setFragmentResult("listener", bundle);
            dismiss();
        }
    }

}
