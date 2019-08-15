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

package io.testproject.addon.imagecomparison.Actions.Android;

import io.appium.java_client.android.AndroidElement;
import io.testproject.addon.imagecomparison.Base.BaseCompareImages;
import io.testproject.java.annotations.v2.ElementAction;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.AndroidElementAction;
import io.testproject.java.sdk.v2.addons.helpers.AndroidAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import org.openqa.selenium.OutputType;

/**
 * This element action compares a given image with a taken Android element screenshot.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
@ElementAction(
        name = "Compare image with UI element",
        description = "Compare UI element with locally saved image, use 'Take element screenshot' to save the image locally first"
)
public class CompareImagesAndroid extends BaseCompareImages implements AndroidElementAction {

    @Parameter(description = "Local path to source image (for example: D:\\screenshots\\srcImage.jpg)")
    private String imagePath;

    /**
     * Default constructor.
     */
    public CompareImagesAndroid() {}

    /**
     * The action's execute method calls the base class's execute method to perform the action.
     * @param helper
     * @param element
     * @return
     * @throws FailureException
     */
    @Override
    public ExecutionResult execute(AndroidAddonHelper helper, AndroidElement element) throws FailureException {
        super.setImagePath(imagePath);
        super.setThreshold(threshold);
        super.setResultImagePath(resultImagePath);
        super.setImgName(imgName);

        try {
            super.compareElementImage(element.getScreenshotAs(OutputType.FILE), helper.getReporter());
        } catch (FailureException e) {
            helper.getReporter().result(e.getMessage());
            return ExecutionResult.FAILED;
        }

        return ExecutionResult.PASSED;
    }
}
