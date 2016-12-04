// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Don't throw exceptions if Google adds additional properties to the response
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateDeviceGroupMessageResponse {

    private final String notificationKey;

    @JsonCreator
    public CreateDeviceGroupMessageResponse(@JsonProperty String notificationKey) {
        this.notificationKey = notificationKey;
    }

    public String getNotificationKey() {
        return notificationKey;
    }
}
