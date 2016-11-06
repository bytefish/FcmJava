// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.topic;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.model.topics.TopicList;
import de.bytefish.fcmjava.requests.FcmUnicastMessage;
import de.bytefish.fcmjava.requests.notification.NotificationPayload;

public class TopicMulticastMessage extends FcmUnicastMessage<Object> {

    private final String condition;
    private final Object data;
    private final NotificationPayload notification;

    public TopicMulticastMessage(FcmMessageOptions options, TopicList topicList, Object data, NotificationPayload notification) {

        super(options, null);

        if(topicList == null) {
            throw new IllegalArgumentException("topicList");
        }

        this.condition = topicList.getTopicsCondition();
        this.data = data;
        this.notification = notification;
    }


    public TopicMulticastMessage(FcmMessageOptions options, String condition, Object data, NotificationPayload notification) {
        super(options, null);

        if(condition == null) {
            throw new IllegalArgumentException("condition");
        }

        this.condition = condition;
        this.data = data;
        this.notification = notification;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    @JsonProperty("data")
    public Object getPayload() {
        return data;
    }

    @JsonProperty("notification")
    public NotificationPayload getNotificationPayload() {
        return this.notification;
    }

}
