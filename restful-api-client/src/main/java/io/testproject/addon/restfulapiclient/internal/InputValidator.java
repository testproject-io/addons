package io.testproject.addon.restfulapiclient.internal;

import com.google.common.base.Strings;
import io.testproject.java.sdk.v2.exceptions.FailureException;

import java.io.File;

/**
 * Helper class for validating the actions inputs
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class InputValidator {

    /**
     * Validate the fields of the action
     * @param uri - The URI field
     * @param queryParameters - The queryParameters field
     * @param headers - The headers field
     * @param expectedResponseCode - expectedResponseCode field
     * @param headerDelimiter - The header delimiter
     * @param filePath - Path to a local file to upload
     * @throws FailureException - An illegal value for one of the fields was found
     */
    public static void validateInputsFields(String uri, String queryParameters, String headers, String expectedResponseCode, String headerDelimiter, String filePath) throws FailureException {
        validateURI(uri);
        validateQueryParameters(queryParameters);
        validateHeaders(headers, headerDelimiter);
        validateExpectedResponseCode(expectedResponseCode);
        validateFilePath(filePath);
    }

    private static void validateFilePath(String filePath) throws FailureException {
        if(!Strings.isNullOrEmpty(filePath)) {
            if(!new File(filePath).exists())
                throw new FailureException("File " + filePath + " was not found");
        }
    }

    private static void validateURI(String uri) throws FailureException {
        if (Strings.isNullOrEmpty(uri))
            throw new FailureException("The uri '" + uri + "' parameter is invalid");
    }

    private static void validateQueryParameters(String queryParameters) throws FailureException {
        if (Strings.isNullOrEmpty(queryParameters)) return;

        String[] queryList = queryParameters.split("&");
        for (String queryData : queryList) {
            String[] querySplit = queryData.split("=");
            if (querySplit.length < 2)
                throw new FailureException("The queryParameters parameter is invalid");
        }
    }

    private static void validateHeaders(String headers, String headerDelimiter) throws FailureException {
        if (Strings.isNullOrEmpty(headers)) return;
        if (Strings.isNullOrEmpty(headerDelimiter)) headerDelimiter = "=";

        String[] headersArr = headers.split("\\s*,\\s*");
        for (String headerData : headersArr) {
            String[] headerSplit = headerData.split(headerDelimiter);
            if (headerSplit.length < 2)
                throw new FailureException("The '" + headers + "' parameter is invalid");
        }
    }

    private static void validateExpectedResponseCode(String expectedResponseCode) throws FailureException {

        if (Strings.isNullOrEmpty(expectedResponseCode))
            return;

        int iExpectedResponseCode = Integer.valueOf(expectedResponseCode);

        if (iExpectedResponseCode < 100 || iExpectedResponseCode > 600)
            throw new FailureException("expectedResponseCode must be a number between 100 and 599");
    }
}
