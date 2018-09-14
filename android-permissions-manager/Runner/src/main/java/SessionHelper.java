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
import io.testproject.java.classes.MobileDriverSettings;
import io.testproject.java.enums.DriverType;

public class SessionHelper {

    public static DriverSettings getDriverSettings() {
        DriverSettings driverSettings = new DriverSettings();

        // Declare that Android driver is required
        driverSettings.setDriverType(DriverType.Appium_Android);

        // Specify target device UUID
        // This information is conveniently visible at https://app.testproject.io/#/agents
        // Under your connected agent on the devices tab
        driverSettings.setDeviceUdid(System.getenv("DEVICE_UDID"));

        // Specify mobile app launch configuration
        // This information is conveniently visible in TestProject
        // when creating an Application in your Project
        MobileDriverSettings mobileSettings = new MobileDriverSettings();
        mobileSettings.setAndroidPackage(System.getenv("APP_PACKAGE"));
        mobileSettings.setAndroidActivity(System.getenv("APP_ACTIVITY"));
        driverSettings.setMobileSettings(mobileSettings);

        return driverSettings;
    }
}