// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client;

import de.bytefish.fcmjava.client.http.HttpClient;
import de.bytefish.fcmjava.client.http.IHttpClient;
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
import de.bytefish.fcmjava.responses.FcmMessageResponse;
import de.bytefish.fcmjava.responses.TopicMessageResponse;

public class FcmClient implements IFcmClient {

    private final IFcmClientSettings settings;
    private final IHttpClient httpClient;

    public FcmClient() {
        this(PropertiesBasedSettings.createFromDefault());
    }

    public FcmClient(IFcmClientSettings settings) {
        this(settings, new HttpClient(settings));
    }

    public FcmClient(IFcmClientSettings settings, IHttpClient httpClient) {

        if(settings == null) {
            throw new IllegalArgumentException("settings");
        }

        if(httpClient == null) {
            throw new IllegalArgumentException("httpClient");
        }

        this.settings = settings;
        this.httpClient = httpClient;
    }

    @Override
    public FcmMessageResponse send(DataMulticastMessage message) {
        return post(message, FcmMessageResponse.class);
    }

    @Override
    public FcmMessageResponse send(NotificationMulticastMessage notification) {
        return post(notification, FcmMessageResponse.class);
    }

    @Override
    public FcmMessageResponse send(DataUnicastMessage message) {
        return post(message, FcmMessageResponse.class);
    }

    @Override
    public FcmMessageResponse send(NotificationUnicastMessage notification) {
        return post(notification, FcmMessageResponse.class);
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
        return httpClient.post(requestMessage, responseType);
    }

    protected <TRequestMessage> void post(TRequestMessage requestMessage) {
        httpClient.post(requestMessage);
    }
}
