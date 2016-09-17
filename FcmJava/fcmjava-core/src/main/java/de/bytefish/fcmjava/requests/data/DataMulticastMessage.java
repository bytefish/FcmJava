// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.FcmMulticastMessage;

import java.util.List;

public class DataMulticastMessage extends FcmMulticastMessage<Object> {

    private final Object data;

    public DataMulticastMessage(FcmMessageOptions options, List<String> registratiodIds, Object data) {
        super(options, registratiodIds);

        this.data = data;
    }

    @Override
    @JsonProperty("data")
    public Object getPayload() {
        return data;
    }
}
