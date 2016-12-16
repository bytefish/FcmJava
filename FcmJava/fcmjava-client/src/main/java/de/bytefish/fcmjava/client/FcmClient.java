// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client;

import de.bytefish.fcmjava.client.http.HttpClient;
import de.bytefish.fcmjava.client.http.IHttpClient;
import de.bytefish.fcmjava.client.retry.IRetryStrategy;
import de.bytefish.fcmjava.client.retry.SimpleRetryStrategy;
import de.bytefish.fcmjava.client.settings.PropertiesBasedSettings;
import de.bytefish.fcmjava.http.client.IFcmClient;
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

public class FcmClient implements IFcmClient {

    private final IFcmClientSettings settings;
    private final IRetryStrategy retryStrategy;
    private final IHttpClient httpClient;

    public FcmClient() {
        this(PropertiesBasedSettings.createFromDefault());
    }

    public FcmClient(IFcmClientSettings settings) {
        this(settings, new HttpClient(settings));
    }

    public FcmClient(IFcmClientSettings settings, IRetryStrategy retryStrategy) {
        this(settings, new HttpClient(settings), retryStrategy);
    }

    public FcmClient(IFcmClientSettings settings, IHttpClient httpClient) {
        this(settings, httpClient, new SimpleRetryStrategy(settings));
    }

    public FcmClient(IFcmClientSettings settings, IHttpClient httpClient, IRetryStrategy retryStrategy) {

        if(settings == null) {
            throw new IllegalArgumentException("settings");
        }

        if(httpClient == null) {
            throw new IllegalArgumentException("httpClient");
        }

        if(retryStrategy == null) {
            throw new IllegalArgumentException("retryStrategy");
        }

        this.settings = settings;
        this.httpClient = httpClient;
        this.retryStrategy = retryStrategy;
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

    protected <TRequestMessage, TResponseMessage> TResponseMessage post(TRequestMessage requestMessage, Class<TResponseMessage> responseType) {
        return retryStrategy.getWithRetry(() -> httpClient.post(requestMessage, responseType));
    }

    protected <TRequestMessage> void post(TRequestMessage requestMessage) {
        retryStrategy.doWithRetry(() -> httpClient.post(requestMessage));
    }


}
