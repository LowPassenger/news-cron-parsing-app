#   Chrono parsing application tool 


This application is designed to parse news from a selected website and save them to a database. You can view today's parsed news in a JavaFX window, scroll through the list of news, and select a specific time period to filter the news displayed.

The application uses a Docker container to save data to a MySQL database. All Docker-related configuration can be found in the docker-compose.yml file located in the root directory of the project.

# Project Structure

The project consists of three Maven modules:
  
  * Client: The JavaFX frontend for viewing and interacting with the parsed news.
  * Common: A shared module for common classes and data. (This module is not meant to be executed directly.)
  * Server: Contains the business logic and handles database connectivity.
  

# How to Start the Project

  * First, start the Server module.
  * Once the server is running, you can start the Client module to launch the JavaFX frontend.
  * Note: Do not attempt to run the Common module as it is only used for shared logic between the client and server.

# Configuration and Constants

The application uses the AppConstants utility class to store various configuration settings. You can modify this class to change settings such as:

  * The website URL for parsing news.
  * Time periods for the news parsing process.
  * JavaFX window dimensions, and more.
  * Ans so on.
