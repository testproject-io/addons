package io.testproject.addon.imagecomparison.Actions.Generic;

import io.testproject.addon.imagecomparison.Base.BaseCompareImages;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

@Action(
        name = "Compare two images",
        description = "Compare {{imgA}} with {{imgB}}",
        summary = "Compare two locally stored images and returns the diff, use 'Take element screenshot' to save the image locally first")
public class CompareTwoImages extends BaseCompareImages implements GenericAction {

    @Parameter(description = "Local path to first image (for example: C:\\Users\\Desktop\\img1.jpg)")
    private String imgA;

    @Parameter(description = "Local path to second image (for example: C:\\Users\\Desktop\\img2.jpg)")
    private String imgB;

    /**
     * Default constructor.
     */
    public CompareTwoImages() {}

    @Override
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        super.setImgA(imgA);
        super.setImgB(imgB);

        try {
            super.compareTwoImages(helper.getReporter());
        } catch (FailureException e) {
            helper.getReporter().result(e.getMessage());
            return ExecutionResult.FAILED;
        }

        return ExecutionResult.PASSED;
    }
}
