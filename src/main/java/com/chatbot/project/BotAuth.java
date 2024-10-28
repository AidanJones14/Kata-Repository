package com.chatbot.project;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * The BotAuth class provides a static method for creating a SessionsClient object for a Dialogflow
 * project. Generally, the private service account key would be stored as an environment variable in a location that
 * Google Cloud knows to access automatically when creating a SessionsClient. As a workaround, there is a way to change the
 * settings used to build the SessionsClient.
 * 
 * @author Aidan Jones
 */
public class BotAuth {

    
    /**
     * This method sets the credentials using the specified path to create and return a SessionsClient object for the
     * Dialogflow project associated with the key. The credentials can be loaded from a different file location, and a SessionsSettings
     * object can be built to create the SessionsClient. In the official documentation, under the SessionsClient.create() method, 
     * it does the following: return create(SessionsSettings.newBuilder().build()); So All that needed to be done
     * was alter how the credentials are being accessed.
     * 
     * @param keyPath The path containing the Service Account Key for the project
     * 
     * @return A SessionsClient object for the associated Dialogflow project
     */
    public static SessionsClient createSessionsClient( String keyPath) throws IOException {
        
        //Create the credentials for the project from a file stream containing the Service Account key JSON file
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream( keyPath ));
        
        //Define the session settings to account for this unorthodox method of obtaining credentials and authenticating
        //The lambda function provides Google Cloud with a way to retrieve the credentials
        //(It does not take the credentials directly, most likely for security or if credentials change)
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider( () -> credentials ).build();

        //Authenticate, create, and return the SessionsClient
        return SessionsClient.create(sessionsSettings);
    }
}