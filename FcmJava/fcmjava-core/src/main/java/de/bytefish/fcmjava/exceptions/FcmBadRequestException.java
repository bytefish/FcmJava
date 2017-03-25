// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.exceptions;

/**
 * This Exception is thrown, if a Bad Request to FCM was made.
 */
public class FcmBadRequestException extends FcmException {

    public FcmBadRequestException() {
    }

    public FcmBadRequestException(String message) {
        super(message);
    }

    public FcmBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public FcmBadRequestException(Throwable cause) {
        super(cause);
    }

    public FcmBadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
