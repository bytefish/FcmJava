package de.bytefish.fcmjava.client.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateUtils {

    public static ZonedDateTime getUtcNow() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

}
