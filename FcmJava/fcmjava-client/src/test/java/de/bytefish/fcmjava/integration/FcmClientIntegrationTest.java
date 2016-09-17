// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.integration;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.constants.Constants;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import org.junit.Test;

public class FcmClientIntegrationTest {

    private class FixedSettings implements IFcmClientSettings {

        @Override
        public String getFcmUrl() {
            return Constants.FCM_URL;
        }

        @Override
        public String getApiKey() {
            return "ahafsdjfsd8z371283";
        }
    }

    @Test
    public void SendMessageTest() throws Exception {
        FcmClient client = new FcmClient(new FixedSettings());

        // Message Options:
        FcmMessageOptions options = FcmMessageOptions.builder()
                .setTimeToLive(60)
                .build();

        // send a Message:
        client.send(new DataUnicastMessage(options,"sdads", 1));
    }

}
