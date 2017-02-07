// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.fcmjava.client.tests;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.client.http.HttpClient;
import de.bytefish.fcmjava.http.client.IFcmClient;
import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
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

        // Create the HttpClient:
        HttpClient httpClient = new HttpClient(settings);

        // And configure the HttpClient:
        httpClient.configure((httpClientBuilder -> {

            // Define the Credentials to be used:
            BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();

            // Set the Credentials (any auth scope used):
            basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("your_username", "your_password"));

            httpClientBuilder
                    // Set the Proxy Address:
                    .setProxy(new HttpHost("your_hostname", 1234))
                    // Set the Authentication Strategy:
                    .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
                    // Set the Credentials Provider we built above:
                    .setDefaultCredentialsProvider(basicCredentialsProvider);
        }));

        // Finally build the FcmClient:
        IFcmClient client = new FcmClient(settings, httpClient);
    }
}
