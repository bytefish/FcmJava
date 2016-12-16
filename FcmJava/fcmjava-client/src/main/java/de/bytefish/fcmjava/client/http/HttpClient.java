// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.http;

import de.bytefish.fcmjava.client.interceptors.request.AuthenticationRequestInterceptor;
import de.bytefish.fcmjava.client.interceptors.request.JsonRequestInterceptor;
import de.bytefish.fcmjava.client.interceptors.request.LoggingRequestInterceptor;
import de.bytefish.fcmjava.client.interceptors.response.LoggingResponseInterceptor;
import de.bytefish.fcmjava.client.interceptors.response.StatusResponseInterceptor;
import de.bytefish.fcmjava.client.utils.JsonUtils;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;

public class HttpClient implements IHttpClient {

    private final IFcmClientSettings settings;
    private final HttpClientBuilder httpClientBuilder;

    public HttpClient(IFcmClientSettings settings) {

        if(settings == null) {
            throw new IllegalArgumentException("settings");
        }

        this.settings = settings;

        // Construct the Builder for all Requests:
        this.httpClientBuilder = HttpClientBuilder.create()
                // Build Request Pipeline:
                .addInterceptorFirst(new AuthenticationRequestInterceptor(settings.getApiKey()))
                .addInterceptorLast(new JsonRequestInterceptor())
                .addInterceptorLast(new LoggingRequestInterceptor())
                // Build Response Pipeline:
                .addInterceptorFirst(new LoggingResponseInterceptor())
                .addInterceptorLast(new StatusResponseInterceptor());
    }

    public <TRequestMessage, TResponseMessage> TResponseMessage post(TRequestMessage requestMessage, Class<TResponseMessage> responseType) {
        try {
            return internalPost(requestMessage, responseType);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <TRequestMessage> void post(TRequestMessage requestMessage) {
        try {
            internalPost(requestMessage);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <TRequestMessage> void internalPost(TRequestMessage requestMessage) throws Exception {

        try (CloseableHttpClient client = httpClientBuilder.build()) {

            // Initialize a new post Request:
            HttpPost httpPost = new HttpPost(settings.getFcmUrl());

            // Set the JSON String as data:
            httpPost.setEntity(new StringEntity(JsonUtils.getAsJsonString(requestMessage), StandardCharsets.UTF_8));

            // Execute the Request:
            try(CloseableHttpResponse response = client.execute(httpPost)) {

                // Get the HttpEntity:
                HttpEntity entity = response.getEntity();

                // Let's be a good citizen and consume the HttpEntity:
                if(entity != null) {

                    // Make Sure it is fully consumed:
                    EntityUtils.consume(entity);
                }
            }
        }
    }

    private <TRequestMessage, TResponseMessage> TResponseMessage internalPost(TRequestMessage requestMessage, Class<TResponseMessage> responseType) throws Exception {

        try(CloseableHttpClient client = httpClientBuilder.build()) {

            // Initialize a new post Request:
            HttpPost httpPost = new HttpPost(settings.getFcmUrl());

            // Get the JSON representation of the given request message:
            String requestJson = JsonUtils.getAsJsonString(requestMessage);

            // Set the JSON String as data:
            httpPost.setEntity(new StringEntity(requestJson, StandardCharsets.UTF_8));

            // Execute the Request:
            try(CloseableHttpResponse response = client.execute(httpPost)) {

                // Get the HttpEntity of the Response:
                HttpEntity entity = response.getEntity();

                // If we don't have a HttpEntity, we won't be able to convert it:
                if(entity == null) {
                    // Simply return null (no response) in this case:
                    return null;
                }

                // Get the JSON Body:
                String responseBody = EntityUtils.toString(entity);

                // Make Sure it is fully consumed:
                EntityUtils.consume(entity);

                // And finally return the Response Message:
                return JsonUtils.getEntityFromString(responseBody, responseType);
            }
        }
    }
}
