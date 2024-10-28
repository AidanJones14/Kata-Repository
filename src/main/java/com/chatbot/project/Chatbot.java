package com.chatbot.project;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;

import java.io.IOException;

/**
 * This class represents a Chatbot that interfaces with the Dialogflow API to get responses according
 * to user input.
 * 
 * @author Aidan Jones
 */
public class Chatbot {


    //SessionsClient required to make calls to the Dialogflow API
    private SessionsClient client;

    //Id associated with the Google loud project
    private String projectId = "chatbotflow-ttcd";

    /**
     * This constructor takes a SessionsClient object as a parameter to set the local value
     * 
     * @param client the SessionsClient used to make calls to the Dialogflow API
     */
    public Chatbot(SessionsClient client) {
        this.client = client;
    }
    
    /**
     * Sends user input to Dialogflow and retrieves the resulting QueryResult to return the fulfillment text.
     *
     * @param sessionId the unique identifier for the session
     * @param text the user input text to be processed by Dialogflow
     * @param language the language code for the input text (for our purposes, "en-US")
     * @return the fulfillment text associated with the QueryResult returned from Dialogflow
     * @throws IOException if there is a network or I/O issue related to the API call
     * @throws ApiException if there is an issue with the API request or response
     */
    public String getResult(String sessionId, String text, String language) throws IOException, ApiException {
        //The session name is created based on the project id and session id
        SessionName session = SessionName.of(projectId, sessionId);
        //The text input is built using the provided input and language
        TextInput textInput = TextInput.newBuilder().setText(text).setLanguageCode(language).build();
        //The Query itself can then be built using the TextInput object
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
        
        //The SessionsClient is then used to provide Dialogflow with the current session (to verify the project and identift the session)
        //detectIntent will prompt Dialogflow to determine the intent of the user input (based on its own collection of known intents)
        //And generate a response
        DetectIntentResponse response = client.detectIntent(session, queryInput);

        //Retrieves the fulfillment text of the response
        return response.getQueryResult().getFulfillmentText();
    }
}