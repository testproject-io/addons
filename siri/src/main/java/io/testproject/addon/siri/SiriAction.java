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
package io.testproject.addon.siri;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.IOSAction;
import io.testproject.java.sdk.v2.addons.helpers.IOSAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

@Action(name = "Siri Command",
        description = "Send the message '{{message}}' to Siri",
        summary = "Sends a message to Siri emulating user voice input")
public class SiriAction implements IOSAction {

    @Parameter(description = "The message/command to send")
    public String message = "";

    @Override
    public ExecutionResult execute(IOSAddonHelper helper) throws FailureException {
        IOSDriver<IOSElement> driver = helper.getDriver();
        driver.executeScript("mobile: siriCommand", ImmutableMap.of("text", message));
        return ExecutionResult.PASSED;
    }

}
