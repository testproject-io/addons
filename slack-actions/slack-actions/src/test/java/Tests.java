import io.testproject.addon.slack.actions.SendSlackMessageAction;
import io.testproject.java.enums.AutomatedBrowserType;
import io.testproject.java.sdk.v2.Runner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Tests {

    private static Runner runner;
    private final static String devToken = "*****";
    private final static String webhook = "YOUR_SLACK_WEBHOOK_URL";

    @BeforeAll
    public static void init() throws InstantiationException {
        runner = Runner.createWeb(devToken, AutomatedBrowserType.Chrome);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        runner.close();
    }

    @Test
    public void runSendSlackMessageAction() throws Exception {
        SendSlackMessageAction sendSlackMessageAction = new SendSlackMessageAction(
                webhook,
                "My Text",
                "#36a64f",
                "My Title",
                "https://api.slack.com/",
                "John Doe",
                "This is an additional text");

        runner.run(sendSlackMessageAction);
    }
}
