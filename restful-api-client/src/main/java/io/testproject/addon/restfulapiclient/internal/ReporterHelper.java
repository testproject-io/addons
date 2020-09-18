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

package io.testproject.addon.restfulapiclient.internal;

import com.google.common.base.Strings;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.reporters.ActionReporter;

import java.lang.String;

/**
 * Action report builder - build the final report based on the server response
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class ReporterHelper {

    /**
     * Build the final result for the actions
     */
    public static ExecutionResult reportResult(ActionReporter report, ServerResponse serverResponse, String expectedResponseCode, String jsonPath) {

        ExecutionResult executionResult = ExecutionResult.PASSED;

        StringBuilder resultStr = new StringBuilder();

        final String LS = System.lineSeparator();

        // Build the report & Fail the test in case there is expectedResponseCode that does not match
        if (Strings.isNullOrEmpty(expectedResponseCode))
            resultStr.append("Server responded with status code: ").append(serverResponse.responseCode).append(". ").append(LS);
        else if (serverResponse.responseCode == Integer.parseInt(expectedResponseCode))
            resultStr.append("Server responded with the expected status code: ").append(serverResponse.responseCode).append(". ").append(LS);
        else {
            resultStr.append("Server responded with status code ").append(serverResponse.responseCode).append(" instead of expected ").append(expectedResponseCode)
                    .append(" status code.").append(LS);
            executionResult = ExecutionResult.FAILED;
        }

        // Build the report & Fail the test in case there is JSON path and an error has occurred in extracting the json path from the response body
        if (!jsonPath.isEmpty()) {
            if (serverResponse.jJsonParseErrorMsg == null) {
                resultStr.append("The value of \"").append(jsonPath).append("\" is \"").append(serverResponse.jsonParseResult).append("\". ").append(LS);
                resultStr.append("The value of \"").append(jsonPath).append("\" as JsonObject is \"").append(serverResponse.jsonParseResultAsJson).append("\". ").append(LS);
            } else {
                resultStr.append("Unable to get the value '").append(jsonPath).append("' due to an error: ").append(serverResponse.jJsonParseErrorMsg).append("\". ").append(LS);
                executionResult = ExecutionResult.FAILED;
            }
        }

        if (!serverResponse.responseBody.isEmpty())
            resultStr.append("Server returned response body: ").append(LS).append(serverResponse.responseBody);
        else
            resultStr.append("No body/value was returned by the server.").append(LS);


        report.result(resultStr.toString());

        return executionResult;
    }

}
