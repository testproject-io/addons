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
package io.testproject.addon.scrollelement;

import io.testproject.addon.scrollelement.common.BaseAction;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.ActionParameter;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import org.openqa.selenium.By;

@Action(name = "Scroll to element by ID that contain text")
public class ByIdContain extends BaseAction implements WebAction {

    @ActionParameter(description = "Part of the text tat appears inside the element")
    public String targetText;

    public ExecutionResult execute(WebAddonHelper helper) {
        return performScroll(helper, By.xpath("//*[contains(@id,'" + targetText + "')]"),
                "Scrolled to element by ID that contain text");
    }
}