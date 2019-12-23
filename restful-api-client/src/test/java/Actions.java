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

import io.testproject.addon.restfulapiclient.actions.*;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.testproject.java.sdk.v2.Runner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class Actions {

    private static Runner runner;

    @BeforeAll
    public static void setup() throws InstantiationException, MalformedURLException {
        runner = Runner.create(System.getenv("YOUR_DEV_TOKEN"));
    }

    @Test
    public void runGetAction() throws Exception {

        GetAction getAction = new GetAction();

        getAction.uri = "https://jsonplaceholder.typicode.com/posts/1";
        getAction.headers = "Authorization=YOUR_API_TOKEN";

        runner.run(getAction);
    }

    @Test
    public void runPostAction() throws Exception {
        PostAction postAction = new PostAction();

        postAction.uri = "https://jsonplaceholder.typicode.com/users";
        postAction.headers = "Authorization=YOUR_API_TOKEN";

        Map<String,Object> body = new HashMap<>();

        body.put("name","Example User");
        body.put("username","ExampleUser");
        body.put("email","a@a.com");

        postAction.body = JSONValue.toJSONString(body);

        runner.run(postAction);
    }

    @Test
    public void runPatchAction() throws Exception {
        PatchAction patchAction = new PatchAction();

        patchAction.uri = "https://jsonplaceholder.typicode.com/posts/1";
        patchAction.headers = "Authorization=YOUR_API_TOKEN";

        Map<String,Object> body = new HashMap<>();

        body.put("userId",1);
        body.put("id",1);
        body.put("title","Post title");
        body.put("email","Post body");

        patchAction.body = JSONValue.toJSONString(body);

        runner.run(patchAction);
    }

    @Test
    public void runPutAction() throws Exception {
        PutAction putAction = new PutAction();

        putAction.uri = "https://jsonplaceholder.typicode.com/posts/1";
        putAction.headers = "Authorization=YOUR_API_TOKEN";

        Map<String,Object> body = new HashMap<>();

        body.put("userId",1);
        body.put("id",1);
        body.put("title","Post title");
        body.put("email","Post body");

        putAction.body = JSONValue.toJSONString(body);

        runner.run(putAction);
    }

    @Test
    public void runDeleteAction() throws Exception {

        DeleteAction deleteAction = new DeleteAction();

        deleteAction.uri = "https://jsonplaceholder.typicode.com/posts/1";
        deleteAction.headers = "Authorization=YOUR_API_TOKEN,Host=jsonplaceholder.typicode.com";

        runner.run(deleteAction);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        runner.close();
    }
}
