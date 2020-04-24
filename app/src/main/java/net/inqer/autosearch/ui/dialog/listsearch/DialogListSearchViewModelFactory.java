package net.inqer.autosearch.ui.dialog.listsearch;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import net.inqer.autosearch.data.model.ListItem;

import java.util.List;

import io.reactivex.Flowable;

public class DialogListSearchViewModelFactory<O extends ListItem> implements ViewModelProvider.Factory {

    private final String title;
    private final Flowable<List<O>> dataSource;

    public DialogListSearchViewModelFactory(String title, Flowable<List<O>> dataSource) {
        this.title = title;
        this.dataSource = dataSource;
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) DialogListSearchViewModel.newInstance(dataSource, title);
    }
}
