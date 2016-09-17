// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.exceptions;

public class FcmUnavailableException extends FcmException {

    public FcmUnavailableException() {
    }

    public FcmUnavailableException(String message) {
        super(message);
    }

    public FcmUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public FcmUnavailableException(Throwable cause) {
        super(cause);
    }

    public FcmUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
