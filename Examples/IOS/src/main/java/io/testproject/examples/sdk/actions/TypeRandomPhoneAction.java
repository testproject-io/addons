package io.testproject.examples.sdk.actions;

import io.appium.java_client.ios.IOSElement;
import io.testproject.java.annotations.v2.ElementAction;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.IOSElementAction;
import io.testproject.java.sdk.v2.addons.helpers.IOSAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

@ElementAction(name = "Type Random Phone Number")
public class TypeRandomPhoneAction implements IOSElementAction {

    @Parameter
    public String countryCode = "1";

    @Parameter
    public int maxDigits;

    @Parameter(direction = ParameterDirection.OUTPUT)
    public String phone;

    public ExecutionResult execute(IOSAddonHelper helper, IOSElement element) throws FailureException {
        long number = (long) (Math.random() * Math.pow(10, maxDigits));
        phone = String.format("+%s%s", countryCode, number);
        element = helper.getDriver().findElement(helper.getSearchCriteria());
        element.sendKeys(phone);
        return ExecutionResult.PASSED;
    }
}
