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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import org.apache.http.client.utils.URIBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

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

    private boolean ignoreUntrustedCertificate;

    private Invocation.Builder request;

    private String headerDelimiter;

    private boolean isXML;

    private String xpath;

    /**
     * The constructor
     * @param requestType                   - The type of the request
     * @param uri                           - The uri
     * @param queryParameters               - queryParameters of the request (optional, can be null)
     * @param headers                       - headers of the request (optional, can be null)
     * @param body                          - body of the request (optional, can be null)
     * @param bodyFormat                    - The type of the body (optional, can be null)
     * @param ignoreUntrustedCertificate    - Ignore untrusted SSL certificate (optional, can be null)
     * @param headerDelimiter               - The character which to delimit the headers (optional, can be null)
     * @param isXML                         - Indicates the response will be XML format (Optional, can be null)
     * @param xpath                         - If response ix XML, provided xpath to parse the result (Optional)
     */
    public RequestHelper(
            RequestMethod requestType,
            String uri, String queryParameters,
            String headers, String body,
            String bodyFormat, String jsonPath,
            boolean ignoreUntrustedCertificate,
            String headerDelimiter,
            boolean isXML,
            String xpath) {
        this.requestMethod = requestType;
        this.uri = uri;
        this.queryParameters = queryParameters;
        this.headers = headers;
        this.body = body;
        this.bodyFormat = bodyFormat;
        this.jsonPath = jsonPath;
        this.ignoreUntrustedCertificate = ignoreUntrustedCertificate;
        this.headerDelimiter = headerDelimiter;
        this.isXML = isXML;
        this.xpath = xpath;
    }

    /**
     * initialize the request
     *
     * @throws FailureException - Unable to initialize the request.
     *                          May thrown when one of the parameter of the object are invalid.
     */
    private void initializeRequest() throws FailureException {

        Client client = null;

        if (ignoreUntrustedCertificate) {
            try {
                TrustManager[] trustManager = new X509TrustManager[]{new X509TrustManager() {

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }};

                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustManager, null);

                client = ClientBuilder.newBuilder()
                        .sslContext(sslContext)
                        .withConfig( new ClientConfig())
                        .hostnameVerifier((s, sslSession) -> true)
                        .build();
            } catch (NoSuchAlgorithmException | KeyManagementException e){
                throw new FailureException("Failed to prepare a request client with custom SSL settings", e);
            }
        } else {
            client = ClientBuilder.newClient();
        }

        // Following must be set to allow PATCH requests with Jersey
        if (requestMethod == RequestMethod.PATCH) {
            client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
        }

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
            if(Strings.isNullOrEmpty(headerDelimiter))
                headerDelimiter = "=";
            String[] headersArr = headers.split("\\s*,\\s*");
            for (String headerData : headersArr) {
                String[] headerSplit = headerData.split(headerDelimiter);
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
    public ServerResponse sendRequest() throws FailureException{

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
                    Entity<?> empty = Entity.text("");
                    response = request.put((Strings.isNullOrEmpty(body)) ? empty : Entity.entity(body, bodyFormat));
                    break;
                case POST:
                    response = request.post((Strings.isNullOrEmpty(body)) ? null : Entity.entity(body, bodyFormat));
                    break;
                case DELETE:
                    response = request.delete();
                    break;
                case PATCH:
                    response = request.method("PATCH", Strings.isNullOrEmpty(body) ? null : Entity.entity(body, bodyFormat));
            }
        } catch (ProcessingException e) {
            throw new FailureException(String.format("Failed to send the request due to an error%s%s",
                    System.lineSeparator(),
                    e.toString()), e);
        }

        ServerResponse sr = new ServerResponse();

        sr.responseBody = response.readEntity(String.class);
        sr.responseCode = response.getStatus();
        sr.responseHeaders = buildResponseString(response.getStringHeaders());

        if (!Strings.isNullOrEmpty(jsonPath)) {
            if (!sr.responseBody.isEmpty()) {
                try{
                    // Creating the json object response using the Gson json provider.
                    // This is required so the value is parsed as valid json and not string literal.
                    sr.jsonParseResultAsJson = JsonPath.parse(sr.responseBody,
                            new Configuration.ConfigurationBuilder()
                                    .jsonProvider(new GsonJsonProvider())
                                    .build()).read(jsonPath).toString();
                    sr.jsonParseResult = JsonPath.parse(sr.responseBody).read(jsonPath).toString();
                } catch (JsonPathException e) {
                    sr.jJsonParseErrorMsg = e.getMessage();
                }
            } else {
                sr.jJsonParseErrorMsg = "Can not search for requested '" + jsonPath + "' path in JSON - response is empty";
            }
        }

        if(isXML && !Strings.isNullOrEmpty(xpath)) {
            // Parse XPATH response if XML
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                builder = factory.newDocumentBuilder();
                InputSource inputSource = new InputSource(new StringReader(sr.responseBody));
                Document doc = builder.parse(inputSource);
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile(this.xpath);

                sr.xpathResponse = expr.evaluate(doc, XPathConstants.STRING).toString();

            } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
                sr.xpathResponseErrorMessage = String.format("Failed to parse response using XPATH %s%sError Message is: %s",
                        this.xpath,
                        System.lineSeparator(),
                        e.toString());
            }
        }
        return sr;
    }

    private String buildResponseString(MultivaluedMap<String, String> stringHeaders) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(stringHeaders);
    }
}
