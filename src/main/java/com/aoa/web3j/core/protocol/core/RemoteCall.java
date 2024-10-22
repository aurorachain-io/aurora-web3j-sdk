package com.aoa.web3j.core.protocol.core;

import com.aoa.web3j.core.utils.Async;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import rx.Observable;

/**
 * A common type for wrapping remote requests.
 *
 * @param <T> Our return type.
 */
public class RemoteCall<T> {

    private Callable<T> callable;

    public RemoteCall(Callable<T> callable) {
        this.callable = callable;
    }

    /**
     * Perform request synchronously.
     *
     * @return result of enclosed function
     * @throws Exception if the function throws an exception
     */
    public T send() throws Exception {
        return callable.call();
    }

    /**
     * Perform request asynchronously with a future.
     *
     * @return a future containing our function
     */
    public CompletableFuture<T> sendAsync() {
        return Async.run(this::send);
    }

    /**
     * Provide an observable to emit result from our function.
     *
     * @return an observable
     */
    public Observable<T> observable() {
        return Observable.create(
                subscriber -> {
                    try {
                        subscriber.onNext(send());
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
                                );
    }
}
