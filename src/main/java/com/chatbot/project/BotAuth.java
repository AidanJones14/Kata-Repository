package com.chatbot.project;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;

import java.io.FileInputStream;
import java.io.IOException;

public class BotAuth {

    

    public static SessionsClient createSessionClient( String keyPath) throws IOException {
        
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream( keyPath ));
        
        //lambda function is used to return the credentials every time Dialogflow needs to authenticate
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider( () -> credentials ).build();

        
        return SessionsClient.create(sessionsSettings);
    }
}