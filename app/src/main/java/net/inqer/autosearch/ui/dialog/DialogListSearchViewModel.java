package net.inqer.autosearch.ui.dialog;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.inqer.autosearch.data.model.ListItem;

import java.util.List;

import io.reactivex.Flowable;

public class DialogListSearchViewModel<O extends ListItem> extends ViewModel {
    private static final String TAG = "DialogListSearchViewMod";

    private MutableLiveData<List<O>> listLiveData = new MutableLiveData<>();
    private Flowable<List<O>> dataSource;
    private String title;

    public static <O extends ListItem> DialogListSearchViewModel<O> newInstance(Flowable<List<O>> dataSource, String title) {
        DialogListSearchViewModel<O> instance = new DialogListSearchViewModel<>();
        instance.dataSource = dataSource;
        instance.title = title;

        Log.d(TAG, "newInstance: done!");
        return instance;
    }

    LiveData<List<O>> observerLiveData() {
        return LiveDataReactiveStreams.fromPublisher(dataSource);
    }
}
