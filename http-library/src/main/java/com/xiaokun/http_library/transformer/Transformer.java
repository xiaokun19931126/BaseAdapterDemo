package com.xiaokun.http_library.transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程转换类
 *
 * @author xiaokun
 * @date 2017/11/30
 */

public class Transformer
{

    public static <T> ObservableTransformer<T, T> switchSchedulers()
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream)
            {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>()
                        {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception
                            {

                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
