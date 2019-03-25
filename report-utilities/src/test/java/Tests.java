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


import io.testproject.addon.report.utilities.ReportStepMessage;
import io.testproject.java.enums.AutomatedBrowserType;
import io.testproject.java.sdk.v2.Runner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Tests {

    // Declare the Runner object
    private static Runner runner;

    // Setting the development token
    private final static String devToken = System.getenv("DEV_TOKEN");

    /**
     * Initializing the Runner instance
     * @throws InstantiationException
     */
    @BeforeAll
    public static void init() throws InstantiationException {
        runner = Runner.createWeb(devToken, AutomatedBrowserType.Chrome);
    }

    /**
     * Terminating the Runner instance
     * @throws IOException
     */
    @AfterAll
    public static void tearDown() throws IOException {
        runner.close();
    }

    /**
     * Executing the test
     * @throws Exception
     */
    @Test
    public void runReportStepMessage() throws Exception {

        // Create new instance of the ReportStepMessage class
        ReportStepMessage reportStepMessage = new ReportStepMessage();

        // Setting the text parameter for the ReportStepMessage instance
        reportStepMessage.setText("Some text");

        // Run the test
        runner.run(reportStepMessage);
    }
}
