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

import io.testproject.addon.restfulapiclient.internal.RequestHelper;
import io.testproject.addon.restfulapiclient.internal.RequestMethod;
import io.testproject.addon.restfulapiclient.internal.ServerResponse;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import junit.framework.Assert;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

public class TestGetRequests {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void testBasicValidGetRequest() throws FailureException {

        // Initialize the request
        RequestHelper requestHelper = new RequestHelper(RequestMethod.GET,
                BASE_URL + "/posts", null, null, null, null, null);

        ServerResponse response = requestHelper.sendRequest();

        // Check if the server responded with the expected response code
        Assert.assertEquals(200, response.responseCode);

        System.out.println(response.responseBody);
        // We should get body
        Assert.assertNotNull(response.responseBody);

        // And because there is not jsonPath, we should check for nulls in jsonParseResult and jJsonParseErrorMsg
        Assert.assertNull(response.jsonParseResult);
        Assert.assertNull(response.jJsonParseErrorMsg);
    }

    @Test
    public void testBasicValidGetRequestWithJsonPath() throws FailureException {

        // Initialize the request
        RequestHelper requestHelper = new RequestHelper(RequestMethod.GET,
                BASE_URL + "/posts", null, null, null, null, "$[0]['userId']");

        ServerResponse response = requestHelper.sendRequest();

        // Check if the server responded with the expected response code
        Assert.assertEquals(200, response.responseCode);

        // We should get body
        Assert.assertNotNull(response.responseBody);

        // And because there is not jsonPath, we should check for nulls in jsonParseResult and jJsonParseErrorMsg
        Assert.assertNotNull(response.jsonParseResult);
        Assert.assertNull(response.jJsonParseErrorMsg);
    }

    @Test
    public void validGetRequestWithQueryParameters() throws FailureException {

        // Initialize the request
        RequestHelper requestHelper = new RequestHelper(RequestMethod.GET,
                BASE_URL + "/comments", "postId=1&id=1", null, null, null, null);

        // Send and get the server response
        ServerResponse response = requestHelper.sendRequest();

        // Check if the server responded with the expected response code
        Assert.assertEquals(200, response.responseCode);

        // The query parameters should lead to only one json object in the json array
        JSONArray jsonArray = new JSONArray(response.responseBody);
        Assert.assertEquals(1, jsonArray.length());

        // jsonParseResult and jJsonParseErrorMsg should be null
        Assert.assertNull(response.jsonParseResult);
        Assert.assertNull(response.jJsonParseErrorMsg);
    }

}
