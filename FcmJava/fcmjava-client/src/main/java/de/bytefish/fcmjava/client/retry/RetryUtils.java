package de.bytefish.fcmjava.client.retry;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.client.functional.Action0;
import de.bytefish.fcmjava.client.functional.Func1;
import de.bytefish.fcmjava.client.retry.strategy.IRetryStrategy;
import de.bytefish.fcmjava.client.retry.strategy.SimpleRetryStrategy;

public class RetryUtils {

    public static <TResult> TResult getWithRetry(Func1<TResult> function, int maxRetries) {
        IRetryStrategy retryStrategy = new SimpleRetryStrategy(maxRetries);

        return getWithRetry(function, retryStrategy);
    }

    public static <TResult> TResult getWithRetry(Func1<TResult> function, IRetryStrategy retryStrategy) {
        return retryStrategy.getWithRetry(function);
    }

    public static void doWithRetry(Action0 action, int maxRetries) {
        IRetryStrategy retryStrategy = new SimpleRetryStrategy(maxRetries);

        doWithRetry(action, retryStrategy);
    }

    public static void doWithRetry(Action0 action, IRetryStrategy retryStrategy) {
        retryStrategy.doWithRetry(action);
    }


}
