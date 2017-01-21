// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ErrorCodeEnum
{
    @JsonProperty("InvalidRegistration")
    InvalidRegistration,

    @JsonProperty("NotRegistered")
    NotRegistered,

    @JsonProperty("MessageTooBig")
    MessageTooBig,

    @JsonProperty("MissingRegistration")
    MissingRegistration,

    @JsonProperty("Unavailable")
    Unavailable,

    @JsonProperty("MismatchSenderId")
    MismatchSenderId,

    @JsonProperty("InvalidDataKey")
    InvalidDataKey,

    @JsonProperty("InvalidTtl")
    InvalidTtl,

    @JsonProperty("InternalServerError")
    InternalServerError,

    @JsonProperty("InvalidPackageName")
    InvalidPackageName,

    @JsonProperty("DeviceMessageRateExceeded")
    DeviceMessageRateExceeded,

    @JsonProperty("TopicsMessageRateExceeded")
    TopicsMessageRateExceeded,

    @JsonProperty("InvalidParameters")
    InvalidParameters
}