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

package helpers;

import com.jayway.jsonpath.JsonPath;
import io.testproject.addon.restfulapiclient.internal.RequestHelper;
import io.testproject.addon.restfulapiclient.internal.RequestMethod;
import io.testproject.addon.restfulapiclient.internal.ServerResponse;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import junit.framework.Assert;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestPostRequests {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void testBasicValidPostRequest() throws FailureException {

        // Initialize the request
        RequestHelper requestHelper = new RequestHelper(RequestMethod.POST,
                BASE_URL + "/posts", null, null, null, null, null);

        // Send the request
        ServerResponse response = requestHelper.sendRequest();

        // Check if the server responded with the expected response code
        Assert.assertEquals(201, response.responseCode);

        Assert.assertNotNull(response.responseBody);
        Assert.assertNull(response.jsonParseResult);
        Assert.assertNull(response.jJsonParseErrorMsg);
    }

    @Test
    public void testValidPostRequestWithQueryParameters() throws FailureException {

        // Initialize the request
        RequestHelper requestHelper = new RequestHelper(RequestMethod.POST,
                BASE_URL + "/posts", "a=1&b=2", null, null, null, null);

        ServerResponse response = requestHelper.sendRequest();

        // Check if the server responded with the expected response code
        Assert.assertEquals(201, response.responseCode);

        Assert.assertNotNull(response.responseBody);
        Assert.assertNull(response.jsonParseResult);
        Assert.assertNull(response.jJsonParseErrorMsg);
    }

    @Test
    public void testValidPostRequestWithBody() throws FailureException {

        // The body info
        Map<String,Object> body = new HashMap<>();
        body.put("postedKey","some value");

        // Initialize the request
        RequestHelper requestHelper = new RequestHelper(RequestMethod.POST,
                BASE_URL + "/users", null, null,
                JSONValue.toJSONString(body),
                null, null);

        // Send and get the server response
        ServerResponse response = requestHelper.sendRequest();

        // Check if the server responded with the expected response code
        Assert.assertEquals(201, response.responseCode);

        // Check if the function posted the body by checking if the server returned the key "postedKey" in the body
        Assert.assertEquals("some value", JsonPath.parse(response.responseBody).read("postedKey"));

        // jsonParseResult and jJsonParseErrorMsg should be null
        Assert.assertNull(response.jsonParseResult);
        Assert.assertNull(response.jJsonParseErrorMsg);
    }
}
