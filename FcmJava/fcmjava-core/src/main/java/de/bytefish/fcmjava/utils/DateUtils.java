// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateUtils {

    public static ZonedDateTime getUtcNow() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

}
