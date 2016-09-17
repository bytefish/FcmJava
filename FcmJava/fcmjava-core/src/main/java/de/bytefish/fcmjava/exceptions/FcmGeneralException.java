// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.exceptions;

public class FcmGeneralException extends FcmException {

    public FcmGeneralException() {
    }

    public FcmGeneralException(String message) {
        super(message);
    }

    public FcmGeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public FcmGeneralException(Throwable cause) {
        super(cause);
    }

    public FcmGeneralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
