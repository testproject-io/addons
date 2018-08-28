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

package runner;

import io.testproject.addon.slack.actions.SendSlackMessageAction;
import io.testproject.java.classes.DriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.v2.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a runner to test the Addon Actions.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class ActionRunner {

    private static final Logger log = LoggerFactory.getLogger(ActionRunner.class);

    // Setting the development token
    private final static String DEV_TOKEN = System.getenv("DEV_TOKEN");

    public static void main(String[] args) {

        DriverSettings driverSettings = new DriverSettings(DriverType.Chrome);

        try (Runner runner = new Runner(DEV_TOKEN, driverSettings)) {

            SendSlackMessageAction action = new SendSlackMessageAction();
            action.appToken = System.getenv("SLACK_BOT_TOKEN"); // Slack API Token
            action.toUsers = "user1,user2,user3"; // Substitute with usernames of actual recipients separated by commas
            action.message = "Hello"; // Substitute with message body
            runner.run(action);

        } catch (Throwable e) {
            log.error("Action execution failed", e);
        }
    }

}