/*
 * Copyright 2018 TestProject LTD. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.testproject.addon.slack.actions.common;

import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Wrapper for Slack API
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class SlackHelper {

    private static final String SLACK_PREFIX = "https://hooks.slack.com/services/";

    /**
     * Send a POST request to the Slack webhook.
     *
     * @param url
     * @param msg
     * @return response code from the server
     * @throws IOException
     */
    public static int sendPOST(String url, String msg) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");

        OutputStream os = con.getOutputStream();
        os.write((msg).getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }

        return responseCode;
    }

    /**
     * Checks if a given URL is a valid Slack webhook URL.
     *
     * @param webhook
     * @return booloean
     */
    public static boolean validateSlackWebhook(String webhook) {
        return webhook.toLowerCase().startsWith(SLACK_PREFIX);
    }
}
