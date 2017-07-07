// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.FcmMulticastMessage;
import de.bytefish.fcmjava.requests.notification.NotificationPayload;

import java.util.Collection;

public class DataMulticastMessage extends FcmMulticastMessage<Object> {

    private final Object data;
    private final NotificationPayload notification;

    public DataMulticastMessage(FcmMessageOptions options, Collection<String> registrationIds, Object data) {
        this(options, registrationIds, data, null);
    }

    public DataMulticastMessage(FcmMessageOptions options, Collection<String> registrationIds, Object data, NotificationPayload notification) {
        super(options, registrationIds);

        this.data = data;
        this.notification = notification;
    }

    @Override
    @JsonProperty("data")
    public Object getPayload() {
        return data;
    }

    @JsonProperty("notification")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public NotificationPayload getNotificationPayload() {
        return this.notification;
    }

}
