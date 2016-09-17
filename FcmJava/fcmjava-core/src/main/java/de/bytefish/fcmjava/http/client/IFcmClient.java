// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.http.client;

import de.bytefish.fcmjava.requests.data.DataMulticastMessage;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import de.bytefish.fcmjava.requests.groups.AddDeviceGroupMessage;
import de.bytefish.fcmjava.requests.groups.CreateDeviceGroupMessage;
import de.bytefish.fcmjava.requests.groups.RemoveDeviceGroupMessage;
import de.bytefish.fcmjava.requests.notification.NotificationMulticastMessage;
import de.bytefish.fcmjava.requests.notification.NotificationUnicastMessage;
import de.bytefish.fcmjava.requests.topic.TopicMulticastMessage;
import de.bytefish.fcmjava.requests.topic.TopicUnicastMessage;
import de.bytefish.fcmjava.responses.CreateDeviceGroupMessageResponse;
import de.bytefish.fcmjava.responses.MulticastMessageResponse;
import de.bytefish.fcmjava.responses.TopicMessageResponse;
import de.bytefish.fcmjava.responses.UnicastMessageResponse;

public interface IFcmClient {

    MulticastMessageResponse send(DataMulticastMessage message);

    MulticastMessageResponse send(NotificationMulticastMessage notification);

    UnicastMessageResponse send(DataUnicastMessage message);

    UnicastMessageResponse send(NotificationUnicastMessage notification);

    CreateDeviceGroupMessageResponse send(CreateDeviceGroupMessage message);

    TopicMessageResponse send(TopicUnicastMessage message);

    TopicMessageResponse send(TopicMulticastMessage message);

    void send(RemoveDeviceGroupMessage message);

    void send(AddDeviceGroupMessage message);


    
}
