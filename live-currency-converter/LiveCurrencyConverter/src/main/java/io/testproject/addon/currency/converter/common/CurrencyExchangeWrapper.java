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

package io.testproject.addon.currency.converter.common;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a wrapper for the Foreign exchange rates API with currency conversion
 * published by the European Central Bank under permissive MIT License
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class CurrencyExchangeWrapper {

    private String fromCurrency, toCurrency;

    public static List<String> allSupportedCurrencies;

    /**
     * The constructor of the CurrencyExchangeWrapper
     *
     * @param fromCurrency from currency - (for example "USD")
     * @param toCurrency   to currency - (for example "USD")
     */
    public CurrencyExchangeWrapper(String fromCurrency, String toCurrency) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    /**
     * Update the list of all supported currencies
     */
    public static void updateAllSupportedCurrencies() {
        JsonObject response = getServerResponse("");
        String baseCurrency = response.get("base").getAsString();
        JsonObject rates = response.get("rates").getAsJsonObject();
        allSupportedCurrencies = new ArrayList<>(rates.keySet());
        if (!allSupportedCurrencies.contains(baseCurrency)) allSupportedCurrencies.add(baseCurrency);
    }

    /**
     * Get the server response as json object
     *
     * @param arguments The arguments of the rest api call
     * @return JsonObject
     */
    private static JsonObject getServerResponse(String arguments) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("https://exchangeratesapi.io/api/latest" + arguments);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        String jsonRootStr = response.readEntity(String.class);
        return new Gson().fromJson(jsonRootStr, JsonObject.class);
    }

    /**
     * Get the conversion rate of fromCurrency -> toCurrency
     *
     * @return the conversion rate
     */
    public float getConversionRate() {
        JsonObject serverResponse = getServerResponse("?base=" + fromCurrency + "&symbols=" + toCurrency);
        return serverResponse.get("rates").getAsJsonObject().get(toCurrency).getAsFloat();
    }

    /**
     * Get list of the supported currencies by the service.
     */
    public static String getAllSupportedCurrenciesAsString() {
        if (allSupportedCurrencies.size() > 0)
            return String.join(", ", allSupportedCurrencies);
        else
            return "NONE";
    }
}
