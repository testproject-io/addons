package io.testproject.addon.combined.actions;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.WebElementAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import org.openqa.selenium.WebElement;

@Action(name = "Clear {{element}} contents and type {{keys}}")
public class ClearAndTypeAction implements WebElementAction {

    @Parameter(description = "Value to type")
    private String keys;

    @Override
    public ExecutionResult execute(WebAddonHelper helper, WebElement element) throws FailureException {
        element = helper.getDriver().findElement(helper.getSearchCriteria());
        element.clear();
        element.sendKeys(keys);
        return ExecutionResult.PASSED;
    }
}
