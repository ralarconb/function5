package com.abh.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/TriggerStringGet". Two ways to invoke it
     * using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/TriggerStringGet
     * 2. curl "{your host}/api/TriggerStringGet?id=HTTP%20Query"
     */
    @FunctionName("TriggerStringGet")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.GET }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        // Item list
        context.getLogger().info("GET parameters are: " + request.getQueryParameters());

        // Get named parameter
        String id = request.getQueryParameters().getOrDefault("id", "");

        // Convert and display
        if (id.isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Document not found.")
                    .build();
        } else {
            // return JSON from to the client
            // Generate document
            final String name = "fake_name";
            final String jsonDocument = "{\"id\":\"" + id + "\", " +
                    "\"description\": \"" + name + "\"}";
            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonDocument)
                    .build();
        }
    }
}
