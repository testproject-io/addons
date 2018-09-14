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

package io.testproject.addon.permissions.manager.internal;

import com.android.ddmlib.*;
import io.testproject.java.classes.DriverSettings;
import io.testproject.java.sdk.v2.addons.helpers.AndroidAddonHelper;
import io.testproject.java.sdk.v2.exceptions.FailureException;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

/**
 * Provides methods to get ADB connection with the android device that the test running on
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class ADBWrapper {

    private IDevice currentDevice;
    private String deviceUdid;

    /**
     * Initialization of the ADB connection with the android device that the test running on
     *
     * @param helper The helper of the action
     *
     * @throws TimeoutException - Timed out while waiting for a list of connected devices
     * @throws FailureException - Action execution interrupted
     * @throws NullPointerException - The UDID of the android device is null
     */
    public ADBWrapper(AndroidAddonHelper helper) throws TimeoutException, NullPointerException, FailureException {

        // Init ADB
        AndroidDebugBridge.init(false);
        AndroidDebugBridge adb = AndroidDebugBridge.createBridge();
        if (adb == null)
            throw new NullPointerException("Failed to create adb bridge");

        // Wait for the devices list from the ADB
        long startTime = System.currentTimeMillis();
        while (!adb.isConnected() || !adb.hasInitialDeviceList()) {
            if (System.currentTimeMillis() - startTime >= 10000)
                throw new TimeoutException("Error occurred while loading devices list");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new FailureException("Action execution interrupted",e);
            }
        }

        // Get the android device that the test running on
        DriverSettings driverSettings = helper.getContext().getDriverSettings();
        if (driverSettings == null)
            throw new NullPointerException("Cannot get the driver settings");

        deviceUdid = driverSettings.getDeviceUdid();
        if (deviceUdid == null)
            throw new NullPointerException("Cannot get the UDID of the android device");

        IDevice[] allDevices = adb.getDevices();
        currentDevice = null;

        for (IDevice idevice : allDevices) {
            if (idevice.getSerialNumber().equals(deviceUdid)) {
                currentDevice = idevice;
                break;
            }
        }

        if (currentDevice == null)
            throw new NoSuchElementException("The android device " + deviceUdid + " was not found");
    }

    /**
     * Initialization of the ADB connection with the android device that the test running on
     *
     * @param adbCommand The ADB command to send to the android device
     *
     * @return The output of the ADB command
     *
     * @throws FailureException Failed to run ADB command on the device
     */
    public String send(String adbCommand) throws FailureException {

        StringBuilder resultBuilder = new StringBuilder();

        try {
            currentDevice.executeShellCommand(adbCommand, new MultiLineReceiver() {
                @Override
                public void processNewLines(String[] lines) {
                    for (String line : lines) {
                        resultBuilder.append(line).append(System.lineSeparator());
                    }
                }

                @Override
                public boolean isCancelled() {
                    return false;
                }
            });
        } catch (Exception e) {
            throw new FailureException("Failed to run ADB command \""+adbCommand+"\" on device \""+deviceUdid+"\"", e);
        }
        return resultBuilder.toString();
    }

    /**
     * Terminate the connection with the android device that the test running on
     */
    public void terminate() {
        AndroidDebugBridge.terminate();
    }
}
