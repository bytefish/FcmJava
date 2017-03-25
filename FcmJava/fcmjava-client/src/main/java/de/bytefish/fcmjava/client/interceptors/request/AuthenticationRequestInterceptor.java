// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.interceptors.request;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * This RequestInterceptor adds the API Key Request Header.
 */
public class AuthenticationRequestInterceptor implements HttpRequestInterceptor {

    private final String apiKey;

    /**
     * Instantiates a new RequestInterceptor with the given API Key.
     *
     * @param apiKey API Key used for Requests to FCM
     */
    public AuthenticationRequestInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        httpRequest.addHeader("Authorization", String.format("key=%s", apiKey));
    }
}
