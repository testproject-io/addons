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
package io.testproject.addon.restfulapiclient.actions;

import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

/**
 *  Validator class which holds the validate method.
 *
 * @author TestProject LTD.
 * @version 1.0
 */
public class ValidateJsonUsingSchema {

    private final String TEMP_SCHEMA = "TempSchema";
    private final String FILE = "file";
    private final String URL = "url";

    /**
     * The validate method, takes the JSON schema and returns the result of the validation as a String.
     *
     * @param pathToSchemaFile             - The path to the schema file, can be a URL/Local path to file/the JSON itself
     * @param pathToSaveFile               - path to save the output file in your local files system (by default will create a temp file)
     * @param jsonToValidate               - the json object to validate
     * @param createFile                   - create a file to save the output (true/false)
     */
    public String validate(String pathToSchemaFile, String pathToSaveFile,  String jsonToValidate, boolean createFile) {
        String violationsAsOutput = "";
        Schema schema;
        JSONObject rawSchema;
        File file;
        Logger logger = LoggerFactory.getLogger(ValidateJsonUsingSchema.class);


        try {
            // Check if path to schema comes from a URL.
            UrlValidator urlValidator = new UrlValidator();
            if(urlValidator.isValid(pathToSchemaFile)) {
                file = createFile(URL, pathToSchemaFile);
            } else if(isValidPath(pathToSchemaFile)){
                // Local file
                file = new File(pathToSchemaFile);
            } else {
                // pathToSchemaFile = the schema itself.
                // Create a temp file from the schema.
                file = createFile(FILE, pathToSchemaFile);
            }

            // Set the raw schema from file
            try(InputStream is = new FileInputStream(file)) {
                rawSchema = new JSONObject(new JSONTokener(is));
            }

            try{ // Schema validation try/catch
                schema = SchemaLoader.load(rawSchema);
                schema.validate(new JSONObject(jsonToValidate)); // throws a ValidationException if this object is invalid
                violationsAsOutput = "Schema validation has passed";
            } catch (ValidationException e) {
                List<String> listOfViolations = e.getAllMessages();
                // If not defined, it will save it in the Downloads folder.
                if(pathToSaveFile == null)
                    pathToSaveFile = Paths.get(System.getProperty("user.home"), "Downloads").toString();

                if(createFile) {
                    try(PrintWriter writer = new PrintWriter(String.format("%s%sSchemaViolation.txt", pathToSaveFile, File.separator)
                            , "UTF-8")) {
                        writer.println("The following violations were present:");
                        listOfViolations.forEach(writer::println);
                    }
                }
                StringBuilder result = new StringBuilder();
                result.append("The following violations were present:").append(System.lineSeparator());
                listOfViolations.forEach(violation -> {
                    result.append(violation).append(System.lineSeparator());
                });

                violationsAsOutput = result.toString();
            }
        } catch (IOException fileNotFoundException) {
            violationsAsOutput = "Failed to open schema file.";
            logger.error("Failed to open schema file", fileNotFoundException);
        } catch(JSONException e) {
            violationsAsOutput = "JSON schema is invalid: " + System.lineSeparator() + e.getMessage();
            logger.error("JSON schema is invalid", e);
        }

        return violationsAsOutput;
    }

    private static boolean isValidPath(String path) {
        return new File(path).isDirectory();
    }

    private File createFile(String from, String path) throws IOException {
        File file = File.createTempFile(TEMP_SCHEMA, ".json");
        switch (from) {
            case FILE:
                FileUtils.writeStringToFile(file, path, "UTF-8");
                break;
            case URL:
                FileUtils.copyURLToFile(new URL(path), file);
                break;
        }

        return file;
    }
}
