package com.chatbot.project;


import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.QueryResult;

import java.io.IOException;
import java.util.Scanner;


/**
 * Hello world!
 */
public class Main {
    private static final String DEFAULT_KEY_PATH = "src/main/resources/chatbot-key.json";

    public static void main(String[] args) {
        
        String sessionId = "123456789";
        String languageCode = "en-US";

        SessionsClient client = null;
        Chatbot chatbot = null;
        
        Scanner in = new Scanner(System.in);
        
        String userInput = null;

        System.out.printf("Current Service Key Path: %s\nChange Service Key Path? ( y / n ): ", DEFAULT_KEY_PATH);

        userInput = in.nextLine();

        while( !userInput.equalsIgnoreCase( "y" ) && !userInput.equalsIgnoreCase( "n" )) {
          System.out.printf("\n%s is not a valid response.\n\nChange Service Key Path? ( y / n ): ", userInput);
          userInput = in.nextLine();
        }
        
        String path = DEFAULT_KEY_PATH;
        if( userInput.equalsIgnoreCase( "y" ) ) {
          System.out.print("\nEnter Desired Path: ");
          path = in.nextLine();
        }

        try{
            client = BotAuth.createSessionClient( path );
        } catch( IOException e ) {
          System.out.printf("\nIssue creating session using path: %s\n Error: ", path, e.getMessage());
          System.exit( 1 );
        }
        
        chatbot = new Chatbot( client );
        System.out.print("\nWelcome to Chatbot, I am an agent deployed by Google's Dialogflow service.\n" 
          + "I can handle basic conversational requests related to x, y, and z.\nTo quit the chat, enter \"exit\"\nWhat can I help you with?\n\nQuery: ");
        
        userInput = in.nextLine();
        System.out.print("\n\n");

        while( !userInput.equalsIgnoreCase( "exit" ) ) {
            
          try {
            QueryResult result = chatbot.getResult(sessionId, userInput, languageCode);
            System.out.println("Response: " + result.getFulfillmentText()); // Print the response
            System.out.print("\n\n");
          } catch (ApiException | IOException e) {
            System.out.println("Error during Dialogflow interaction: " + e.getMessage());
          }
          System.out.print("Query: ");
          userInput = in.nextLine();
          System.out.print("\n\n");
        }
        
        client.close();
        
    }
}
