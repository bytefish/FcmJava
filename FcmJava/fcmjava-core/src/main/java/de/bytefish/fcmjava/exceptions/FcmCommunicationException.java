// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.exceptions;

public class FcmCommunicationException extends FcmException {
    public FcmCommunicationException() {
    }

    public FcmCommunicationException(String message) {
        super(message);
    }

    public FcmCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FcmCommunicationException(Throwable cause) {
        super(cause);
    }

    public FcmCommunicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
