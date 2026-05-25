/**
 * Student Name: Nicholas Morris
 * Student Number: ST10502284
 * Assignment: PROG5121 PoE Part 1 - Registration and Login
 * Description: Main Class that runs the application, handles user input and output, and calls the necessary methods from the Login and Messages classes
 */
package main;

import fileManagement.FileManager;
import java.io.IOException;
import java.util.Scanner;
import part1.Login;
import part2.Messages;

public class RunApp {//start of class

    public static void main(String[] args) throws IOException { //start of main method

        Scanner input = new Scanner(System.in);
        Login loginHandler = new Login();
        Messages messageHandler = new Messages();
        FileManager fileManager = new FileManager();

        String finalMessage = "";
        String registerName = "";
        String registerSurname = "";
        String registerUsername = "";
        String registerPassword = "";
        String registerNumber = "";

        boolean loggedIn = false;

        // -- Application Start --
        System.out.println("-- WELCOME TO QUICKCHAT --");
        System.out.println("1) Register new account");
        System.out.println("2) Login with existing account");
        System.out.print("Select an option: ");
        int startChoice = input.nextInt();
        input.nextLine();
        System.out.println("-----------------------------------");

        // user registration path
        if (startChoice == 1) {

            boolean registered = false;
            // Loop until the user successfully registers
            // This allows them to correct any input errors without restarting the app
            while (!registered) {
                System.out.println("-- REGISTER NEW ACCOUNT --");
                System.out.print("Enter your Name: ");
                registerName = input.nextLine();

                System.out.print(">" + "\nEnter your Surname: ");
                registerSurname = input.nextLine();

                System.out.print(">" + "\nEnter your Username: ");
                registerUsername = input.nextLine();

                System.out.print(">" + "\nEnter your Password: ");
                registerPassword = input.nextLine();

                System.out.print(">" + "\nEnter your Cellphone Number: ");
                registerNumber = input.nextLine();
                System.out.println("-----------------------------------");

                // Call the registerUser method and capture the result message
                String regStatus = loginHandler.registerUser(registerUsername, registerPassword, registerName, registerSurname, registerNumber);
                System.out.println(regStatus);

                // If registration is successful, save the user data to file and display login details
                if (regStatus.contains("registered successfully")) {
                    registered = true;

                    // FIXED argument order to match saveRegisteredUser(username, password, name, surname, cell)
                    fileManager.saveRegisteredUser(registerUsername, registerPassword, registerName, registerSurname, registerNumber);

                    System.out.println("-----------------------------------");
                    System.out.println("-- LOGIN DETAILS --");
                    System.out.println("Your username is: " + registerUsername);
                    System.out.println("Your password is: " + registerPassword);
                    System.out.println("-----------------------------------");
                } else {
                    System.out.println("Registration failed. Please try again.");
                    System.out.println("-----------------------------------");
                }
            }
        
        // user login path
        // This path allows the user to login with an existing account by checking the credentials against the saved data in the file users.txt. 
        // If the credentials match an existing account, the user is logged in and can access the messaging features. If not, they are prompted to try again until they enter valid credentials.
        } else if (startChoice == 2) {

            boolean foundInFile = false;
            while (!foundInFile) {
                System.out.println("-- LOGIN WITH EXISTING ACCOUNT --");
                System.out.print("Enter your Username: ");
                String enteredUsername = input.nextLine();

                System.out.print("Enter your Password: ");
                String enteredPassword = input.nextLine();
                System.out.println("-----------------------------------");

                //Check if the entered credentials match an existing user in the file
                if (fileManager.userExists(enteredUsername, enteredPassword)) {

                    String[] userData = fileManager.readRegisteredUsers(enteredUsername, enteredPassword);

                    // Fill in the Login object so returnLoginStatus() has firstName/surname
                    // userData: [0]=username [1]=password [2]=name [3]=surname [4]=cellNumber
                    loginHandler.registerUser(userData[0], userData[1], userData[2], userData[3], userData[4]);

                    registerUsername = userData[0];
                    registerPassword = userData[1];
                    registerName = userData[2];
                    registerSurname = userData[3];
                    registerNumber = userData[4];

                    // If we found the user in the file, we can attempt to log them in using the loginUser method
                    foundInFile = true;
                    System.out.println("Account found!");
                    System.out.println("-----------------------------------");
                    loggedIn = loginHandler.loginUser(registerUsername, registerPassword);

                } else {
                    System.out.println("No matching account found. Please try again.");
                    System.out.println("-----------------------------------");
                }
            }

        } else {
            System.out.println("Invalid option. Restarting...");
            return;
        }

        //--- USER LOGIN PHASE ---
        // This loop allows the user to attempt to login until they enter valid credentials. 
        while (!loggedIn) {
            System.out.println("-- LOGIN TO ACCOUNT --");
            System.out.print("Please enter your Username: ");
            String username = input.nextLine();

            System.out.print(">" + "\nPlease enter your Password: ");
            String password = input.nextLine();
            System.out.println("-----------------------------------");

            // Attempt to login with the entered credentials
            boolean isSuccess = loginHandler.loginUser(username, password);
            
            // If login is successful, set loggedIn to true to exit the loop and display the login status message
            if (isSuccess) { 
                loggedIn = true;
                finalMessage = loginHandler.returnLoginStatus(isSuccess);
                System.out.println("-- STATUS --");
                System.out.println(finalMessage);
            } else {
                System.out.println("Login failed. Please try again.");
                System.out.println("-----------------------------------");
            }
        }

        //--- MAIN APPLICATION PHASE ---
        // Once the user is logged in, they can access the main features of the application, which include sending messages, viewing recently sent messages, and quitting the application.
        if (loggedIn) {

            boolean runApp = true;
            while (runApp) {
                System.out.println("");
                System.out.println("---Welcome to QuickChat---");

                messageHandler.displayOptions();
                System.out.print("\tSelect Option: ");

                int menuSelection = input.nextInt();
                input.nextLine();

                // The switch statement handles the user's selection from the main menu and directs them to the corresponding functionality based on their choice.
                switch (menuSelection) {    

                    case 1: {

                        System.out.println("You have selected: Send Messages");

                        System.out.print("Enter the number of messages you want to send: ");
                        int numOfMessages = input.nextInt();
                        input.nextLine();
                        
                        System.out.println("Enter the recipients Cell number: ");
                        String cellNum = input.nextLine();

                        // Loop through the number of messages the user wants to send, prompting for message content and performing necessary checks for each message
                        for (int i = 0; i < numOfMessages; i++) {
                            
                            System.out.println("===================================");
                            System.out.println("Entered Cellphone Number: " + cellNum);
                            System.out.println(messageHandler.checkRecipientCell(cellNum));
                            System.out.println("===================================");
                            
                            // If the cell number check indicates an issue, prompt the user to re-enter the cell number and retry the current iteration
                            if(messageHandler.checkRecipientCell(cellNum).contains("incorrectly")){
                                System.out.println("Enter the recipients Cell number: ");
                                cellNum = input.nextLine();
                                i--;
                                continue;
                            }

                            System.out.println("Please enter your message: ");
                            System.out.print(":$>> ");
                            String message = input.nextLine();

                            messageHandler.setMessage(message);

                            String lengthCheck = messageHandler.checkMessageLength(message);
                            
                            // If the message length check indicates an issue, prompt the user to re-enter the message and retry the current iteration
                            if (!lengthCheck.equals("Message ready to send.")) {
                                System.out.println(lengthCheck);
                                i--; // Decrement i to retry the same iteration
                                continue; //bypasses code executed after this check and goes to the next loop iteration, which is the same one since i was decremented
                            }

                            System.out.println(lengthCheck);

                            // If the message passes the length check, we proceed to generate a message ID, check its validity, increment the message counter, and create a message hash string.
                            System.out.println("===================================");
                            long msgID = messageHandler.generateMessageID();
                            messageHandler.setMessageID(msgID);
                            boolean msgIDCheck = messageHandler.checkMessageID(msgID);
                            System.out.println("Generated Message ID: " + msgID);
                            System.out.println("Message ID Valid: " + msgIDCheck);
                            System.out.println("===================================");

                            int currentMsgCount = messageHandler.incrementMessageCounter();
                            messageHandler.setGlobalMessageCounter(currentMsgCount);

                            String msgHashString = messageHandler.createMessageHash(msgID, currentMsgCount, message);
                            messageHandler.setMsgHashString(msgHashString);
                            
                            

                        }

                        System.out.println(messageHandler.sentMessage());
                        System.out.print("\tSelect Message Action: ");
                        int messageAction = input.nextInt();

                        // After sending the messages, the user is prompted to select an action for the sent messages, which includes options to send the messages, store them as JSON, or disregard them and return to the main menu.
                        // The switch statement handles the user's selection and performs the corresponding actions based on their choice.
                        switch (messageAction) {

                            case 1: {
                                System.out.println("You have selected: Send Message");
                                System.out.println("Sending messages...");
                                System.out.println("Messages sent successfully!");

                                messageHandler.storeMessageAsRegular(messageHandler.getMessageID(), messageHandler.getMsgHashString(), registerNumber, messageHandler.getMessage());

                                System.out.println(messageHandler.printMessages());
                                System.out.println("Total messages sent: " + messageHandler.getGlobalMessageCounter());

                            }

                            case 2: {
                                System.out.println("You have selected: Store Messages");
                                messageHandler.storeMessageAsJSON(messageHandler.getMessageID(), messageHandler.getMsgHashString(), registerNumber, messageHandler.getMessage());
                                System.out.println("Message stored successfully!");
                                messageHandler.setGlobalMessageCounter(0);
                                

                            }

                            case 3: { //goes back to main menu, does not exit the app
                                System.out.println("You have selected: Disregard");
                                System.out.println("Returning to main menu...");

                            }

                        }

                    }
                    case 2: {
                        System.out.println("You have selected: Show recently sent");
                        System.out.println("Retrieving recently sent messages...");
                        System.out.println(messageHandler.printMessages());
                        System.out.println("Total messages sent: " + messageHandler.getGlobalMessageCounter());

                    }

                    case 3: {
                        System.out.println("You have selected: Quit");
                        System.out.println("Exiting application...");
                        runApp = false;
                    }

                    default: {
                        System.out.println("Invalid selection, please try again.");
                        messageHandler.displayOptions();
                        System.out.print("\tSelect Option: ");
                        menuSelection = input.nextInt();
                    }

                }

            }

        }

    }
}
//end of main method
//end of class
