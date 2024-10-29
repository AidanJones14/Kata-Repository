package com.chatbot.project;


import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.QueryResult;

import java.io.IOException;
import java.util.Scanner;


/**
 * The Main class handles the general flow of the chatbot program. Functionality is provided to change the path containing the
 * Service Account key needed to authenticate with Google Cloud if needed. This is a security risk and generally an unorthodox approach,
 * but it would be a much more complicated ordeal to create an endpoint where authentication is secured. For the purposes of
 * this project, the repository will not be shared with anyone other than who is trusted. This class reads user input via the command line,
 * and maintains an instance of the Chatbot class, in order to process user input using a SessionsClient object initiated with the
 * BotAuth static createSessionsClient() method and the path to the Service Account key JSON file. The response from the Dialogflow API is read
 * back to the user via the command line, and the user is prompted to continue until they input "exit".
 * 
 * @author Aidan Jones
 */
public class Main {

    //Default key path where the Service Account Key JSON file resides
    private static final String DEFAULT_KEY_PATH = "src/main/resources/chatbot-key.json";
    
    /**
     * This method contains all logic for the flow of the Chatbot program. It handles user input and
     * outputs the result from the Dialogflow service
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        
        //Arbitrary session identifier
        String sessionId = "123456789";
        //Language code for english (Used to generate TextInput for API)
        String languageCode = "en-US";
        
        //SessionsClient object used to make API requests to Dialogflow Service
        SessionsClient client = null;

        //Chatbot object used to process user input and retrieve Server response
        Chatbot chatbot = null;
        
        //Scanner used to read user input
        Scanner in = new Scanner(System.in);
        
        //String used to process user input
        String userInput = null;

        System.out.printf("Current Service Key Path: %s\nChange Service Key Path? ( y / n ): ", DEFAULT_KEY_PATH);

        userInput = in.nextLine();
        
        //Retrieves and ensures that user answers the prompt correctly
        while( !userInput.equalsIgnoreCase( "y" ) && !userInput.equalsIgnoreCase( "n" )) {
          System.out.printf("\n%s is not a valid response.\n\nChange Service Key Path? ( y / n ): ", userInput);
          userInput = in.nextLine();
        }
        
        //Alters path to Service Account Key if a different location needs to be used
        String path = DEFAULT_KEY_PATH;
        if( userInput.equalsIgnoreCase( "y" ) ) {
          System.out.print("\nEnter Desired Path: ");
          path = in.nextLine();
        }

        //Attempts to create the SessionsClient object using the desired path and handles exceptions accordingly
        try{
            client = BotAuth.createSessionsClient( path );
        } catch( IOException e ) {
          System.out.printf("\nIssue creating session using path: %s\n", path);
          System.exit( 1 );
        }
        
        chatbot = new Chatbot( client );
        System.out.print("\nWelcome to Chatbot, I am an agent deployed by Google's Dialogflow service.\n" 
          + "I can handle basic conversational requests related to reserving a table at a restaurant or booking a hotel room.\nTo quit the chat, enter \"exit\"\nWhat can I help you with?\n\nQuery: ");
        


        userInput = in.nextLine();
        System.out.print("\n\n");
        
        // Continues conversation until user enters "exit"
        while( !userInput.equalsIgnoreCase( "exit" ) ) {
          
          //Attempts to retrieve a response from the Dialogflow service using the Chatbot object
          //Handles any API or IO exceptions caused by indicating user of error message
          try {
            System.out.println("Response: " + chatbot.getResult(sessionId, userInput, languageCode));
            System.out.print("\n\n");
          } catch (ApiException | IOException e) {
            System.out.println("Error during Dialogflow interaction: " + e.getMessage() + "\n\n");
          }
          System.out.print("Query: ");
          userInput = in.nextLine();
          System.out.print("\n\n");
        }
        
        client.close();
        
    }
}
