// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.test.model;

import de.bytefish.fcmjava.model.topics.Topic;
import org.junit.Assert;
import org.junit.Test;

public class TopicTest {

    @Test
    public void testTopicName() {
        Topic topic = new Topic("name");

        Assert.assertEquals("name", topic.getName());
        Assert.assertEquals("/topics/name", topic.getTopicPath());
    }
}
