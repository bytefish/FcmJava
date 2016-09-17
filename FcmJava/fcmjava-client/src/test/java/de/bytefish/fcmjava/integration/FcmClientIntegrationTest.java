// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.constants.Constants;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.model.topics.Topic;
import de.bytefish.fcmjava.requests.topic.TopicUnicastMessage;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;
import java.time.Duration;

public class FcmClientIntegrationTest {

    private class PersonData {

        private final String firstName;
        private final String lastName;

        public PersonData(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @JsonProperty("firstName")
        public String getFirstName() {
            return firstName;
        }

        @JsonProperty("lastName")
        public String getLastName() {
            return lastName;
        }
    }

    private class FileContentBasedSettings implements IFcmClientSettings {

        private final String apiToken;

        public FileContentBasedSettings(String apiTokenPath, Charset encoding) {
            apiToken = FileUtils.readFile(apiTokenPath, encoding);
        }

        @Override
        public String getFcmUrl() {
            return Constants.FCM_URL;
        }

        @Override
        public String getApiKey() {
            return apiToken;
        }
    }

    @Test
    @Ignore("This is an Integration Test using external files to contact the FCM Server")
    public void SendMessageTest() throws Exception {

        // Create the Client using file-based settings:
        FcmClient client = new FcmClient(new FileContentBasedSettings("D:\\token.txt", Charset.forName("UTF-8")));

        // Message Options:
        FcmMessageOptions options = FcmMessageOptions.builder()
                .setTimeToLive(Duration.ofHours(1))
                .build();

        // Send a Message:
        client.send(new TopicUnicastMessage(options, new Topic("news"), new PersonData("Philipp", "Wagner")));
    }
}
