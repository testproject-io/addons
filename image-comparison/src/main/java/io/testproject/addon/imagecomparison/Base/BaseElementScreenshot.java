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

package io.testproject.addon.imagecomparison.Base;

import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.reporters.Reporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This base class contains the logic of the take screenshot action for Android, iOS and Web.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class BaseElementScreenshot {

    @Parameter(description = "Local path to save the screenshot (for example: D:\\screenshots)")
    public String path;

    @Parameter(description = "File name (default: current timestamp)")
    public String imgName;

    @Parameter(description = "Full path on local drive (for example: D:\\screenshots\\result.jpg)", direction = ParameterDirection.OUTPUT)
    public String resultFullPath;

    // Setters
    public void setPath(String path) { this.path = path; }

    public void setImgName(String imgName) { this.imgName = imgName; }

    /**
     * Default constructor.
     */
    public BaseElementScreenshot() {}

    /**
     * Executes the element's screenshot capture logic.
     *
     * @param reporter
     * @param element
     * @return
     * @throws FailureException
     */
    protected ExecutionResult execute(Reporter reporter, WebElement element) throws FailureException {
        if (!validateParameters()) {
            throw new FailureException("You must provide a local path to save the screenshot");
        }

        File screenshot = element.getScreenshotAs(OutputType.FILE);
        try {
            resultFullPath = path + File.separator + imgName;
            FileUtils.copyFile(screenshot, new File(resultFullPath));
        } catch (IOException e) {
            throw new FailureException("Failed to take element screenshot.");
        }

        reporter.result(String.format("Element screenshot was stored in %s.", resultFullPath));
        return ExecutionResult.PASSED;
    }

    /**
     * Validates the input parameters of the action. Throws a FailureException if the image path is empty.
     */
    private boolean validateParameters() {
        if (StringUtils.isEmpty(path))
            return false;

        String timestamp = new SimpleDateFormat("yyyyMMddHHmm'.jpg'").format(new Date());
        imgName = (StringUtils.isEmpty(imgName)) ? "screenshot_" + timestamp : imgName + ".jpg";

        return true;
    }
}
