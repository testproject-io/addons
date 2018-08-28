/*
 * Copyright 2018 TestProject LTD. and/or its affiliates
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

package io.testproject.addon.currency.converter;

import io.testproject.addon.currency.converter.common.CurrencyExchangeWrapper;
import io.testproject.addon.currency.converter.common.InputTransformer;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.ActionParameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.WebAction;
import io.testproject.java.sdk.v2.addons.helpers.WebAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.reporters.ActionReporter;

import java.util.List;

/**
 * This action converts different currencies using external currency exchange API
 *
 * @author TestProject LTD.
 * @version 1.0
 */
@Action(name = "Live Currency Converter", description = "Convert {{amount}} from {{source}} to {{target}}")
public class CurrencyConverterAction implements WebAction {

    @ActionParameter(description = "Amount (Default: 1.00)")
    public String amount = "";

    @ActionParameter(description = "Source (AUD, EUR, CAD, GBP, etc.) (Default: USD, leave empty for auto detection)")
    public String source = "";

    @ActionParameter(description = "Target (AUD, EUR, CAD, GBP, etc.) (Default: EUR)")
    public String target = "";

    @ActionParameter(description = "Static exchange rate (Leave empty to use live rate)")
    public String customConversionRate = "";

    @ActionParameter(description = "Conversion result", direction = ParameterDirection.OUTPUT)
    public String conversionResult = "";

    public ExecutionResult execute(WebAddonHelper helper) {

        // Get the report object to report the result of the action to TestProject
        ActionReporter report = helper.getReporter();

        // Initialize source and target fields
        if (source.equals("")) {
            try {
                // If the value contain currency symbol or string, we try to get from it the currency type
                source = InputTransformer.extractCurrencyStr(amount);
            } catch (IllegalArgumentException e) {
                source = "USD";
            }
        } else
            source = source.toUpperCase();
        if (target.equals(""))
            target = "EUR";
        else
            target = target.toUpperCase();
        if (source.equals(target)) {
            report.result("Source currency can't be the same as the target currency");
            return ExecutionResult.FAILED;
        }

        // Get the amount from the amount field
        float floatAmount;

        if (amount.equals("")) {
            floatAmount = 1f;
        } else {
            try {
                floatAmount = InputTransformer.extractAmount(amount);
            } catch (IllegalArgumentException e) {
                report.result("Illegal amount input in the amount field");
                return ExecutionResult.FAILED;
            }
        }

        try {
            CurrencyExchangeWrapper.updateAllSupportedCurrencies();
        } catch (Exception e) {
            report.result("Failed to get list of all supported currencies");
            return ExecutionResult.FAILED;
        }

        // Check if the currency of "source" field is supported
        if (!CurrencyExchangeWrapper.allSupportedCurrencies.contains(source)) {
            report.result("The requested source currency (" + source + ") is not supported.\n" +
                    "The supported currencies are:\n " +
                    CurrencyExchangeWrapper.getAllSupportedCurrenciesAsString());
            return ExecutionResult.FAILED;
        }

        if (!CurrencyExchangeWrapper.allSupportedCurrencies.contains(target)) {
            List<String> supportedCurrencies = CurrencyExchangeWrapper.allSupportedCurrencies;
            supportedCurrencies.remove(source);
            report.result("The conversion " + source + " --> " + target + " is not supported.\n " +
                    "You can convert " + source + " to one of the following currencies:\n " +
                    String.join(", ", supportedCurrencies));
            return ExecutionResult.FAILED;
        }

        // Start the CurrencyExchangeWrapper
        CurrencyExchangeWrapper currencyExchangeWrapper = new CurrencyExchangeWrapper(source, target);

        // Get the conversionRate from customConversionRate or from the api
        float conversionRate;
        if (!customConversionRate.equals("")) {
            try {
                conversionRate = InputTransformer.extractAmount(customConversionRate);
            } catch (Exception exception) {
                report.result("Cannot get the customConversionRate parameter");
                return ExecutionResult.FAILED;
            }
            if (conversionRate <= 0f) {
                report.result("customConversionRate is illegal");
                return ExecutionResult.FAILED;
            }
        } else {
            try {
                conversionRate = currencyExchangeWrapper.getConversionRate();
            } catch (Exception exception) {
                report.result("Failed to get the conversion rate for the selected currency. - error: " + exception.getMessage());
                return ExecutionResult.FAILED;
            }
        }

        // Calculate the final currency
        conversionResult = Float.toString(conversionRate * floatAmount);
        report.result("Currency is " + conversionResult + " " + target + " ( " + source + " --> " + target + " )");

        return ExecutionResult.PASSED;
    }
}
