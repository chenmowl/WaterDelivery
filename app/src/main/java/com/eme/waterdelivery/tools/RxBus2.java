package com.eme.waterdelivery.tools;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by dijiaoliang on 17/4/7.
 */
public class RxBus2 {

    private static RxBus2 defaultRxBus;
    private Subject<Object> bus;

    private RxBus2() {
        bus = PublishSubject.create().toSerialized();
    }

    public static RxBus2 getInstance() {
        if (null == defaultRxBus) {
            synchronized (RxBus2.class) {
                if (null == defaultRxBus) {
                    defaultRxBus = new RxBus2();
                }
            }
        }
        return defaultRxBus;
    }

    public void post(Object o){
        bus.onNext(o);
    }

    public boolean hasObservable() {
        return bus.hasObservers();
    }

    /*
     * 转换为特定类型的Obserbale
     */
    public <T> Observable<T> toObservable(Class<T> type) {
        return bus.ofType(type);
    }

}
