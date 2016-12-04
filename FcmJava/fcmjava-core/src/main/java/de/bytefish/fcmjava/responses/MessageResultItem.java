// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.enums.ErrorCodeEnum;

public class MessageResultItem {

    private final String messageId;
    private final String canonicalRegistrationId;
    private final ErrorCodeEnum errorCode;

    @JsonCreator
    public MessageResultItem(
            @JsonProperty("message_id") String messageId,
            @JsonProperty("registration_id") String canonicalRegistrationId,
            @JsonProperty("error") ErrorCodeEnum errorCode) {
        this.messageId = messageId;
        this.canonicalRegistrationId = canonicalRegistrationId;
        this.errorCode = errorCode;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCanonicalRegistrationId() {
        return canonicalRegistrationId;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }
}
