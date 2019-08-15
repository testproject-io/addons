import io.testproject.addon.imagecomparison.Actions.Android.CompareImagesAndroid;
import io.testproject.addon.imagecomparison.Actions.Android.TakeScreenshotAndroid;
import io.testproject.addon.imagecomparison.Actions.Generic.CompareTwoImages;
import io.testproject.addon.imagecomparison.Actions.Web.CompareImagesWeb;
import io.testproject.addon.imagecomparison.Actions.Web.TakeScreenshotWeb;
import io.testproject.addon.imagecomparison.Actions.iOS.CompareImagesIOS;
import io.testproject.addon.imagecomparison.Actions.iOS.TakeScreenshotIOS;
import io.testproject.java.enums.AutomatedBrowserType;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.java.sdk.v2.drivers.WebDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.IOException;

public class Tests {
    private static Runner runner;
    private final static String devToken = "YOUR_DEV_TOKEN";

    // Android
    private final static String deviceUDID = "YOUR_DEVICE_UDID";
    private final static String packageName = "PACKAGE_NAME";
    private final static String activityName = "ACTIVITY_NAME";

    // iOS
    private final static String iOSdeviceUDID = "YOUR_DEVICE_UDID";
    private final static String deviceName = "YOUR_DEVICE_NAME";
    private final static String bundleId = "YOUR_BUNDLEID";

    // Web
    private final static String URL = "https://example.testproject.io/web/";
    WebDriver driver = runner.getDriver();


    @BeforeAll
    public static void init() throws InstantiationException {
        runner = Runner.createAndroid(devToken, deviceUDID, packageName, activityName);
        runner = Runner.createIOS(devToken, iOSdeviceUDID, deviceName, bundleId);
        runner = Runner.createWeb(devToken, AutomatedBrowserType.Chrome);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        runner.close();
    }

    // Android actions
    @Test
    public void runCompareImagesAndroid() throws Exception {
        CompareImagesAndroid action = new CompareImagesAndroid();

        action.setImagePath("IMAGE_PATH");
        action.setThreshold("THRESHOLD");
        action.setResultImagePath("RESULT_PATH");
        action.setImgName("IMAGE_NAME");

        By by = By.xpath("//android.widget.FrameLayout[1]");
        runner.run(action, by);
    }

    @Test
    public void runTakeScreenshotAndroid() throws Exception {
        TakeScreenshotAndroid action = new TakeScreenshotAndroid();

        action.setPath("IMAGE_PATH");
        action.setImgName("IMAGE_NAME");

        By by = By.xpath("//android.widget.FrameLayout[1]");
        runner.run(action, by);
    }


    // iOS actions
    @Test
    public void runCompareImagesIOS() throws Exception {
        CompareImagesIOS action = new CompareImagesIOS();

        action.setImagePath("IMAGE_PATH");
        action.setThreshold("THRESHOLD");
        action.setResultImagePath("RESULT_PATH");
        action.setImgName("IMAGE_NAME");

        By by = By.id("name");

        runner.run(action, by);
    }

    @Test
    public void runTakeScreenshotIOS() throws Exception {
        TakeScreenshotIOS action = new TakeScreenshotIOS();

        action.setPath("IMAGE_PATH");
        action.setImgName("IMAGE_NAME");

        By by = By.xpath("//XCUIElementTypeWindow[1]");

        runner.run(action, by);
    }


    // Web actions
    @Test
    public void runCompareImagesWeb() throws Exception {
        CompareImagesWeb action = new CompareImagesWeb();

        action.setImagePath("IMAGE_PATH");
        action.setThreshold("THRESHOLD");
        action.setResultImagePath("RESULT_PATH");
        action.setImgName("IMAGE_NAME");

        driver.navigate().to(URL);
        By by = By.xpath("/html/body/header/nav");

        runner.run(action, by);
    }

    @Test
    public void runTakeScreenshotWeb() throws Exception {
        TakeScreenshotWeb action = new TakeScreenshotWeb();

        action.setPath("IMAGE_PATH");
        action.setImgName("IMAGE_NAME");

        driver.navigate().to(URL);
        By by = By.xpath("/html/body/header/nav");
        runner.run(action, by);
    }

    // Compare two locally saved images
    @Test
    public void runCompareTwoImages() throws Exception {
        CompareTwoImages action = new CompareTwoImages();

        action.setImgA("IMAGE_A");
        action.setImgB("IMAGE_B");
        action.setThreshold("THRESHOLD");
        action.setResultImagePath("RESULT_PATH");
        action.setImgName("IMAGE_NAME");

        runner.run(action);
    }
}
