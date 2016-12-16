// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.integration.retry;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.client.http.IHttpClient;
import de.bytefish.fcmjava.exceptions.FcmRetryAfterException;
import de.bytefish.fcmjava.http.client.IFcmClient;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import de.bytefish.fcmjava.integration.utils.TestUtils;
import de.bytefish.fcmjava.model.builders.FcmMessageOptionsBuilder;
import de.bytefish.fcmjava.requests.groups.CreateDeviceGroupMessage;
import de.bytefish.fcmjava.responses.CreateDeviceGroupMessageResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FcmClientRetryTest {

    @Mock
    private IFcmClientSettings settingsMock;

    @Mock
    private IHttpClient httpClientMock;

    @Before
    public void Setup() {
        initMocks(this);
    }

    @Test
    public void retryWithThrowTest() {

        // Fake Message to send:
        CreateDeviceGroupMessage createDeviceGroupMessage = new CreateDeviceGroupMessage(new FcmMessageOptionsBuilder().build(), new ArrayList<>(), "Unit Test");

        // Set Mock Expectations:
        when(settingsMock.getRetryCount())
                .thenReturn(5);

        when(httpClientMock.post(createDeviceGroupMessage, CreateDeviceGroupMessageResponse.class))
                .thenThrow(new FcmRetryAfterException(Duration.ZERO));

        // Create the Test Subject:
        IFcmClient client = new FcmClient(settingsMock, httpClientMock);

        // Invoke it and make sure it throws:
        TestUtils.assertThrows(() -> client.send(createDeviceGroupMessage), FcmRetryAfterException.class);

        // And finally verify it has been called 5 times as set in the Mock Expectations:
        verify(httpClientMock, times(5))
                .post(createDeviceGroupMessage, CreateDeviceGroupMessageResponse.class);
    }

    @Test
    public void retryNotNecessaryTest() {

        // Fake Message to send:
        CreateDeviceGroupMessage createDeviceGroupMessage = new CreateDeviceGroupMessage(new FcmMessageOptionsBuilder().build(), new ArrayList<>(), "Unit Test");

        // Fake Message to receive:
        CreateDeviceGroupMessageResponse createDeviceGroupMessageResponse = new CreateDeviceGroupMessageResponse("Unit Test");

        // Set Mock Expectations:
        when(settingsMock.getRetryCount())
                .thenReturn(5);

        when(httpClientMock.post(createDeviceGroupMessage, CreateDeviceGroupMessageResponse.class))
                .thenReturn(createDeviceGroupMessageResponse);

        // Create the Test Subject:
        IFcmClient client = new FcmClient(settingsMock, httpClientMock);

        // Invoke it and make sure it throws:
        TestUtils.assertDoesNotThrow(() -> client.send(createDeviceGroupMessage));

        // And finally verify it has been called 5 times as set in the Mock Expectations:
        verify(httpClientMock, times(1))
                .post(createDeviceGroupMessage, CreateDeviceGroupMessageResponse.class);
    }
}
