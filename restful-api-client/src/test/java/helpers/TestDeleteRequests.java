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
import org.junit.jupiter.api.Test;

public class TestDeleteRequests {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void basicValidDeleteRequest() throws FailureException {

        // Initialize the request
        RequestHelper requestHelper = new RequestHelper(RequestMethod.DELETE,
                BASE_URL + "/posts/1", null, null, null, null, null);

        // Send and get the server response
        ServerResponse response = requestHelper.sendRequest();

        // Check if the server responded with the expected response code
        Assert.assertEquals(200, response.responseCode);
    }
}
