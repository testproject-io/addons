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

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ComparisonResult;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import io.testproject.java.sdk.v2.reporters.Reporter;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This base class contains the logic of the image comparison action for Android, iOS and Web.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class BaseCompareImages {

    @Parameter(description = "Max image difference percentage (Default: 5%)")
    public String threshold;

    @Parameter(description = "Local path to save the comparison image (for example: D:\\screenshots)")
    public String resultImagePath;

    @Parameter(description = "File name (default: current timestamp)")
    public String imgName;

    @Parameter(description = "Full path on local drive (for example: D:\\screenshots\\result.jpg)", direction = ParameterDirection.OUTPUT)
    public String fullResPath;

    // Parameters
    private String imgA;
    private String imgB;
    private BufferedImage elementImg;
    private BufferedImage userImg;
    private String imagePath;

    // Setters
    public void setResultImagePath(String resultImagePath) { this.resultImagePath = resultImagePath; }

    public void setImgName(String imgName) { this.imgName = imgName; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public void setThreshold(String threshold) { this.threshold = threshold; }

    public void setImgA(String imgA) { this.imgA = imgA; }

    public void setImgB(String imgB) { this.imgB = imgB; }

    /**
     * Default constructor.
     */
    protected BaseCompareImages() {}

    /**
     * Executes the image comparison action.
     *
     * @param imgA
     * @param reporter
     * @return
     * @throws FailureException
     */
    protected ExecutionResult compareElementImage(File imgA, Reporter reporter) throws FailureException {
        if (!validateParameters()) {
            throw new FailureException("You must provide a path to the original image you want to compare to.");
        }
        return compareImagesUtils(imgA, new File(imagePath), reporter);
    }

    /**
     * Compares two locally saved images. Both images must be at the same size.
     * @param reporter
     * @return
     * @throws FailureException
     */
    protected ExecutionResult compareTwoImages(Reporter reporter) throws FailureException {
        if (StringUtils.isEmpty(imgA) || StringUtils.isEmpty(imgB)) {
            throw new FailureException("You must provide a path for both images");
        }

        if (StringUtils.isEmpty(resultImagePath)) {
            throw new FailureException("You must provide a local path to store the comparison image");
        }

        threshold = (StringUtils.isEmpty(threshold)) ? "5" : threshold;

        return compareImagesUtils(new File(imgA), new File(imgB), reporter);
    }

    /**
     * Performs the actual image comparison flow: calculates the difference and getting the comparison image.
     * @param imgA
     * @param imgB
     * @param reporter
     * @throws FailureException
     */
    private ExecutionResult compareImagesUtils(File imgA, File imgB, Reporter reporter) throws FailureException {
        try {
            double diffPercentage = compareImages(imgA, imgB);
            getResultImage(resultImagePath);

            if (diffPercentage > Double.parseDouble(threshold)) {
                throw new FailureException(String.format("Image compare failed. The difference is: %.2f%%. Image difference file stored in: %s", diffPercentage, fullResPath));
            }

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Image compare passed. The difference is %.2f%%.", diffPercentage))
                    .append(System.lineSeparator());

            if (!StringUtils.isEmpty(fullResPath)) {
                StringBuilder storedIn = new StringBuilder();
                storedIn.append("Image difference file stored in:")
                        .append(System.lineSeparator())
                        .append(fullResPath);
                sb.append(storedIn);
            }

            reporter.result(sb.toString());
        } catch (IOException e) {
            throw new FailureException("The location of the images was not found.");
        } catch (NumberFormatException e) {
            throw new FailureException("The threshold value must be a double type numeric value.");
        }

        return ExecutionResult.PASSED;
    }

    /**
     * Validates the input parameters provided by the user.
     *
     * @return
     */
    private boolean validateParameters() {
        if (StringUtils.isEmpty(imagePath))
            return false;
        if (StringUtils.isEmpty(threshold))
            threshold = "5";
        return true;
    }

    /**
     * Compares two images and return the difference percentage. The comparison is done by comparing each pixel of the two images.
     *
     * @param imgA
     * @param imgB
     * @return
     * @throws IOException
     * @throws FailureException
     */
    private double compareImages(File imgA, File imgB) throws IOException, FailureException {
        setImages(imgA, imgB);

        int width1 = elementImg.getWidth();
        int width2 = userImg.getWidth();
        int height1 = elementImg.getHeight();
        int height2 = userImg.getHeight();

        if ((width1 != width2) || (height1 != height2))
            throw new FailureException(
                    String.format("Destination image must have the same dimensions as the source image [%s X %s]",
                            elementImg.getWidth(), elementImg.getHeight()));

        long difference = 0;
        for (int i = 0; i < height1; i++) {
            for (int j = 0; j < width1; j++) {
                int rgbA = elementImg.getRGB(j, i);
                int rgbB = userImg.getRGB(j, i);
                int redA = (rgbA >> 16) & 0xff;
                int greenA = (rgbA >> 8) & 0xff;
                int blueA = (rgbA) & 0xff;
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) & 0xff;

                difference += Math.abs(redA - redB);
                difference += Math.abs(greenA - greenB);
                difference += Math.abs(blueA - blueB);
            }
        }

        double total_pixels = width1 * height1 * 3;
        double avg_different_pixels = difference / total_pixels;
        return (avg_different_pixels / 255) * 100;
    }

    /**
     * Sets the provided images of type File to private fields variables of type BufferedImage.
     *
     * @param imgA
     * @param imgB
     * @throws IOException
     * @throws FailureException
     */
    private void setImages(File imgA, File imgB) throws IOException, FailureException {
        if (imgA == null || imgB == null) {
            throw new FailureException("Can not open the images");
        }
        this.elementImg = ImageIO.read(imgA);
        this.userImg = ImageIO.read(imgB);
    }

    /**
     * Creates a comparison result image that highlights the differences between the two images.
     * The result image stored in the specified location provided by the user.
     *
     * @param resultImgPath
     * @throws IOException
     */
    private void getResultImage(String resultImgPath) throws IOException {
        if (StringUtils.isEmpty(resultImgPath))
            return;

        String timestamp = new SimpleDateFormat("yyyyMMddHHmm'.jpg'").format(new Date());
        imgName = (StringUtils.isEmpty(imgName)) ? "comparison_result" + timestamp : imgName + ".jpg";
        String pathName = resultImgPath + File.separator + imgName;
        File resultDestination = new File(pathName);

        ImageComparison imageComparison = new ImageComparison(userImg, elementImg, resultDestination);
        imageComparison.setRectangleLineWidth(3);

        ComparisonResult comparisonResult = imageComparison.compareImages();

        BufferedImage resultImage = comparisonResult.getResult();
        ImageComparisonUtil.saveImage(resultDestination, resultImage);

        fullResPath = pathName;
    }
}
