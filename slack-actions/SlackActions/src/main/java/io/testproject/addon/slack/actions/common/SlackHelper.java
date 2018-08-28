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

import com.google.gson.*;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.HttpURLConnection;

import io.testproject.java.sdk.v2.exceptions.FailureException;

/**
 * Wrapper for Slack API
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class SlackHelper implements AutoCloseable {

    private String appToken;

    private Client client = null;

    private JsonObject usersInfo = null;

    public SlackHelper(String appToken) throws SlackClientIntializatonException {
        this.appToken = appToken;
        try {
            client = ClientBuilder.newClient();
            initTeamUsers();
        } catch (Exception e) {
            throw new SlackClientIntializatonException("Failed to initialize API client", e);
        }
    }

    /**
     * Initialize a local cache of all users in a Slack team
     */
    private void initTeamUsers() throws RuntimeException {
        Response response = client.target("https://slack.com/api/users.list?token=" + appToken).request(MediaType.APPLICATION_JSON).get();
        usersInfo = readSlackSuccessResponse(response, HttpURLConnection.HTTP_OK);
    }

    /**
     * Return comma separated string that contain list of users ids
     * to use it later while creating channel ID
     *
     * @param users comma separated string that contain list of user names
     * @return comma separated string that contain list of users ids
     * @throws NotFoundException - None of the users were found
     */
    private String getUserIds(String users) throws NotFoundException {

        List<String> listUserNames = new ArrayList<>(Arrays.asList(users.split("\\s*,\\s*")));
        List<String> userIds = new ArrayList<>();

        // Iterate over all team members
        for (JsonElement jsonElement : usersInfo.getAsJsonArray("members")) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            // Check if the member have "real_name" and "id" keys
            if (!jsonObject.has("real_name")) continue;
            if (!jsonObject.has("id")) continue;

            // Iterate over all usernames
            for (String userName : listUserNames) {

                // Check for matches
                if (jsonObject.get("real_name").getAsString().equals(userName)) {

                    // Add the user id to the list of ids
                    userIds.add(jsonObject.get("id").getAsString());

                    // Remove that user (marks it as processed)
                    listUserNames.remove(userName);

                    // Exit the loop
                    break;
                }
            }
        }

        // Check if there are any unprocessed users
        if (listUserNames.size() > 0)
            throw new NotFoundException("None of the users in provided list were found in the Team");

        // Return comma separated result for caller convenience
        return String.join(",", userIds);
    }

    /**
     * Create a new conversation
     *
     * @param toUsers comma separated string that contain list of user names
     * @return The conversation ID
     * @throws RuntimeException - could not get the conversation ID
     */
    private String createConversation(String toUsers) throws RuntimeException {

        // Get comma separated string that contain list of users ids
        String userIds = getUserIds(toUsers);

        // Open the conversation
        Response response;
        try {
            response = client.target("https://slack.com/api/conversations.open")
                    .queryParam("token",appToken)
                    .queryParam("users",userIds)
                    .request(MediaType.APPLICATION_JSON).post(Entity.entity(new Form(), MediaType.APPLICATION_JSON));
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to send request. Exception: " + e.toString());
        }

        JsonObject slackResponse;
        try {
            slackResponse = readSlackSuccessResponse(response, HttpURLConnection.HTTP_OK);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create conversation, " + e.getMessage());
        }

        if (!slackResponse.has("channel"))
            throw new RuntimeException("channel id was not found");

        JsonObject channelJson = slackResponse.get("channel").getAsJsonObject();

        if (!channelJson.has("id"))
            throw new RuntimeException("id not found");

        // Return the conversation id
        return channelJson.get("id").getAsString();
    }

    /**
     * Send message to user(s)
     *
     * @param message The message to send
     * @param toUsers the target users to send message to
     * @throws Exception - an error has occurred. see exception message for more details
     */
    public void sendMessage(String message, String toUsers) throws RuntimeException {

        // Get the channel of the conversation
        String channel = createConversation(toUsers);

        // Send the message to the channel
        Response response;
        try {
            response = client.target("https://slack.com/api/chat.postMessage")
                    .queryParam("token",appToken)
                    .queryParam("channel",channel)
                    .queryParam("text",message)
                    .request(MediaType.APPLICATION_JSON).post(Entity.entity(new Form(), MediaType.APPLICATION_JSON));

        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to send request to the server. Exception: " + e.toString());
        }

        try {
            readSlackSuccessResponse(response, HttpURLConnection.HTTP_OK);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to send message, " + e.getMessage());
        }
    }

    /**
     * Examine Slack API response and return the JSON
     *
     * @param response    The response from the server
     * @param successCode The expected success code
     * @throws FailureException - The slack server responded with unsuccessful response
     */
    private JsonObject readSlackSuccessResponse(Response response, int successCode) throws RuntimeException {
        if (response.getStatus() != successCode)
            throw new RuntimeException("The server response code is not the expected response. server responded with code " + response.getStatus());

        JsonObject json = new Gson().fromJson(response.readEntity(String.class), JsonObject.class);

        if (!json.get("ok").getAsString().equals("true")) {

            if (json.has("error"))
                throw new RuntimeException("Slack server responded with an error: " + json.get("error").toString());
            else
                throw new RuntimeException("The server responded failure");
        }

        return json;
    }

    /**
     * Clean the resources
     */
    public void close() {
        if (client != null) client.close();
    }
}
