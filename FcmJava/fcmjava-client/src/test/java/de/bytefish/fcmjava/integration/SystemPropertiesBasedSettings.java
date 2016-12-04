// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.integration;

import de.bytefish.fcmjava.constants.Constants;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;

class SystemPropertiesBasedSettings implements IFcmClientSettings {

    @Override
    public String getFcmUrl() {
        return System.getProperty("fcm.api.url", Constants.FCM_URL);
    }

    /**
     * Returns the FCM API key.
     * Can be configured by using vm arguments (e.g. -Dfcm.api.key=...)
     *
     * @return FCM API key
     */
    @Override
    public String getApiKey() {
        return System.getProperty("fcm.api.key");
    }

}
