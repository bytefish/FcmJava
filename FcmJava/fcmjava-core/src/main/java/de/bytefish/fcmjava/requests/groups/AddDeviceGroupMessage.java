// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.groups;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.model.enums.OperationEnum;

import java.lang.reflect.Type;
import java.util.Collection;

public class AddDeviceGroupMessage extends DeviceGroupMessage {

    private final String notificationKey;

    public AddDeviceGroupMessage(FcmMessageOptions options, Collection<String> registrationIds, String notificationKeyName, String notificationKey) {
        super(options, registrationIds, notificationKeyName);

        this.notificationKey = notificationKey;
    }

    @Override
    public OperationEnum getOperation() {
        return OperationEnum.Add;
    }

    @JsonProperty("notification_key")
    public String getNotificationKey() {
        return this.notificationKey;
    }

}
