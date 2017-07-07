// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;

import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class FcmMulticastMessage<TPayload> extends FcmMessage<TPayload> {

    private final Collection<String> registrationIds;

    public FcmMulticastMessage(FcmMessageOptions options, Collection<String> registrationIds) {
        super(options);

        if(registrationIds == null) {
            throw new IllegalArgumentException("registrationIds");
        }

        this.registrationIds = registrationIds;
    }

    @JsonProperty("registration_ids")
    public Collection<String> getRegistrationIds() {
        return registrationIds;
    }

}
