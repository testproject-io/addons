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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides convenience methods to normalize action inputs.
 * We may need to convert "10$" to 10 or "5.2 USD" to 5.2.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class InputTransformer {

    public static float extractAmount(String str) throws IllegalArgumentException {
        Pattern p = Pattern.compile("[0-9.]+");
        Matcher matcher = p.matcher(str);
        if (!matcher.find())
            throw new IllegalArgumentException("Could not extract number from string");
        return Float.parseFloat(matcher.group(0));
    }

    public static String extractCurrencyStr(String str) throws IllegalArgumentException {
        String[][] currencies =
                {
                        {"USD", "$", "Dollar"},
                        {"EUR", "€", "Euro"},
                        {"ILS", "₪", "NIS", "Shekel"},
                        {"JPY", "¥", "Yen"}
                };
        str = str.toLowerCase();
        for (String[] currencyStrs : currencies)
            for (String currencyStr : currencyStrs)
                if (str.contains(currencyStr.toLowerCase())) return currencyStrs[0];

        throw new IllegalArgumentException("Currency symbol not found");
    }
}
