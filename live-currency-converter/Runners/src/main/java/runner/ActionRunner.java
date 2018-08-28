package runner;

import io.testproject.addon.currency.converter.CurrencyConverterAction;
import io.testproject.java.classes.DriverSettings;
import io.testproject.java.enums.DriverType;
import io.testproject.java.sdk.v2.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a runner to test the Addon Actions
 * published by the European Central Bank under permissive MIT License
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class ActionRunner {

    private static final Logger log = LoggerFactory.getLogger(ActionRunner.class);

    // Setting the development token
    private final static String DEV_TOKEN = System.getenv("DEV_TOKEN");

    private static DriverSettings driverSettings;

    public static void main(String[] args) {

        driverSettings = new DriverSettings(DriverType.Chrome);

        try (Runner runner = new Runner(DEV_TOKEN, driverSettings)) {
            runCurrencyConverterAction(runner);
        } catch (Throwable e) {
            log.error("Action execution failed", e);
        }
    }

    public static void runCurrencyConverterAction(Runner runner) {

        CurrencyConverterAction action = new CurrencyConverterAction();

        action.source = "USD";
        action.target = "EUR";

        runner.run(action);
    }
}