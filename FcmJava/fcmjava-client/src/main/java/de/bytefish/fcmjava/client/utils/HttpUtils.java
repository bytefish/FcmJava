// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.utils;

import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpUtils {


    public static <TRequestMessage, TResponseMessage> TResponseMessage post(HttpClientBuilder httpClientBuilder, IFcmClientSettings settings, TRequestMessage requestMessage, Class<TResponseMessage> responseType) {
        try {
            return internalPost(httpClientBuilder, settings, requestMessage, responseType);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <TRequestMessage> void post(HttpClientBuilder httpClientBuilder, IFcmClientSettings settings, TRequestMessage requestMessage) {
        try {
            internalPost(httpClientBuilder, settings, requestMessage);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <TRequestMessage> void internalPost(HttpClientBuilder httpClientBuilder, IFcmClientSettings settings, TRequestMessage requestMessage) throws Exception {

        try (CloseableHttpClient client = httpClientBuilder.build()) {

            // Initialize a new post Request:
            HttpPost httpPost = new HttpPost(settings.getFcmUrl());

            // Set the JSON String as data:
            httpPost.setEntity(new StringEntity(JsonUtils.getAsJsonString(requestMessage)));

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

    private static <TRequestMessage, TResponseMessage> TResponseMessage internalPost(HttpClientBuilder httpClientBuilder, IFcmClientSettings settings, TRequestMessage requestMessage, Class<TResponseMessage> responseType) throws Exception {

        try(CloseableHttpClient client = httpClientBuilder.build()) {

            // Initialize a new post Request:
            HttpPost httpPost = new HttpPost(settings.getFcmUrl());

            // Get the JSON representation of the given request message:
            String requestJson = JsonUtils.getAsJsonString(requestMessage);

            // Set the JSON String as data:
            httpPost.setEntity(new StringEntity(requestJson));

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
