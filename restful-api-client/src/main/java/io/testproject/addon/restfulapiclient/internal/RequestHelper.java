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

import com.google.common.base.Strings;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import org.apache.http.client.utils.URIBuilder;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import javax.ws.rs.core.MediaType;

/**
 * Helper class for sending the requests
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class RequestHelper {

    private RequestMethod requestMethod;

    private String uri;

    private String queryParameters;

    private String headers;

    private String body;

    private String bodyFormat;

    private String jsonPath;


    private Invocation.Builder request;

    /**
     * The constructor
     *
     * @param requestType     - The type of the request
     * @param uri             - The uri
     * @param queryParameters - queryParameters of the request (optional, can be null)
     * @param headers         - headers of the request (optional, can be null)
     * @param body            - body of the request (optional, can be null)
     * @param bodyFormat      - The type of the body (optional, can be null)
     */
    public RequestHelper(RequestMethod requestType, String uri, String queryParameters, String headers, String body, String bodyFormat, String jsonPath) {
        this.requestMethod = requestType;
        this.uri = uri;
        this.queryParameters = queryParameters;
        this.headers = headers;
        this.body = body;
        this.bodyFormat = bodyFormat;
        this.jsonPath = jsonPath;
    }

    /**
     * initialize the request
     *
     * @throws FailureException - Unable to initialize the request.
     *                          May thrown when one of the parameter of the object are invalid.
     */
    private void initializeRequest() throws FailureException {

        Client client = ClientBuilder.newClient();

        // Setup the uri and the query parameters for the request
        URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(uri);
        } catch (URISyntaxException e) {
            client.close();
            throw new FailureException("Failed to create URI", e);
        }

        // Add the query parameters to the URI
        if (!Strings.isNullOrEmpty(queryParameters)) {

            // Ignore the "?" if the user wrote it at the beginning of the string
            if (String.valueOf(queryParameters.charAt(0)).equals("?"))
                queryParameters = queryParameters.substring(1);

            String[] queryList = queryParameters.split("&");
            for (String queryData : queryList) {
                String[] querySplit = queryData.split("=");
                String queryKey = querySplit[0];
                String queryValue = queryData.substring(queryKey.length() + 1);
                uriBuilder.addParameter(queryKey, queryValue);
            }
        }

        // Set up the request
        try {
            request = client.target(uriBuilder.build()).request();
        } catch (URISyntaxException e) {
            client.close();
            throw new FailureException("Failed to build the request", e);
        }

        // Set up request headers

        if (!Strings.isNullOrEmpty(headers)) {
            String[] headersArr = headers.split("\\s*,\\s*");
            for (String headerData : headersArr) {
                String[] headerSplit = headerData.split("=");

                String headerKey = headerSplit[0];
                String headerValue = headerData.substring(headerKey.length() + 1);
                request.header(headerKey, headerValue);
            }
        }

        // Set default bodyFormat
        if (!Strings.isNullOrEmpty(body) && Strings.isNullOrEmpty(bodyFormat) &&
                (requestMethod == RequestMethod.POST || requestMethod == RequestMethod.PUT))
            bodyFormat = MediaType.APPLICATION_JSON;
    }

    /**
     * Send the request
     *
     * @return ServerResponse object
     * @throws FailureException - Unable to send the request
     */
    public ServerResponse sendRequest() throws FailureException {

        initializeRequest();

        Response response = null;

        if (!Strings.isNullOrEmpty(body) && bodyFormat.isEmpty())
            bodyFormat = MediaType.APPLICATION_JSON;

        try {
            switch (requestMethod) {
                case GET:
                    response = request.get();
                    break;
                case PUT:
                    response = request.put((Strings.isNullOrEmpty(body)) ? null : Entity.entity(body, bodyFormat));
                    break;
                case POST:
                    response = request.post((Strings.isNullOrEmpty(body)) ? null : Entity.entity(body, bodyFormat));
                    break;
                case DELETE:
                    response = request.delete();
                    break;
            }
        } catch (ProcessingException e) {
            throw new FailureException("Failed to send the request due to error.",e);
        }

        ServerResponse sr = new ServerResponse();

        sr.responseBody = response.readEntity(String.class);
        sr.responseCode = response.getStatus();

        if (!Strings.isNullOrEmpty(jsonPath)) {

            if (!sr.responseBody.isEmpty()) {
                try {
                    sr.jsonParseResult = JsonPath.parse(sr.responseBody).read(jsonPath).toString();
                } catch (JsonPathException e) {
                    sr.jJsonParseErrorMsg = e.getMessage();
                }
            } else {
                sr.jJsonParseErrorMsg = "Can not search for requested '" + jsonPath + "' path in JSON - response is empty";
            }
        }

        return sr;
    }


}
