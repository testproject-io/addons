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

import io.testproject.addon.slack.actions.common.SlackClientIntializatonException;
import io.testproject.addon.slack.actions.common.SlackHelper;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.ActionParameter;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.reporters.ActionReporter;

import javax.ws.rs.NotFoundException;

/**
 * This action sends Slack messages
 *
 * @author TestProject LTD.
 * @version 1.0
 */
@Action(name = "Send Slack message to user(s)", description = "Send Slack message to {{users}}")
public class SendSlackMessageAction implements WebAction {

    @ActionParameter(description = "OAuth Access Token")
    public String appToken = "";

    @ActionParameter(description = "Comma separated list of recipients usernames")
    public String toUsers = "";

    @ActionParameter(description = "Message body")
    public String message = "";

    public ExecutionResult execute(WebAddonHelper helper) {

        // Get the report object to report the result of the action to TestProject
        ActionReporter report = helper.getReporter();

        // Validate fields
        if (appToken.equals("")) {
            report.result("The AppToken field can't be empty");
            return ExecutionResult.FAILED;
        }

        if (toUsers.equals("")) {
            report.result("The ToUsers field can't be empty");
            return ExecutionResult.FAILED;
        }

        if (message.equals("")) {
            report.result("The message field can't be empty");
            return ExecutionResult.FAILED;
        }

        // initialize the helper object with the api token
        try (SlackHelper slackHelper = new SlackHelper(appToken)) {
            // Send the message
            slackHelper.sendMessage(message, toUsers);
            report.result("Sent message to users: {{toUsers}}");
            return ExecutionResult.PASSED;
        } catch (SlackClientIntializatonException e) {
            report.result(e.getMessage() + ": " + e.getCause().getMessage());
        } catch (NotFoundException e) {
            report.result(e.getMessage());
        } catch (Throwable e) {
            report.result("Failed to send message: " + e.toString());
        }

        return ExecutionResult.FAILED;
    }
}
