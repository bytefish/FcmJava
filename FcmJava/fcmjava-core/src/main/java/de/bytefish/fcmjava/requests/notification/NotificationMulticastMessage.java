// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.FcmMulticastMessage;

import java.util.Collection;

public class NotificationMulticastMessage extends FcmMulticastMessage<NotificationPayload> {

    private final NotificationPayload notificationPayload;

    public NotificationMulticastMessage(FcmMessageOptions options, Collection<String> registrationIds, NotificationPayload notificationPayload) {
        super(options, registrationIds);

        if(notificationPayload == null) {
            throw new IllegalArgumentException("notificationPayload");
        }

        this.notificationPayload = notificationPayload;
    }

    @Override
    @JsonProperty("notification")
    public NotificationPayload getPayload() {
        return this.notificationPayload;
    }
}
