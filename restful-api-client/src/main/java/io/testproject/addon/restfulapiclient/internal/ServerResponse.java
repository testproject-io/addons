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

package io.testproject.addon.restfulapiclient.internal;


/**
 * Object that holds server response
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class ServerResponse {
    public String responseBody = "";
    public int responseCode;
    public String responseHeaders;

    public String jsonParseResult = null;
    public String jsonParseResultAsJson = null;
    public String jJsonParseErrorMsg = null;
    public String xpathResponse = null;
    public String xpathResponseErrorMessage = null;
}
