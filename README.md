NOTE: Built using Maven

This Java project utilizes the Dialogflow REST API to enable a chatbot that assists users with various services; currently, it supports only restaurant reservations and finding hotel listings. The model is not trained to fulfill any actual requests, instead functioning primarily as a mock-up to simulate the user's ability to reserve a table or find hotel listings.

Documentation for the implementation was generated and stored in the "Javadocs_HTML/apidocs" directory and can be accessed in a web browser via the "index.html" file, provided it remains in the same directory as its accompanying files.

While I had planned to test the Chatbot and Main classes, I decided against it due to constraints with making real API calls. I did not have sufficient time to implement a mock dependency, such as Mockito, to simulate the API calls.

The Dialogflow Agent I created has been trained to handle basic conversational intents related to:

1. Reserving a table at a restaurant
2. Booking a hotel room

Due to time limitations, I was unable to expand the Dialogflow agent with additional conversational intents. It currently does not fulfill requests, but only demonstrates the reservation and booking conversations. Below are sample conversations to illustrate its capabilities.

Example Conversations: Reserving a table at a restaurant

(a)

Query: Can you reserve me a table somewhere?

Response: Where would you like to make the reservation?

Query: Olive Garden

Response: How many seats would you like to reserve?

Query: Five

Response: What day are you planning to go?

Query: Tomorrow at 3 pm

Response: I've reserved a table for 5 at Olive Garden. Let me know if you need anything else!

(b)

Query: Can you help me reserve a table next Wednesday for 4 people?

Response: What’s the name of the restaurant?

Query: Chili’s

Response: What time would you like the reservation?

Query: 6 pm

Response: A table for 4 is all set at Chili’s. Have a great dining experience!

When the agent detects the user's intent to make a reservation, it ensures that the following parameters are gathered if not already provided:

1. Name of the restaurant
2. Number of guests
3. Date
4. Time


Example Conversations: Booking a hotel room

(a)

Query: Can you book a hotel room for me?

Response: What location are you considering for your stay?

Query: Boston

Response: What days do you plan on staying?

Query: Monday through Friday next week

Response: Can you specify a price range for your stay?

Query: $600

Response: I have some recommendations for hotels in Boston that fit your budget of $600.

(b)

Query: Can you find a room for me this weekend, my budget is $100

Response: Where would you like to book your hotel?

Query: Raleigh

Response: You can get good deals on hotels in Raleigh within your budget of $100.

When the agent detects the user's intent to find hotel options, it ensures the following parameters are gathered if not already provided:

1. Location
2. Period of time
3. Price range


USAGE WARNING: This project authenticates with Google Cloud using a Service Account Key. Generally, the private service account key would be stored as an environment variable in a location accessible by Google Cloud when creating a SessionsClient. As a workaround, the program includes a setting to customize the directory used for building the Sessions Client for Dialogflow (and authenticate with the service). By default, the key is set to be located at "src/main/resources/chatbot-key.json", and I have provided an option to adjust this directory if needed. Because this repository is private, I have included the Service Account Key in the repository under the specified path.

Usage Information: 
The program should be run using "Main.java", where my design propogates specific responsibilities to the BotAuth and Chatbot classes. I have also provided a test class for the BotAuth class to test authentification using the Service Key found in "src/main/resources/chatbot-key.json".