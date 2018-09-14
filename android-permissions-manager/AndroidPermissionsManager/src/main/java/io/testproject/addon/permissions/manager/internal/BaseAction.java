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

import io.testproject.java.sdk.v2.addons.helpers.AndroidAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.reporters.ActionReporter;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the base code of any of the actions in the addon.
 * Any action should have in common the methods in the base code.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class BaseAction {

    /**
     * Grant/Revoke listed permissions
     *
     * @param helper          The helper of the action
     * @param op              PermissionsState enum
     * @param permissionGroup Array of permissions
     *
     * @return ExecutionResult
     *
     * @throws FailureException Failed to toggle (grant/revoke) listed permissions
     *
     */
    protected ExecutionResult togglePermissionGroup(AndroidAddonHelper helper,
                                                    PermissionsState op,
                                                    String[] permissionGroup) throws FailureException {

        // Get the report object
        ActionReporter report = helper.getReporter();

        // Get the device UDID
        String deviceUDID = helper.getContext().getDriverSettings().getDeviceUdid();

        // Get the package name of the AUT
        String packageName = helper.getContext().getDriverSettings().getMobileSettings().getAndroidPackage();
        if (packageName.equals("")) {
            report.result("Failed reading the package name of the AUT"); // TODO: updating it in the future when name is available.
            return ExecutionResult.FAILED;
        }

        // Start connection with the device that the test running on
        ADBWrapper adbWrapper;
        try {
            adbWrapper = new ADBWrapper(helper);
        } catch (Exception e) {
            report.result("Failed connecting to the Android device " + deviceUDID + ". Error:" + e.toString());
            return ExecutionResult.FAILED;
        }

        List<String> succeedList = new ArrayList<>(); // List any permission that the action succeed to grant/revoke

        // Enable/Disable any permission in the group
        String grantRevoke = op.toString().toLowerCase();
        for (String permission : permissionGroup) {

            try {
                String adbOutput = adbWrapper.send("pm " + grantRevoke + " " + packageName + " android.permission." + permission);

                if (adbOutput.equals("")) succeedList.add(permission);
            } catch (Exception e) {
                throw new FailureException(String.format("Failed to %s the following permissions %s on device %s",
                        op.toString().toLowerCase(), String.join(",", permissionGroup), deviceUDID), e);
            } finally {
                // Terminate the connection with the device
                adbWrapper.terminate();
            }
        }

        // Report the result
        if (succeedList.size() > 0) {

            report.result(op.toString().toLowerCase() + " the following permissions: " + String.join(", ", succeedList));

            return ExecutionResult.PASSED;
        } else {

            report.result("Permissions list is empty");
            return ExecutionResult.FAILED;
        }
    }
}