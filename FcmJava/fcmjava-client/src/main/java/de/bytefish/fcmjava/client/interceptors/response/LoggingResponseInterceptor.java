// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.interceptors.response;

import de.bytefish.fcmjava.client.utils.OutParameter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingResponseInterceptor implements HttpResponseInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingResponseInterceptor.class);

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        if(log.isDebugEnabled()) {
            // Try to get the Response as a String:
            OutParameter<String> httpResponseString = new OutParameter<>();
            if(tryGetResponseBody(httpResponse, httpResponseString)) {
                log.debug(httpResponseString.get());
            }
        }
    }

    private boolean tryGetResponseBody(HttpResponse httpResponse, OutParameter<String> httpEntityString) {
        // This shouldn't happen:
        if(httpResponse == null) {
            return false;
        }
        // Exit, if we don't have an Entity:
        HttpEntity httpEntity = httpResponse.getEntity();
        if(httpEntity == null) {
            return false;
        }

        // Try to get the Entity as a String:
        return tryGetEntityString(httpEntity, httpEntityString);
    }

    private boolean tryGetEntityString(HttpEntity httpEntity, OutParameter<String> httpEntityString) {
        try {
            httpEntityString.set(EntityUtils.toString(httpEntity));

            return true;
        } catch (Exception e) {
            if(log.isErrorEnabled()) {
                log.error("Failed to get the HttpEntity Content", e);
            }
            return false;
        }
    }
}
