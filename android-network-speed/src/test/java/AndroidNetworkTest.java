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
import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.testproject.addon.android_network.AndroidNetworkAction;
import io.testproject.java.sdk.v2.Runner;

public class AndroidNetworkTest {
    private static final String DEV_TOKEN = System.getenv("TESTPROJECT_DEV_KEY");
    private static final String DEVICE_UDID = System.getenv("ANDROID_DEVICE_ID");
    private static final String PACKAGE_NAME = "com.android.chrome";
    private static final String ACTIVITY_NAME = "org.chromium.chrome.browser.ChromeTabbedActivity";

    private static Runner runner;
    private static AndroidDriver<AndroidElement> driver;

    @BeforeAll
    static void setup() throws InstantiationException {
        runner = Runner.createAndroid(DEV_TOKEN, DEVICE_UDID, PACKAGE_NAME, ACTIVITY_NAME);
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
    void testNetworkAction() throws Exception {
        AndroidNetworkAction action = new AndroidNetworkAction();
        By waitEl = By.id("mm-0");
        WebDriverWait wait = new WebDriverWait(driver, 45);
        // make sure we start at full speed
        action.speed = "FULL";
        runner.run(action);

        for (String ctx : driver.getContextHandles()) {
            if (ctx != "NATIVE_APP") {
                driver.context(ctx);
                break;
            }
        }

        long start = System.currentTimeMillis();
        // set up browser
        driver.get("https://testproject.io");
        wait.until(ExpectedConditions.presenceOfElementLocated(waitEl));
        long fullSpeedLoadTime = System.currentTimeMillis() - start;

        action.speed = "GPRS";
        runner.run(action);

        // Verify
        start = System.currentTimeMillis();
        driver.get("https://testproject.io");
        wait.until(ExpectedConditions.presenceOfElementLocated(waitEl));
        long gprsLoadTime = System.currentTimeMillis() - start;

        // make sure we end at full speed
        action.speed = "FULL";
        runner.run(action);

        assert(gprsLoadTime > fullSpeedLoadTime);
    }
}
