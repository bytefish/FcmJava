// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.interceptors.response;

import de.bytefish.fcmjava.exceptions.FcmAuthenticationException;
import de.bytefish.fcmjava.exceptions.FcmBadRequestException;
import de.bytefish.fcmjava.exceptions.FcmGeneralException;
import de.bytefish.fcmjava.exceptions.FcmUnavailableException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class StatusResponseInterceptor implements HttpResponseInterceptor {

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        // Early exit, if there is no HTTP Response:
        if(httpResponse == null) {
            return;
        }

        // Early exit, if we can't determine the Status:
        if(httpResponse.getStatusLine() == null) {
            return;
        }

        // Get the HTTP Status Code:
        int httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Is it OK? So we can exit here:
        if (httpStatusCode == HttpStatus.SC_OK)
        {
            return;
        }

        // The Error Reason:
        String reasonPhrase = httpResponse.getStatusLine().getReasonPhrase();

        // Now throw the right exception at the user:
        switch (httpStatusCode)
        {
            case HttpStatus.SC_BAD_REQUEST:
                throw new FcmBadRequestException(reasonPhrase);
            case HttpStatus.SC_UNAUTHORIZED:
                throw new FcmAuthenticationException(reasonPhrase);
            case HttpStatus.SC_SERVICE_UNAVAILABLE:
                throw new FcmUnavailableException(reasonPhrase);
            default:
                throw new FcmGeneralException(reasonPhrase);
        }
    }
}
