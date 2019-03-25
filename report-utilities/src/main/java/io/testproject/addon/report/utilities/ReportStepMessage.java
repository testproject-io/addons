package io.testproject.addon.report.utilities;/*
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


import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.reporters.ActionReporter;


/**
 * Reports the step message.
 */
@Action(name = "Report step message",
        description = "Report {{text}}",
        summary = "Reports a blank step execution with specified message")
public class ReportStepMessage implements GenericAction {

    @Parameter(description = "Text")
    private String text;

    public void setText(String text) { this.text = text; }

    /**
     * Reports a blank step execution with specified message
     * @param addonHelper
     * @return ExecutionResult
     * @throws FailureException
     */
    @Override
    public ExecutionResult execute(AddonHelper addonHelper) throws FailureException {
        // Get the Action Reporter
        ActionReporter reporter = addonHelper.getReporter();

        // Report the result of the action
        reporter.result(this.text);

        return ExecutionResult.PASSED;
    }
}
