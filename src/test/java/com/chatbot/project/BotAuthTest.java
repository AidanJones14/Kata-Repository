package com.chatbot.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


import org.junit.jupiter.api.Test;
import com.google.cloud.dialogflow.v2.SessionsClient;
import java.io.IOException;

/**
 * Unit test for BotAuth Class. This class contains a static method for validating credentials to create a SessionsClient
 * for the Dialogflow service. Generally, the private service account key would be stored as an environment variable,
 * where the credentials would be veriied automatically when creating the SessionsClient. 
 * 
 * @author Aidan Jones
 */
public class BotAuthTest {
    private static final String KEY_PATH = "src/main/resources/chatbot-key.json";
    /**
     * Uses key path to authorize and create and verify a SessionsClient object through Dialogflow API.
     */
    @Test
    public void testCreateSessionsClient() {
        //Create SessionsClient by specifying credentials key path and ensure that no exception is thrown
        SessionsClient test = null;
        try {
          test = BotAuth.createSessionsClient(KEY_PATH);
        } catch (IOException e) {
          fail("Exception thrown: " + e.getMessage());
        }
        

        //Ensure that the SessionsClient for the Dialogflow project service account was created
        assertNotNull( test );

        if(test != null) {
          test.close();
        }

    }
}
