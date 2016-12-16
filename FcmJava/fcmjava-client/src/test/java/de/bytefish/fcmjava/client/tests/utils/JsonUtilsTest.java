package de.bytefish.fcmjava.client.tests.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.client.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;

public class JsonUtilsTest {

    public class GermanUmlautEntity {

        private final String content;

        @JsonCreator
        public GermanUmlautEntity(@JsonProperty("content") String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

    @Test
    public void umlautsSerializeTest() {
        GermanUmlautEntity entity = new GermanUmlautEntity("Bitteschön. Dankeschön.");

        Assert.assertEquals("{\"content\":\"Bitteschön. Dankeschön.\"}", JsonUtils.getAsJsonString(entity));
    }

}
