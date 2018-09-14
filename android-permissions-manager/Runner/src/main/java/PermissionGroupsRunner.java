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

import io.testproject.addon.permissions.manager.revoke.group.*;
import io.testproject.addon.permissions.manager.grant.group.*;
import io.testproject.java.sdk.v2.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runner for grant/revoke actions based on predefined groups
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class PermissionGroupsRunner {

    private static final Logger log = LoggerFactory.getLogger(PermissionGroupsRunner.class);

    // Setting the development token
    private static final String DEV_TOKEN = System.getenv("DEV_TOKEN");

    public static void main(String[] args) {

        // Running the test using TestProjectRunner
        try (Runner runner = new Runner(DEV_TOKEN, SessionHelper.getDriverSettings())) {

            // You can run here any of the other actions

            // Grand actions
            runGrantStorage(runner);
            runGrantContacts(runner);
            runGrantLocation(runner);
            runGrantMedia(runner);
            runGrantPhoneCalls(runner);
            runGrantSensors(runner);
            runGrantSMS(runner);
            runGrantStorage(runner);

            // Revoke actions
            runRevokeStorage(runner);
            runRevokeContacts(runner);
            runRevokeLocation(runner);
            runRevokeMedia(runner);
            runRevokePhoneCalls(runner);
            runRevokeSensors(runner);
            runRevokeSMS(runner);
            runRevokeStorage(runner);
        } catch (Throwable e) {
            log.error("Action execution failed", e);
        }
    }

    // Actions to grant permissions by group
    private static void runGrantStorage(Runner runner) {
        GrantStorage action = new GrantStorage();
        runner.run(action);
    }

    private static void runGrantContacts(Runner runner) {
        GrantContacts action = new GrantContacts();
        runner.run(action);
    }

    private static void runGrantLocation(Runner runner) {
        GrantLocation action = new GrantLocation();
        runner.run(action);
    }

    private static void runGrantMedia(Runner runner) {
        GrantMedia action = new GrantMedia();
        runner.run(action);
    }

    private static void runGrantPhoneCalls(Runner runner) {
        GrantPhoneCalls action = new GrantPhoneCalls();
        runner.run(action);
    }

    private static void runGrantSensors(Runner runner) {
        GrantSensors action = new GrantSensors();
        runner.run(action);
    }

    private static void runGrantSMS(Runner runner) {
        GrantSMS action = new GrantSMS();
        runner.run(action);
    }

    // Actions to revoke permissions by group
    private static void runRevokeContacts(Runner runner) {
        RevokeContacts action = new RevokeContacts();
        runner.run(action);
    }

    private static void runRevokeLocation(Runner runner) {
        RevokeLocation action = new RevokeLocation();
        runner.run(action);
    }

    private static void runRevokeMedia(Runner runner) {
        RevokeMedia action = new RevokeMedia();
        runner.run(action);
    }

    private static void runRevokeNetwork(Runner runner) {
        RevokeNetwork action = new RevokeNetwork();
        runner.run(action);
    }

    private static void runRevokePhoneCalls(Runner runner) {
        RevokePhoneCalls action = new RevokePhoneCalls();
        runner.run(action);
    }

    private static void runRevokeSensors(Runner runner) {
        RevokeSensors action = new RevokeSensors();
        runner.run(action);
    }

    private static void runRevokeSMS(Runner runner) {
        RevokeSMS action = new RevokeSMS();
        runner.run(action);
    }

    private static void runRevokeStorage(Runner runner) {
        RevokeStorage action = new RevokeStorage();
        runner.run(action);
    }
}