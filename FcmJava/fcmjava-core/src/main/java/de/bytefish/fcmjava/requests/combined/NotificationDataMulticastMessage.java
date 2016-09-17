// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.combined;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.FcmMulticastMessage;
import de.bytefish.fcmjava.requests.notification.NotificationPayload;

import java.util.List;

public class NotificationDataMulticastMessage extends FcmMulticastMessage<Object> {

    private final NotificationPayload notificationPayload;
    private final Object dataPayload;

    public NotificationDataMulticastMessage(FcmMessageOptions options, List<String> registratiodIds, NotificationPayload notificationPayload, Object dataPayload) {
        super(options, registratiodIds);

        this.notificationPayload = notificationPayload;
        this.dataPayload = dataPayload;
    }

    @JsonProperty("notification")
    public NotificationPayload getNotificationPayload() {
        return this.notificationPayload;
    }

    @Override
    @JsonProperty("data")
    public Object getPayload() {
        return this.dataPayload;
    }
}
