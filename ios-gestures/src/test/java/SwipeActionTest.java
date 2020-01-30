/*
 * Copyright 2019 TestProject LTD. and/or its affiliates
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
import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.testproject.addon.ios_gestures.SwipeAction;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.java.sdk.v2.drivers.IOSDriver;

public class SwipeActionTest {
    private static final String DEV_TOKEN = System.getenv("TESTPROJECT_DEV_KEY");
    private static final String DEVICE_UDID = System.getenv("IOS_DEVICE_ID");
    private static final String DEVICE_NAME = System.getenv("IOS_DEVICE_NAME");
    private static final String BUNDLE_ID = "io.cloudgrey.the-app";

    private static Runner runner;
    private static IOSDriver<IOSElement> driver;

    @BeforeAll
    static void setup() throws InstantiationException {
        runner = Runner.createIOS(DEV_TOKEN, DEVICE_UDID, DEVICE_NAME, BUNDLE_ID);
        driver = runner.getDriver();
    }

    @AfterAll
    static void tearDown() throws IOException {
        driver.quit();
        runner.close();
    }

    @Before
    void prepareApp() {
        driver.resetApp();
    }

    @Test
    void testSwipeAction() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("List Demo"))).click();

        // Create Action
        SwipeAction action = new SwipeAction();
        action.direction = "down";

        // Run action
        runner.run(action);
    }
}
