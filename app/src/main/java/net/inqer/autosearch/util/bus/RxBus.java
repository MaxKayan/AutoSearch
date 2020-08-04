package net.inqer.autosearch.util.bus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@Singleton
public class RxBus {
    private static final String TAG = "RxBus";
    private PublishSubject publisher = PublishSubject.create();

    @Inject
    public RxBus() {
    }

    public void publish(Object event) {
//        Log.d(TAG, "publish: new event: " + event.toString());
        publisher.onNext(event);
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class PickerType
    public <T> Observable<T> listen(Class<T> eventType) {
        return publisher.ofType(eventType);
    }
}
