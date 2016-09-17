// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.interceptors.request;

import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingRequestInterceptor implements HttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if(log.isDebugEnabled()) {
            log.debug(httpRequest.toString());
        }
    }
}
