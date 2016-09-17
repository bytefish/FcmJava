// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.topic;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.FcmUnicastMessage;

public class TopicMulticastMessage extends FcmUnicastMessage<Object> {

    private final String condition;
    private final Object data;

    public TopicMulticastMessage(FcmMessageOptions options, String condition, Object data) {
        super(options, null);

        this.condition = condition;
        this.data = data;
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

}
