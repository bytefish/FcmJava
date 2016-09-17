// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.topic;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.model.topics.Topic;
import de.bytefish.fcmjava.requests.FcmUnicastMessage;

public class TopicUnicastMessage extends FcmUnicastMessage<Object> {

    private final Object data;

    public TopicUnicastMessage(FcmMessageOptions options, Topic to, Object data) {
        super(options, to.getTopicPath());

        this.data = data;
    }

    @Override
    @JsonProperty("data")
    public Object getPayload() {
        return data;
    }
}
