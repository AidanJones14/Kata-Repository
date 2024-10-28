package com.chatbot.project;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.*;

import java.io.IOException;

public class Chatbot {
    private SessionsClient client;
    private String projectId = "chatbotflow-ttcd";

    public Chatbot(SessionsClient client) {
        this.client = client;
    }

    public QueryResult getResult(String sessionId, String text, String language) throws IOException, ApiException {

        SessionName session = SessionName.of(projectId, sessionId);
        TextInput textInput = TextInput.newBuilder().setText(text).setLanguageCode(language).build();
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        DetectIntentResponse response = client.detectIntent(session, queryInput);
        return response.getQueryResult();
    }
}