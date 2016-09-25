// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <TEntity> String getAsJsonString(TEntity source) {
        try {
            return internalGetAsJsonString(source);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <TEntity> TEntity getEntityFromString(String source, Class<TEntity> valueType) {
        try {
            return internalGetEntityFromString(source, valueType);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <TEntity> String internalGetAsJsonString(TEntity source) throws Exception {
        if(source == null) {
            throw new IllegalArgumentException("source");
        }
        return mapper.writeValueAsString(source);
    }

    private static <TEntity> TEntity internalGetEntityFromString(String source, Class<TEntity> valueType) throws Exception {
        return mapper.readValue(source, valueType);
    }
}
