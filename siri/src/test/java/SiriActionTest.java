import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.testproject.addon.siri.SiriAction;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.java.sdk.v2.drivers.IOSDriver;

public class SiriActionTest {
    private static final String DEV_TOKEN = System.getenv("TESTPROJECT_DEV_KEY");
    private static final String DEVICE_UDID = System.getenv("IOS_DEVICE_ID");
    private static final String DEVICE_NAME = System.getenv("IOS_DEVICE_NAME");
    private static final String BUNDLE_ID = "io.cloudgrey.the-app";

    private static Runner runner;
    private static IOSDriver<IOSElement> driver;

    @BeforeAll
    static void setup() throws InstantiationException {
        runner = Runner.createIOS(DEV_TOKEN, DEVICE_UDID, DEVICE_NAME, BUNDLE_ID);
        driver = runner.getDriver();
    }

    @AfterAll
    static void tearDown() throws IOException {
        driver.quit();
        runner.close();
    }

    @Before
    void prepareApp() {
        driver.resetApp();
    }

    @Test
    void testSiriAction() throws Exception {
        // Create Action
        SiriAction action = new SiriAction();
        action.message = "What's two plus two?";

        // Run action
        runner.run(action);

        // Verify
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("2 + 2 =")));
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("4")));
    }
}
