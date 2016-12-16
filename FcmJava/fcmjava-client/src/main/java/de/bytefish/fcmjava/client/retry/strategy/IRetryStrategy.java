// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.retry.strategy;

import de.bytefish.fcmjava.client.functional.Action0;
import de.bytefish.fcmjava.client.functional.Func1;

public interface IRetryStrategy {

    void doWithRetry(Action0 action);

    <TResult> TResult getWithRetry(Func1<TResult> function);

}
