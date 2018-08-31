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
import io.testproject.java.classes.DriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.v2.drivers.WebDriver;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.addon.scrollelement.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionRunner {

    // Setting the development token
    private final static String DEV_TOKEN = System.getenv("DEV_TOKEN");

    public static void main(String[] args) throws Exception {

        Logger logger = LoggerFactory.getLogger(ActionRunner.class);

        // Creating driver settings to be used when running the test
        DriverSettings driverSettings = new DriverSettings(DriverType.Chrome);


        try (Runner runner = new Runner(DEV_TOKEN, driverSettings)) {
            WebDriver driver = null;

            // Scroll by class name
            ByClassName byClassName = new ByClassName();

            try {
                driver = runner.getDriver(byClassName);
            } catch (Exception e) {
                logger.debug("Error getting the driver from helper");
                System.exit(-1);
            }

            driver.navigate().to("https://testproject.io");

            // Run "Scroll to element by class name" action
            byClassName.className = "rs-header";
            runner.run(byClassName);

            Thread.sleep(2000);

            // Run "Scroll to element that contains text" action
            ByContainsText byContainsText = new ByContainsText();
            byContainsText.targetText = "Get the features you need to fully automate";
            runner.run(byContainsText);

            Thread.sleep(2000);

            // Run "Scroll to element by ID" action
            ByElementID byElementID = new ByElementID();
            byElementID.id = "recording-studio";
            runner.run(byElementID);

            Thread.sleep(2000);

            // Run "Scroll to element by ID that contain text" action
            ByIdContain byIdContain = new ByIdContain();
            byIdContain.targetText = "mobile-nav-";
            runner.run(byIdContain);

            Thread.sleep(2000);

            // Run "Scroll to element by xpath" action
            ByXpath byXpath = new ByXpath();
            byXpath.xpath = "//*[@id=\"recording-studio\"]/div/div[6]/div/h3";
            runner.run(byXpath);

            Thread.sleep(2000);

        } catch (Throwable e) {
            logger.debug("Action execution failed", e);
            System.exit(-1);
        }

    }
}