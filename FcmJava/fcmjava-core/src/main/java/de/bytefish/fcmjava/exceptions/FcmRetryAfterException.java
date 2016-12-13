// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.exceptions;

import java.time.Duration;
import java.time.ZonedDateTime;

public class FcmRetryAfterException extends FcmException {

    private final Duration retryDelay;

    public FcmRetryAfterException(Duration retryDelay) {
        this.retryDelay = retryDelay;
    }

    public FcmRetryAfterException(Duration retryDelay, String message) {
        super(message);

        this.retryDelay = retryDelay;
    }

    public FcmRetryAfterException(Duration retryDelay, String message, Throwable cause) {
        super(message, cause);

        this.retryDelay = retryDelay;
    }

    public FcmRetryAfterException(Duration retryDelay, Throwable cause) {
        super(cause);

        this.retryDelay = retryDelay;
    }

    public FcmRetryAfterException(Duration retryDelay, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

        this.retryDelay = retryDelay;
    }

}
