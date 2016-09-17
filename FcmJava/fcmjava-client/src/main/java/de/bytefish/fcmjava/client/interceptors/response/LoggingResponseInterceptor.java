// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.interceptors.response;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingResponseInterceptor implements HttpResponseInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingResponseInterceptor.class);

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        if(log.isDebugEnabled()) {
            log.debug(httpResponse.toString());
        }
    }
}
