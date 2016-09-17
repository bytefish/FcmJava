// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.enums.ErrorCodeEnum;

import java.util.List;

public class TopicMessageResponse {

    private final String messageId;
    private final ErrorCodeEnum errorCode;

    @JsonCreator
    public TopicMessageResponse(
            @JsonProperty("message_id") String messageId,
            @JsonProperty("error") ErrorCodeEnum errorCode) {
        this.messageId = messageId;
        this.errorCode = errorCode;
    }

    public String getMessageId() {
        return messageId;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }
}