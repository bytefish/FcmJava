// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.interceptors.response;

import de.bytefish.fcmjava.client.interceptors.response.utils.RetryHeaderUtils;
import de.bytefish.fcmjava.client.utils.DateUtils;
import de.bytefish.fcmjava.client.utils.OutParameter;
import de.bytefish.fcmjava.exceptions.*;
import org.apache.http.*;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class StatusResponseInterceptor implements HttpResponseInterceptor {

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        // Early exit, if there is no HTTP Response:
        if (httpResponse == null) {
            return;
        }

        // Early exit, if we can't determine the Status:
        if (httpResponse.getStatusLine() == null) {
            return;
        }

        // Get the HTTP Status Code:
        int httpStatusCode = httpResponse.getStatusLine().getStatusCode();

        // Is it OK? So we can exit here:
        if (httpStatusCode == HttpStatus.SC_OK) {
            return;
        }

        // The Error Reason:
        String reasonPhrase = httpResponse.getStatusLine().getReasonPhrase();

        // If it is a Bad Request, we could not retry it:
        if (httpStatusCode == HttpStatus.SC_BAD_REQUEST) {
            throw new FcmBadRequestException(reasonPhrase);
        }

        // If we are unauthorized, we could not retry it:
        if (httpStatusCode == HttpStatus.SC_UNAUTHORIZED) {
            throw new FcmAuthenticationException(reasonPhrase);
        }

        // Any Status Code between 500 and 600 could be retried:
        if (httpStatusCode >= 500 && httpStatusCode < 600) {

            // Holds the Duration, which has been sent by the Server:
            OutParameter<Duration> result = new OutParameter<>();

            // Try to determine the next interval we can send at:
            if (RetryHeaderUtils.tryDetermineRetryDelay(httpResponse, result)) {
                throw new FcmRetryAfterException(result.get(), reasonPhrase);
            }
        }

        throw new FcmGeneralException(reasonPhrase);
    }


}