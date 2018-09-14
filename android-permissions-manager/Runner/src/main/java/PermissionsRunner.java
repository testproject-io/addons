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

import io.testproject.addon.permissions.manager.grant.GrantPermissions;
import io.testproject.addon.permissions.manager.revoke.RevokePermissions;
import io.testproject.java.sdk.v2.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Runner for grant/revoke actions based on user’s input
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class PermissionsRunner {

    private static final Logger log = LoggerFactory.getLogger(PermissionsRunner.class);

    // Setting the development token
    private static final String DEV_TOKEN = System.getenv("DEV_TOKEN");

    public static void main(String[] args) {

        // Running the test using TestProjectRunner
        try (Runner runner = new Runner(DEV_TOKEN, SessionHelper.getDriverSettings())) {

            // An example for permissions list
            String selectedPermissions = "READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE";
            // For list of all available permissions: https://developer.android.com/reference/android/Manifest.permission

            runGrantPermissions(runner, selectedPermissions);
            runRevokePermissions(runner, selectedPermissions);
        } catch (Throwable e) {
            log.error("Action execution failed", e);
        }
    }

    // Enable permissions by group
    private static void runRevokePermissions(Runner runner, String permissions) {
        GrantPermissions action = new GrantPermissions();
        action.permissions = permissions;
        runner.run(action);
    }

    // Disable permissions by group
    private static void runGrantPermissions(Runner runner, String permissions) {
        RevokePermissions action = new RevokePermissions();
        action.permissions = permissions;
        runner.run(action);
    }
}