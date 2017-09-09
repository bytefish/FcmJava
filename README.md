# FcmJava #

## Table of Contents ##

* [Description](#description)
* [Maven Dependencies](#maven-dependencies)
* [Quickstart](#quickstart)
    * [FcmClient](#fcmclient)
    * [FcmClientSettings and API Key](#fcmclientsettings-and-api-key)
    * [Configuring a Proxy](#configuring-a-proxy)
* [FAQ](#quickstart)
    * [How to interpret the FCM Response Messages](#how-to-interpret-the-fcm-response-messages)
* [Android Client](#android-client)
* [Additional Resources](#additional-resources)

## Description ##

[FcmJava] is a library for working with the [Firebase Cloud Messaging (FCM) API].

## Maven Dependencies ##

You can add the following dependencies to your pom.xml to include [FcmJava] in your project.

```xml
<dependency>
  <groupId>de.bytefish.fcmjava</groupId>
  <artifactId>fcmjava-core</artifactId>
  <version>2.2</version>
</dependency>

<dependency>
  <groupId>de.bytefish.fcmjava</groupId>
  <artifactId>fcmjava-client</artifactId>
  <version>2.2</version>
</dependency>
```

## Quickstart ##

The Quickstart shows you how to work with [FcmJava].

### FcmClient ###

```java
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

    @Test
    @Ignore("This is an Integration Test using system properties to contact the FCM Server")
    public void SendTopicMessageTest() throws Exception {

        // Create the Client using system-properties-based settings:
        try (FcmClient client = new FcmClient(PropertiesBasedSettings.createFromDefault())) {

            // Message Options:
            FcmMessageOptions options = FcmMessageOptions.builder()
                    .setTimeToLive(Duration.ofHours(1))
                    .build();

            // Send a Message:
            TopicMessageResponse response = client.send(new TopicUnicastMessage(options, new Topic("news"), new PersonData("Philipp", "Wagner")));

            // Assert Results:
            Assert.assertNotNull(response);

            // Make sure there are no errors:
            Assert.assertNotNull(response.getMessageId());
            Assert.assertNull(response.getErrorCode());
        }
    }
}
```

### FcmClientSettings and API Key ###

The ``FcmClient`` can be instantiated with ``IFcmClientSettings`` to supply the API Key. By default the ``FcmClient`` uses the 
``PropertiesBasedSettings``, which locate the settings in a default location. If you need to supply the API Key in a different 
way, you can simply instantiate the ``FcmClient`` with a custom ``IFcmClientSettings`` implementation.

#### Using the PropertiesBasedSettings ####

By default the FCM API Key is read from an external ``.properties`` file called ``fcmjava.properties`` to ensure the API Key 
secret does not reside in code or leaks into the public. The default location of the ``fcmjava.properties`` is 
``System.getProperty("user.home") + "/.fcmjava/fcmjava.properties"``.

The file has to contain the FCM API Endpoint and the API Key:

```properties
fcm.api.url=https://fcm.googleapis.com/fcm/send
fcm.api.key=<YOUR_API_KEY_HERE>
```

If the properties are available in the default location you can simply instantiate the ``FcmClient``as seen in the example.

You can use the ``PropertiesBasedSettings`` class to read the Properties and pass them into the ``FcmClient``, if the Properties path differs from the default path:

1. ``PropertiesBasedSettings.createFromDefault()``
    * Uses the default file location of ``System.getProperty("user.home") + "/.fcmjava/fcmjava.properties"`` to read the properties. This is the recommended way of reading your API Key.
2. ``PropertiesBasedSettings.createFromFile(Path path, Charset charset)``
    * Uses a custom file location to read the settings from.
3. ``PropertiesBasedSettings.createFromSystemProperties()``
    * Uses the System Properties to initialize the settings.
4. ``PropertiesBasedSettings.createFromProperties(Properties properties)``
    * Uses the supplied Properties to build the FcmSettings.

#### Implementing the IFcmClientSettings interface ####

It's not neccessary to use the ``PropertiesBasedSettings`` for supplying an API Key to the ``FcmClient``. You can easily implement the ``IFcmClientSettings`` interface 
and pass it into the ``FcmClient``.

The following test shows a simple ``IFcmClientSettings`` implementation, that will be instantiated with the given API Key. Again I strongly suggest to not hardcode the 
Firebase Cloud Messaging API Key in code. This makes it possible to accidentally leak your credentials into public.

```java
// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.tests.settings;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.constants.Constants;
import de.bytefish.fcmjava.http.client.IFcmClient;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import org.junit.Test;

class FixedFcmClientSettings implements IFcmClientSettings {

    private final String apiKey;

    public FixedFcmClientSettings(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getFcmUrl() {
        return Constants.FCM_URL;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }
}

public class FcmClientSettingsTest {

    @Test
    public void testFixedClientSettings() {

        // Construct the FCM Client Settings with your API Key:
        IFcmClientSettings clientSettings = new FixedFcmClientSettings("your_api_key_here");

        // Instantiate the FcmClient with the API Key:
        IFcmClient client = new FcmClient(clientSettings);
    }

}
```

### Configuring a Proxy ###

[Apache HttpClient]: http://hc.apache.org/httpcomponents-client-ga/
[HttpClientBuilder]: http://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/impl/client/HttpClientBuilder.html

[FcmJava] uses [Apache HttpClient] for making requests to the Firebase Cloud Messaging server. So in order to configure 
a proxy for the HTTP requests, you can configure the [HttpClientBuilder] used in [FcmJava]. This is done by instantiating 
the ``DefaultHttpClient`` with your configured [HttpClientBuilder].

The following test shows how to build the ``FcmClient`` with a custom ``HttpClient``, which configures a Proxy for the [HttpClientBuilder].

```java
// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.tests;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.client.http.apache.DefaultHttpClient;
import de.bytefish.fcmjava.http.client.IFcmClient;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.junit.Test;

class FakeFcmClientSettings implements IFcmClientSettings {

    @Override
    public String getFcmUrl() {
        return "";
    }

    @Override
    public String getApiKey() {
        return "";
    }
}

public class HttpBuilderConfigurationTest {


    @Test
    public void testFcmClientWithProxySettings() {

        // Create Settings:
        IFcmClientSettings settings = new FakeFcmClientSettings();

        // Define the Credentials to be used:
        BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();

        // Set the Credentials (any auth scope used):
        basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("your_username", "your_password"));

        // Create the Apache HttpClientBuilder:
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                // Set the Proxy Address:
                .setProxy(new HttpHost("your_hostname", 1234))
                // Set the Authentication Strategy:
                .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
                // Set the Credentials Provider we built above:
                .setDefaultCredentialsProvider(basicCredentialsProvider);

        // Create the DefaultHttpClient:
        DefaultHttpClient httpClient = new DefaultHttpClient(settings, httpClientBuilder);

        // Finally build the FcmClient:
        IFcmClient client = new FcmClient(settings, httpClient);
    }
}
```

## FAQ ##

### How to interpret the FCM Response Messages ###

[Issue #30](https://github.com/bytefish/FcmJava/issues/30) explains how to interpret the FCM response message and handle errors.

The user [@yakuninv](https://github.com/yakuninv) asks how to handle an errorneous ``FcmMessageResultItem`` in the ``FcmMessageResponse``:

> As part of error handling when sending ``NotificationMulticastMessage`` I need to map an erroneous ``FcmMessageResultItem`` to the token that caused an error. A typical use case is to remove not registered tokens from my Database.
> 
> Can I rely on the order of ``FcmMessageResultItems`` in the ``FcmMessageResponse``? Does it correspond to the order of ``registrationIds`` provided in the constructor of the ``NotificationMulticastMessage``?

The user [@culebras](https://github.com/culebras) has written a good summary:

> Take a look into this thread, I think it will answer your question (looks like **the order is the same**):
> 
> * https://stackoverflow.com/questions/40518125/wich-fcm-registration-id-has-failed-when-targeted-for-multiple-registration-ids
> 
> - So for the `FcmMessageResultItems` with `errorCode` equals to `NotRegistered` you will want to remove those tokens for your DB.
> - For the `FcmMessageResultItems` with `errorCode` equals to `Unavailable` maybe you will want to resend the message for those tokens.
> - For the `FcmMessageResultItems` with `registration_id` not `null`, you will want to update the tokens in your  DB (I think that the new `registration_id` can be obtained in this library from `FcmMessageResultItem.getCanonicalRegistrationId`).
> - Etc.
> 
> But, regarding to the Canonical IDs, you have to consider this:
> 
> * https://stackoverflow.com/questions/45018247/android-google-fcm-canonical-ids-how-to-reproduce-in-non-production-or-tests
> 
> Because it looks like that:
> 
> > In FCM, it seems the Canonical IDs are no longer used (or at the very least extremely rarely) because of how the Instance ID service works. To put it simply, the service works that there would only be one valid token per App Instance.
> 
> So the updating of tokens in your DB for those tokens which are refreshed in FCM cloud would be mostly done in the method `onTokenRefresh()` in your Android client application (in this method is where the device should send the token to your DB the first time the device register itself to the FCM and also when the token is refreshed in FCM).
> 
> So, it seems that is not very likely that you are going to receive `FcmMessageResultItem` with `registration_id` not `null`, but anyway, it is good idea to also expect this and update the canonical token of those `FcmMessageResultItem`.

## Android Client ##

I have decided to clone the messaging quickstart sample of Google, which is available at:

* [https://github.com/firebase/quickstart-android/tree/master/messaging](https://github.com/firebase/quickstart-android/tree/master/messaging)

Now first subscribe to the ``news`` topic, then execute the above [FcmJava] application. 

The Android app will now receive a message with the sent data included:

```
09-17 21:10:45.250 10882-11300/com.google.firebase.quickstart.fcm D/MyFirebaseMsgService: From: /topics/news
09-17 21:10:45.251 10882-11300/com.google.firebase.quickstart.fcm D/MyFirebaseMsgService: Message data payload: {lastName=Wagner, firstName=Philipp}
```

## Additional Resources ##

* [Send messages from Spring Boot to Ionic 2 over FCM](https://golb.hplar.ch/p/Send-messages-from-Spring-Boot-to-Ionic-2-over-FCM)

[FcmJava]: https://github.com/bytefish/FcmJava
[Firebase Cloud Messaging (FCM) API]: https://firebase.google.com
