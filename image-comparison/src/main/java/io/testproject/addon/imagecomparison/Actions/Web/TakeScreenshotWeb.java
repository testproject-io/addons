/*
 * Copyright 2019 TestProject LTD. and/or its affiliates
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

package io.testproject.addon.imagecomparison.Actions.Web;

import io.testproject.addon.imagecomparison.Base.BaseElementScreenshot;
import io.testproject.java.annotations.v2.ElementAction;
import io.testproject.java.sdk.v2.addons.WebElementAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import org.openqa.selenium.WebElement;

/**
 * This element action takes a screenshot of the selected Web element.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
@ElementAction(
        name = "Take element screenshot",
        description = "Takes a screenshot of an element and saves a copy to a local path"
)
public class TakeScreenshotWeb extends BaseElementScreenshot implements WebElementAction {

    /**
     * Default constructor.
     */
    public TakeScreenshotWeb() {}

    /**
     * The action's execute method calls the base class's execute method to perform the action.
     * @param helper
     * @param element
     * @return
     * @throws FailureException
     */
    @Override
    public ExecutionResult execute(WebAddonHelper helper, WebElement element) throws FailureException {
        try {
            super.execute(helper.getReporter(), element);
        } catch (FailureException e) {
            helper.getReporter().result(e.getMessage());
            return ExecutionResult.FAILED;
        }

        return ExecutionResult.PASSED;
    }
}
