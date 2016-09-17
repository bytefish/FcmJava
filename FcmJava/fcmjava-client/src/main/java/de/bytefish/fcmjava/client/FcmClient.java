// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bytefish.fcmjava.http.client.IFcmClient;
import de.bytefish.fcmjava.client.interceptors.request.AuthenticationRequestInterceptor;
import de.bytefish.fcmjava.client.interceptors.request.JsonRequestInterceptor;
import de.bytefish.fcmjava.client.interceptors.request.LoggingRequestInterceptor;
import de.bytefish.fcmjava.client.interceptors.response.LoggingResponseInterceptor;
import de.bytefish.fcmjava.client.interceptors.response.StatusResponseInterceptor;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import de.bytefish.fcmjava.requests.data.DataMulticastMessage;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import de.bytefish.fcmjava.requests.groups.AddDeviceGroupMessage;
import de.bytefish.fcmjava.requests.groups.CreateDeviceGroupMessage;
import de.bytefish.fcmjava.requests.groups.RemoveDeviceGroupMessage;
import de.bytefish.fcmjava.requests.notification.NotificationMulticastMessage;
import de.bytefish.fcmjava.requests.notification.NotificationUnicastMessage;
import de.bytefish.fcmjava.requests.topic.TopicMulticastMessage;
import de.bytefish.fcmjava.requests.topic.TopicUnicastMessage;
import de.bytefish.fcmjava.responses.CreateDeviceGroupMessageResponse;
import de.bytefish.fcmjava.responses.MulticastMessageResponse;
import de.bytefish.fcmjava.responses.TopicMessageResponse;
import de.bytefish.fcmjava.responses.UnicastMessageResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class FcmClient implements IFcmClient {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final IFcmClientSettings settings;
    private final HttpClientBuilder httpClientBuilder;

    public FcmClient(IFcmClientSettings settings) {

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

    @Override
    public MulticastMessageResponse send(DataMulticastMessage message) {
        return post(message, MulticastMessageResponse.class);
    }

    @Override
    public MulticastMessageResponse send(NotificationMulticastMessage notification) {
        return post(notification, MulticastMessageResponse.class);
    }

    @Override
    public UnicastMessageResponse send(DataUnicastMessage message) {
        return post(message, UnicastMessageResponse.class);
    }

    @Override
    public UnicastMessageResponse send(NotificationUnicastMessage notification) {
        return post(notification, UnicastMessageResponse.class);
    }

    @Override
    public CreateDeviceGroupMessageResponse send(CreateDeviceGroupMessage message) {
        return post(message, CreateDeviceGroupMessageResponse.class);
    }

    @Override
    public TopicMessageResponse send(TopicUnicastMessage message) {
        return post(message, TopicMessageResponse.class);
    }

    @Override
    public TopicMessageResponse send(TopicMulticastMessage message) {
        return post(message, TopicMessageResponse.class);
    }

    @Override
    public void send(RemoveDeviceGroupMessage message) {
        post(message);
    }

    @Override
    public void send(AddDeviceGroupMessage message) {
        post(message);
    }

    private <TRequestMessage> void InternalPost(TRequestMessage requestMessage) throws Exception {

        try (CloseableHttpClient client = httpClientBuilder.build()) {

            // Initialize a new post Request:
            HttpPost httpPost = new HttpPost(settings.getFcmUrl());

            // Set the JSON String as data:
            httpPost.setEntity(new StringEntity(getJsonString(requestMessage)));

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

    private <TRequestMessage, TResponseMessage> TResponseMessage InternalPost(TRequestMessage requestMessage, Class<TResponseMessage> responseType) throws Exception {

        try(CloseableHttpClient client = httpClientBuilder.build()) {

            // Initialize a new post Request:
            HttpPost httpPost = new HttpPost(settings.getFcmUrl());

            // Get the JSON representation of the given request message:
            String requestJson = getJsonString(requestMessage);

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
                return getEntity(responseBody, responseType);
            }
        }
    }

    protected <TRequestMessage, TResponseMessage> TResponseMessage post(TRequestMessage requestMessage, Class<TResponseMessage> responseType) {
        try {
            return InternalPost(requestMessage, responseType);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected <TRequestMessage> void post(TRequestMessage requestMessage) {
        try {
            InternalPost(requestMessage);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <TEntity> String getJsonString(TEntity source) throws Exception {
        if(source == null) {
            throw new IllegalArgumentException("source");
        }
        return mapper.writeValueAsString(source);
    }

    private <TEntity> TEntity getEntity(String source, Class<TEntity> valueType) throws Exception {
        return mapper.readValue(source, valueType);
    }
}
