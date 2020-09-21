package io.testproject.addon.restfulapiclient.actions;

import io.testproject.java.sdk.v2.reporters.ActionReporter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ValidateJsonUsingSchema {
    public String validate(String pathToSchemaFile, String pathToSaveFile,  String jsonToValidate, boolean createFile) {
        String violationsAsOutput = "";
        Schema schema;
        JSONObject rawSchema;

        try {
            //Check if path to schema comes from a URL.
            UrlValidator urlValidator = new UrlValidator();
            if(urlValidator.isValid(pathToSchemaFile.toString())) {
                File file = new File(pathToSchemaFile);
                FileUtils.copyURLToFile(new URL(pathToSchemaFile), file);
                rawSchema = new JSONObject(new JSONTokener(file.toString()));
            } else {
                //Local file
                InputStream inputStream = getClass().getResourceAsStream(pathToSchemaFile);
                rawSchema = new JSONObject(new JSONTokener(inputStream));
            }

            try{ // Schema validation try/catch
                schema = SchemaLoader.load(rawSchema);
                schema.validate(new JSONObject(jsonToValidate)); // throws a ValidationException if this object is invalid
                violationsAsOutput = "Validation has finished successfully";
            } catch (ValidationException e) {
                List<String> listOfViolations = e.getCausingExceptions()
                        .stream()
                        .map(ValidationException::getMessage)
                        .collect(Collectors.toList());

                if(createFile) {
                    PrintWriter writer = new PrintWriter(String.format("%s%sSchemaViolation.txt", pathToSaveFile, File.pathSeparator)
                            , "UTF-8");
                    writer.println("The following violations were present:");
                    listOfViolations.forEach(writer::println);
                    writer.close();
                }
                StringBuilder result = new StringBuilder();
                result.append("The following violations were present:\n");
                listOfViolations.forEach(violation -> {
                    result.append(String.format("%s%s", violation, System.lineSeparator()));
                });

                violationsAsOutput = result.toString();
            }
        } catch (IOException fileNotFoundException) {
            violationsAsOutput = "Failed to open schema file.";
        }

        return violationsAsOutput;
    }
}
