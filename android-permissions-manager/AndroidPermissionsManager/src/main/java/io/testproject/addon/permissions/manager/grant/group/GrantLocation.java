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

package io.testproject.addon.permissions.manager.grant.group;

import io.testproject.addon.permissions.manager.internal.BaseAction;
import io.testproject.addon.permissions.manager.internal.PermissionsGroups;
import io.testproject.addon.permissions.manager.internal.PermissionsState;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.sdk.v2.addons.AndroidAction;
import io.testproject.java.sdk.v2.addons.helpers.AndroidAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

@Action(name = "Grant permissions for device location")
public class GrantLocation extends BaseAction implements AndroidAction {

    public ExecutionResult execute(AndroidAddonHelper helper) throws FailureException {
        return togglePermissionGroup(helper, PermissionsState.GRANT, PermissionsGroups.LOCATION);
    }
}