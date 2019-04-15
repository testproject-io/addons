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
package io.testproject.addon.slack.actions;

import com.google.gson.Gson;
import io.testproject.addon.slack.actions.common.SlackHelper;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.reporters.ActionReporter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This action sends Slack text to an incoming webhook.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
@Action(name = "Message Slack",
        description = "Slack {{text}} using {{webhook}}",
        summary = "Sends a text on Slack using incoming WebHook")
public class SendSlackMessageAction implements GenericAction {

    @Parameter(description = "Webhook URL")
    public String webhook;

    @Parameter(description = "Message content")
    public String text;

    @Parameter(description = "Color in Hexa-decimal format e.g. #36a64f (optional)")
    public String color;

    @Parameter(description = "Title (optional)")
    public String title;

    @Parameter(description = "Title link (optional)")
    public String title_link;

    @Parameter(description = "Author (optional)")
    public String author;

    @Parameter(description = "Additional text (optional)")
    public String additional_text;

    public SendSlackMessageAction() {}

    /**
     * Convenience constructor for JUnit tests only.
     *
     * @param webhook
     * @param text
     * @param color
     * @param title
     * @param title_link
     * @param author
     * @param additional_text
     */
    public SendSlackMessageAction(String webhook, String text, String color, String title, String title_link, String author, String additional_text) {
        this.webhook = webhook;
        this.text = text;
        this.color = color;
        this.title = title;
        this.title_link = title_link;
        this.author = author;
        this.additional_text = additional_text;
    }

    @Override
    public ExecutionResult execute(AddonHelper addonHelper) throws FailureException {

        ActionReporter reporter = addonHelper.getReporter();

        // Validate fields
        if (StringUtils.isEmpty(this.webhook)) {
            reporter.result("You must provide a webhook URL");
            return ExecutionResult.FAILED;
        }

        if (!SlackHelper.validateSlackWebhook(this.webhook)) {
            reporter.result("Invalid Slack webhook URL");
            return ExecutionResult.FAILED;
        }

        if (StringUtils.isEmpty(this.text)) {
            reporter.result("Message can not be empty");
            return ExecutionResult.FAILED;
        }

        // Send the text to the URL endpoint
        try {
            int responseCode = SlackHelper.sendPOST(webhook, createPayload());

            if (responseCode != 200) {
                reporter.result("Server returned invalid response code: " + responseCode);
                return ExecutionResult.FAILED;
            }
        } catch (IOException e) {
            throw new FailureException("Failed sending Slack text", e);
        }

        reporter.result("The text was sent successfully");
        return ExecutionResult.PASSED;
    }

    /**
     * Creates the JSON payload for the POST request.
     *
     * @return payload
     */
    public String createPayload() {

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("text", this.text);

        List<Object> attachments = new ArrayList();
        payload.put("attachments", attachments);

        HashMap<String, String> attachment = new HashMap<>();
        attachments.add(attachment);

        attachment.put("title", this.title);
        attachment.put("title_link", this.title_link);
        attachment.put("author", this.author);
        attachment.put("additional_text", this.additional_text);
        attachment.put("color", StringUtils.isEmpty(this.color) ? "#36a64f" : this.additional_text);

        return new Gson().toJson(payload);
    }
}
