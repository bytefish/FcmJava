// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.exceptions;

public abstract class FcmException extends RuntimeException {

    public FcmException() {
    }

    public FcmException(String message) {
        super(message);
    }

    public FcmException(String message, Throwable cause) {
        super(message, cause);
    }

    public FcmException(Throwable cause) {
        super(cause);
    }

    public FcmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
