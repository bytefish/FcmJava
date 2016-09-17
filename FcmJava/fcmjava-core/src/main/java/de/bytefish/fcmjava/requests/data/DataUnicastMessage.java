// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.FcmUnicastMessage;

public class DataUnicastMessage extends FcmUnicastMessage<Object> {

    private final Object data;

    public DataUnicastMessage(FcmMessageOptions options, String to, Object data) {
        super(options, to);

        this.data = data;
    }

    @Override
    @JsonProperty("data")
    public Object getPayload() {
        return data;
    }
}
