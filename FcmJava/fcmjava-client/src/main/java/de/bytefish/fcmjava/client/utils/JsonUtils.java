// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility Methods to simplify JSON Serialization and Deserialization with Jackson.
 */
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns the given Entity as a JSON String.
     * @param source The Source object, which should be annotated-
     * @param <TEntity> Type of the Source object.
     * @return String representation of the Java object.
     */
    public static <TEntity> String getAsJsonString(TEntity source) {
        try {
            return internalGetAsJsonString(source);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes a JSON String into a Java Object.
     * @param source The Source JSON
     * @param valueType The Class to deserialize into.
     * @param <TEntity> The type of the Java class.
     * @return A deserialized object from the given JSON data.
     */
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
