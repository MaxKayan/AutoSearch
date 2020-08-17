package net.inqer.autosearch.ui.dialog.listsearch;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.ListItem;

import java.util.List;

import io.reactivex.Flowable;

public class DialogListSearchViewModel<T extends ListItem> extends ViewModel {
    private static final String TAG = "DialogListSearchViewMod";

    private MutableLiveData<List<T>> listLiveData = new MutableLiveData<>();
    private Flowable<List<T>> dataSource;
    private String title;

    public static <O extends ListItem> DialogListSearchViewModel<O> newInstance(Flowable<List<O>> dataSource, String title) {
        DialogListSearchViewModel<O> instance = new DialogListSearchViewModel<>();
        instance.dataSource = dataSource;
        instance.title = title;

        return instance;
    }

    LiveData<List<T>> observeDataList() {
        return LiveDataReactiveStreams.fromPublisher(dataSource
                .map(tList -> {
                    if (tList.size() > 0) tList.add(0, tList.get(0));
                    return tList;
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "observerLiveData: Error: " + throwable.getMessage(), throwable);
                }));
    }
}
