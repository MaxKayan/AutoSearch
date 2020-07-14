package net.inqer.autosearch.ui.dialog.valuespicker;

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
import net.inqer.autosearch.databinding.DialogValuesPickerBinding;
import net.inqer.autosearch.ui.dialog.listsearch.DialogListSearchViewModel;
import net.inqer.autosearch.ui.dialog.listsearch.DialogListSearchViewModelFactory;
import net.inqer.autosearch.ui.dialog.listsearch.adapter.AutoCompleteListItemAdapter;
import net.inqer.autosearch.ui.dialog.listsearch.adapter.DialogListAdapter;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class DialogValuesPicker extends DialogFragment {
    public static final String TAG = "DialogValuesPicker";

    private static final String TITLE = "dialog_header";
    private static final String HINT = "dialog_values_hint";
    private static final String CODE = "dialog_values_request_code";
    public static final String RESULT = "dialog_values_result";

    private String requestKey;
    private String title;
    private String hint;

    private DialogValuesPickerBinding binding;

    public static DialogValuesPicker newInstance(String requestCode, String title, String hint) {
        DialogValuesPicker instance = new DialogValuesPicker();
        Bundle args = new Bundle();
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
        } else {
            Log.w(TAG, "unpackBundleArgs: args bundle is null!");
        }
    }


    private void setupView() {

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
