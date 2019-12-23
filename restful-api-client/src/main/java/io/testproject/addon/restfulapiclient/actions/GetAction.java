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
package io.testproject.addon.restfulapiclient.actions;

import io.testproject.addon.restfulapiclient.actions.base.BaseAction;
import io.testproject.addon.restfulapiclient.internal.*;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

/**
 * This actions sends GET request
 *
 * @author TestProject LTD.
 */
@Action(name = "HTTP GET Request", description = "GET {{uri}}?{{query}}")
public class GetAction extends BaseAction implements GenericAction {
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        return baseExecute(helper, RequestMethod.GET, null, null);
    }
}
