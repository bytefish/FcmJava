// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.interceptors.response;

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
            if (tryDetermineRetryDelay(httpResponse, result)) {
                throw new FcmRetryAfterException(result.get(), reasonPhrase);
            }
        }

        throw new FcmGeneralException(reasonPhrase);
    }

    private boolean tryDetermineRetryDelay(HttpResponse httpResponse, OutParameter<Duration> retryDelay) {
        try {
            return internalTryDetermineRetryDelay(httpResponse, retryDelay);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean internalTryDetermineRetryDelay(HttpResponse httpResponse, OutParameter<Duration> retryDelay) {

        // Try to get the Retry-After Header send by FCM:
        Header retryAfterHeader = httpResponse.getFirstHeader("Retry-After");

        // Early exit, if we do not have a Retry Header:
        if (retryAfterHeader == null) {
            return false;
        }

        // Try to get the Value:
        String retryDelayAsString = retryAfterHeader.getValue();

        // Try to parse as Number and Date:
        return tryGetRetryDelay(retryDelayAsString, retryDelay);
    }

    private boolean tryGetRetryDelay(String retryDelayAsString, OutParameter<Duration> result) {
        if(tryGetFromLong(retryDelayAsString, result)) {
            return true;
        }
        if(tryGetFromDate(retryDelayAsString, result)) {
            return true;
        }
        return false;
    }

    private boolean tryGetFromLong(String retryDelayAsString, OutParameter<Duration> result) {
        OutParameter<Long> longResult = new OutParameter<>();

        if(!tryConvertToLong(retryDelayAsString, longResult)) {
            return false;
        }

        // If we can convert it to Long, it is a Second Duration:
        Duration retryDelayAsDuration = Duration.ofSeconds(longResult.get());

        // Set in the Out Parameter:
        result.set(retryDelayAsDuration);

        return true;
    }

    private boolean tryConvertToLong(String longAsString, OutParameter<Long> result) {
        try {
            result.set(Long.parseLong(longAsString));

            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private boolean tryGetFromDate(String dateAsString, OutParameter<Duration> result) {
        OutParameter<ZonedDateTime> resultDate = new OutParameter<>();
        if(!tryToConvertToDate(dateAsString, resultDate)) {
            return false;
        }

        // Get Now Date and Next Retry Date as String:
        ZonedDateTime utcNowDateTime = DateUtils.getUtcNow();
        ZonedDateTime nextRetryDateTime = resultDate.get().withZoneSameInstant(ZoneOffset.UTC);

        // Calculate Duration between both:
        Duration durationToNextRetryTime = Duration.between(utcNowDateTime, nextRetryDateTime);

        // Set it as Result:
        result.set(durationToNextRetryTime);

        // And return success:
        return true;
    }

    private boolean tryToConvertToDate(String dateAsString, OutParameter<ZonedDateTime> result) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
            result.set(ZonedDateTime.parse(dateAsString, formatter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}