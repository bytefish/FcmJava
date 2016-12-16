// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.retry.strategy;

import de.bytefish.fcmjava.client.functional.Action0;
import de.bytefish.fcmjava.client.functional.Func1;
import de.bytefish.fcmjava.exceptions.FcmRetryAfterException;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;

import java.time.Duration;

public class SimpleRetryStrategy implements IRetryStrategy {

    private final int maxRetries;

    public SimpleRetryStrategy(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public void doWithRetry(Action0 action) {
        getWithRetry(() -> {
            action.invoke();

            return null;
        });
    }

    @Override
    public <TResult> TResult getWithRetry(Func1<TResult> function) {

        // Holds the current Retry Count:
        int currentRetryCount = 0;

        // Holds the Return Value:
        TResult returnValue = null;

        // Simple Retry Loop with Thread Sleep for waiting:
        do {
            try {
                returnValue = function.invoke();
                // Break out of Loop, if there was no exception:
                break;
            } catch(FcmRetryAfterException e) {
                currentRetryCount = currentRetryCount + 1;
                // If we hit the maximum retry count, then throw the Exception:
                if(currentRetryCount == maxRetries) {
                    throw e;
                }
                // Sleep for the amount of time returned by FCM:
                internalSleep(e.getRetryDelay());
            }
        } while(currentRetryCount <= maxRetries);

        // And finally return the result:
        return returnValue;
    }

    private void internalSleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
