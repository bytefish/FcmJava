// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.groups;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.FcmMulticastMessage;
import de.bytefish.fcmjava.model.enums.OperationEnum;

import java.util.Collection;

public abstract class DeviceGroupMessage extends FcmMulticastMessage<String> {

    private final String notificationKeyName;

    public DeviceGroupMessage(FcmMessageOptions options, Collection<String> registrationIds, String notificationKeyName) {
        super(options, registrationIds);

        this.notificationKeyName = notificationKeyName;
    }

    @Override
    @JsonProperty("notification_key_name")
    public String getPayload() {
        return notificationKeyName;
    }

    @JsonProperty("operation")
    public abstract OperationEnum getOperation();
}
