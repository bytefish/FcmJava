// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.exceptions;

public class FcmAuthenticationException extends FcmException {

    public FcmAuthenticationException() {
    }

    public FcmAuthenticationException(String message) {
        super(message);
    }

    public FcmAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FcmAuthenticationException(Throwable cause) {
        super(cause);
    }

    public FcmAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
