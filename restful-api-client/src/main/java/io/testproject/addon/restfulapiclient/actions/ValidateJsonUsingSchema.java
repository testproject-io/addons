package io.testproject.addon.restfulapiclient.actions;
import io.testproject.java.sdk.v2.reporters.ActionReporter;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ValidateJsonUsingSchema {
    public String validate(Path pathToSchemaFile, Path pathToSaveFile,  String jsonToValidate, boolean createFile, ActionReporter reporter) {
        String violationsAsOutput = "";
        try (InputStream inputStream = getClass().getResourceAsStream(pathToSchemaFile.toString())) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            try{
                schema.validate(new JSONObject(jsonToValidate)); // throws a ValidationException if this object is invalid
                reporter.result("Validation has finished successfully");
            } catch (ValidationException e) {
                List<String> listOfViolations = e.getCausingExceptions()
                        .stream()
                        .map(ValidationException::getMessage)
                        .collect(Collectors.toList());

                if(createFile) {
                    PrintWriter writer = new PrintWriter(String.format("%s%sSchemaViolation.txt", pathToSaveFile, File.pathSeparator)
                            , "UTF-8");
                    listOfViolations.forEach(writer::println);
                    writer.close();
                }
                StringBuilder result = new StringBuilder();
                result.append("The following violations were present:\n");
                listOfViolations.forEach(violation -> {
                    result.append(String.format("%s%s", violation, System.lineSeparator()));
                });

                reporter.result(result.toString());
                violationsAsOutput = result.toString();
            }
        }
         catch (NullPointerException | IOException e) {
            reporter.result("Failed to open schema file!");
         }

        return violationsAsOutput;
    }
}
