// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.groups;

import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.model.enums.OperationEnum;

import java.util.Collection;

public class CreateDeviceGroupMessage extends DeviceGroupMessage {

    public CreateDeviceGroupMessage(FcmMessageOptions options, Collection<String> registrationIds, String notificationKeyName) {
        super(options, registrationIds, notificationKeyName);
    }

    @Override
    public OperationEnum getOperation() {
        return OperationEnum.Create;
    }

}
