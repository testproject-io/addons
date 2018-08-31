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
package io.testproject.addon.scrollelement.common;

import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.drivers.WebDriver;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.reporters.ActionReporter;
import org.openqa.selenium.*;

/**
 * Preform the core operations of the actions
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class BaseAction {

    /**
     * Preform the actual scroll of the action
     *
     * @param helper         The WebAddonHelper object
     * @param by             By object for how to find the element
     * @param successMessage The success message that reported to TestProject when the action
     *                       succeed to preform the operation
     * @return io.testproject.java.sdk.v2.enums.ExecutionResult
     */
    protected ExecutionResult performScroll(WebAddonHelper helper, By by, String successMessage) {
        WebDriver driver = helper.getDriver();
        ActionReporter report = helper.getReporter();
        try {
            WebElement webElement = driver.findElement(by);
            driver.executeScript("arguments[0].scrollIntoView();", webElement);
            report.result(successMessage);
            return ExecutionResult.PASSED;
        } catch (NoSuchElementException e) {
            report.result("Problem looking for the element.\n" +
            "Make sure that you wrote the correct element in the action");
            return ExecutionResult.FAILED;
        } catch (JavascriptException e) {
            report.result("Error scrolling to the element - " +
                    "An error occurred while executing JavaScript code to scroll to the element");
            return ExecutionResult.FAILED;
        }
        catch (Exception e) {
            report.result("Unknown error: " + e.toString());
            return ExecutionResult.FAILED;
        }
    }

    /**
     * Helper method to escape double quotes.
     * It is used by actions such as ByContainsText
     *
     * @param item The WebAddonHelper object
     * @return Escaped string
     */
    protected String resolveAprostophes(String item) {
        if (!item.contains("'")) {
            return "'" + item + "'";
        }
        StringBuilder finalString = new StringBuilder();
        finalString.append("concat('");
        finalString.append(item.replace("'", "',\"'\",'"));
        finalString.append("')");
        return finalString.toString();
    }
}
